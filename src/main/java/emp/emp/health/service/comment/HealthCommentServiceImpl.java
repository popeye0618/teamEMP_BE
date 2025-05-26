package emp.emp.health.service.comment;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import emp.emp.exception.BusinessException;
import emp.emp.health.dto.request.GeminiRequest;
import emp.emp.health.dto.response.GeminiResponse;
import emp.emp.health.dto.response.HealthRecordGraphRes;
import emp.emp.health.entity.HealthComment;
import emp.emp.health.enums.Type;
import emp.emp.health.exception.HealthErrorCode;
import emp.emp.health.repository.HealthCommentRepository;
import emp.emp.member.entity.Member;
import emp.emp.util.security.SecurityUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HealthCommentServiceImpl implements HealthCommentService {

	@Qualifier("geminiRestTemplate")
	private final RestTemplate restTemplate;
	private final HealthCommentRepository healthCommentRepository;
	private final SecurityUtil securityUtil;

	@Value("${gemini.api.url}")
	private String apiUrl;

	@Value("${gemini.api.key}")
	private String geminiApiKey;

	@Override
	@Transactional
	public String getAiComment(int year, int month, int week, Type type, List<HealthRecordGraphRes> data, Member member) {

		if (data.isEmpty()) {
			return "입력 데이터가 없습니다.";
		}

		System.out.println(data.size());
		Optional<HealthComment> existing = healthCommentRepository.findByYearAndMonthAndWeekAndMemberAndTypeAndDataLength(
			year, month, week, member, type, data.size());

		if (existing.isPresent()) {
			return existing.get().getContent();
		}

		String comment;
		try {
			comment = getContents(year, month, week, type, data);
		} catch (Exception e) {
			throw new BusinessException(HealthErrorCode.AI_ERROR);
		}

		HealthComment hc = HealthComment.builder()
			.year(year)
			.month(month)
			.week(week)
			.member(member)
			.type(type)
			.content(comment)
			.dataLength(data.size())
			.build();
		healthCommentRepository.save(hc);

		return comment;

	}

	@Override
	@Transactional
	public String getAiComment(int year, int month, Type type, List<HealthRecordGraphRes> data, Member member) {

		if (data.isEmpty()) {
			return "입력 데이터가 없습니다.";
		}

		Optional<HealthComment> existing = healthCommentRepository
			.findByYearAndMonthAndWeekIsNullAndMemberAndTypeAndDataLength(year, month, member, type, data.size());

		if (existing.isPresent()) {
			return existing.get().getContent();
		}

		String comment;
		try {
			comment = getContents(year, month, null, type, data);
		} catch (Exception e) {
			throw new BusinessException(HealthErrorCode.AI_ERROR);
		}

		HealthComment hc = HealthComment.builder()
			.year(year)
			.month(month)
			.week(null)
			.member(member)
			.type(type)
			.content(comment)
			.dataLength(data.size())
			.build();
		healthCommentRepository.save(hc);

		return comment;
	}

	private String getContents(int year, int month, Integer week, Type type, List<HealthRecordGraphRes> data) {

		String requestUrl = apiUrl + geminiApiKey;

		String prompt = "";
		if (week != null) {
			prompt = makePrompt(year, month, week, type, data);
		} else {
			prompt = makePrompt(year, month, type, data);
		}

		GeminiRequest request = new GeminiRequest(prompt);
		GeminiResponse response = restTemplate.postForObject(requestUrl, request, GeminiResponse.class);

		String message = response.getCandidates().get(0).getContent().getParts().get(0).getText().toString();

		return message;
	}

	private String makePrompt(int year, int month, int week, Type type, List<HealthRecordGraphRes> data) {
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String unit = getUnit(type);

		List<String> asStrings = data.stream()
			.map(r -> r.getDate().format(fmt) + ", " + r.getValue() + " " + unit + "\n")
			.collect(Collectors.toList());

		return year + "년 " + month + "월 " + week + "주차의 " + type.name() +
			"에 대한 기록들이야. 이 주의 건강 통계에 대한 분석과 앞으로 어떻게 행동하면 좋을지를 100자 이내의 한글로 답변해줘.\n"
			+ String.join("", asStrings);
	}

	private String makePrompt(int year, int month, Type type, List<HealthRecordGraphRes> data) {
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String unit = getUnit(type);

		List<String> asStrings = data.stream()
			.map(r -> r.getDate().format(fmt) + ", " + r.getValue() + " " + unit + "\n")
			.collect(Collectors.toList());

		return year + "년 " + month + "월의 " + type.name() +
			"에 대한 기록들이야. 이 달의 건강 통계에 대한 분석과 앞으로 어떻게 행동하면 좋을지를 100자 이내의 한글로 답변해줘.\n"
			+ String.join("", asStrings);
	}

	private String getUnit(Type type) {
		return switch (type) {
			case BLOOD_PRESSURE -> "mmHg";
			case BLOOD_SUGAR   -> "mg/dL";
			case SLEEP_TIME    -> "h";
			case WEIGHT        -> "kg";
		};
	}
}

package emp.emp.member.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import emp.emp.exception.BusinessException;
import emp.emp.member.dto.request.HealthTagReq;
import emp.emp.member.dto.response.HealthTagRes;
import emp.emp.member.entity.HealthTag;
import emp.emp.member.entity.Member;
import emp.emp.member.exception.HealthTagErrorCode;
import emp.emp.member.repository.HealthTagRepository;
import emp.emp.util.security.SecurityUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HealthTagServiceImpl implements HealthTagService {

	private final HealthTagRepository healthTagRepository;
	private final SecurityUtil securityUtil;

	@Override
	@Transactional
	public List<HealthTagRes> createHealthTag(List<HealthTagReq> healthTagReqList) {

		Member member = securityUtil.getCurrentMember();

		validTagLength(healthTagReqList);
		validTagCount(member, healthTagReqList);

		List<HealthTag> healthTags = healthTagReqList.stream().map(
			healthTagReq -> HealthTag.builder()
				.member(member)
				.content(healthTagReq.getContent())
				.isPublicTag(healthTagReq.isPublic())
				.build()
		).collect(Collectors.toList());

		List<HealthTag> saved = healthTagRepository.saveAll(healthTags);

		return toHealthTagRes(saved);
	}

	@Override
	@Transactional
	public void deleteHealthTag(Long tagId) {

		Member member = securityUtil.getCurrentMember();

		HealthTag tag = healthTagRepository.findById(tagId)
			.orElseThrow(() -> new BusinessException(HealthTagErrorCode.TAG_NOT_EXIST));

		if (!tag.getMember().getId().equals(member.getId())) {
			throw new BusinessException(HealthTagErrorCode.NOT_MY_TAG);
		}

		healthTagRepository.delete(tag);

	}

	@Override
	public List<HealthTagRes> getMyHealthTags() {
		Member member = securityUtil.getCurrentMember();

		List<HealthTag> healthTags = healthTagRepository.findAllByMember(member);

		return toHealthTagRes(healthTags);
	}

	@Override
	public List<String> getHealthTags(Member member) {
		List<HealthTag> healthTagList = healthTagRepository.findAllByMemberAndIsPublicTag(member, true);

		return healthTagList.stream().map(
			HealthTag::getContent
		).collect(Collectors.toList());
	}

	private List<HealthTagRes> toHealthTagRes(List<HealthTag> healthTags) {
		return healthTags.stream()
			.map(tag -> {
				HealthTagRes res = new HealthTagRes();
				res.setId(tag.getId());
				res.setContent(tag.getContent());
				res.setPublic(tag.isPublicTag());
				return res;
			})
			.collect(Collectors.toList());
	}

	private void validTagLength(List<HealthTagReq> healthTagReqList) {
		healthTagReqList.forEach(req -> {
			if (req.getContent() == null || req.getContent().length() > 4) {
				throw new BusinessException(HealthTagErrorCode.LENGTH_NOT_VALID);
			}
		});
	}

	private void validTagCount(Member member, List<HealthTagReq> healthTagReqList) {
		long existingPublic = healthTagRepository.countByMemberAndIsPublicTag(member, true);
		long existingPrivate = healthTagRepository.countByMemberAndIsPublicTag(member, false);

		long reqPublic = healthTagReqList.stream().filter(HealthTagReq::isPublic).count();
		long reqPrivate = healthTagReqList.size() - reqPublic;

		if (existingPublic + reqPublic > 2) {
			throw new BusinessException(HealthTagErrorCode.PUBLIC_COUNT_NOT_VALID);
		}
		if (existingPrivate + reqPrivate > 2) {
			throw new BusinessException(HealthTagErrorCode.PRIVATE_COUNT_NOT_VALID);
		}
	}
}

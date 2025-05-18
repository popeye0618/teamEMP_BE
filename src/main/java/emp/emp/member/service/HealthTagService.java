package emp.emp.member.service;

import java.util.List;

import emp.emp.member.dto.request.HealthTagReq;
import emp.emp.member.dto.response.HealthTagRes;
import emp.emp.member.entity.Member;

public interface HealthTagService {

	List<HealthTagRes> createHealthTag(List<HealthTagReq> healthTagReqList);

	void deleteHealthTag(Long tagId);

	List<HealthTagRes> getMyHealthTags();

	List<String> getHealthTags(Member member);
}

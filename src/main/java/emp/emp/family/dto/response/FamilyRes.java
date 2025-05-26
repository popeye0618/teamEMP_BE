package emp.emp.family.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FamilyRes {

	private String familyName;
	private String familyCode;
	private FamilyMemberRes familyHead;
	private List<FamilyMemberRes> familyMembers;
}

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
public class FamilyMemberRes {

	private String name;
	private int age;
	private String gender;
	private List<String> healthTag;
}

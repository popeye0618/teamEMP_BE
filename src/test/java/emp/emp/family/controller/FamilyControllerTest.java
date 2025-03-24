package emp.emp.family.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import emp.emp.family.dto.request.CreateFamilyReq;
import emp.emp.family.dto.request.JoinFamilyReq;
import emp.emp.family.service.FamilyService;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Import({FamilyControllerTest.TestConfig.class})
class FamilyControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private FamilyService familyService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@DisplayName("가족 생성 테스트")
	@WithMockUser(username = "testUser")
	void testCreateFamily() throws Exception {
		CreateFamilyReq req = new CreateFamilyReq();
		req.setName("Test Family");

		doNothing().when(familyService).createFamily(any(CreateFamilyReq.class));

		MvcResult result = mockMvc.perform(post("/api/auth/user/family")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req)))
			.andReturn();

		assertThat(result.getResponse().getStatus()).isEqualTo(200);
	}

	@Test
	@DisplayName("가족 가입 테스트")
	@WithMockUser(username = "testUser")
	void testJoinFamily() throws Exception {
		JoinFamilyReq req = new JoinFamilyReq();
		req.setCode("ABC123");

		doNothing().when(familyService).joinFamily(any(JoinFamilyReq.class));

		MvcResult result = mockMvc.perform(post("/api/auth/user/family/join")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req)))
			.andReturn();

		assertThat(result.getResponse().getStatus()).isEqualTo(200);
	}

	@Test
	@DisplayName("가족 삭제 테스트")
	@WithMockUser(username = "testUser")
	void testDeleteFamily() throws Exception {
		doNothing().when(familyService).deleteFamily();

		MvcResult result = mockMvc.perform(delete("/api/auth/user/family"))
			.andReturn();

		assertThat(result.getResponse().getStatus()).isEqualTo(200);
	}

	@TestConfiguration
	static class TestConfig {
		@Bean
		@Primary
		public FamilyService familyService() {
			return Mockito.mock(FamilyService.class);
		}
	}
}
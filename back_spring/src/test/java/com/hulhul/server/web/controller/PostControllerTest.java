package com.hulhul.server.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hulhul.server.domain.anonymous.AnonymousStatus;
import com.hulhul.server.domain.post.PostStatus;
import com.hulhul.server.web.dto.PostRequestDto;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class PostControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;

//	@Test
	public void 포스트_RESTAPI_통신() {
		// RestAPI 통신 확인
		try {
			mockMvc.perform(get("/api/v1/post/1").contentType(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(status().isOk()).andDo(print());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void 포스트_로그인후_저장() {
		MockHttpSession session = new MockHttpSession();
		PostRequestDto dto = PostRequestDto.builder().title("테스트").contents("테스트 콘텐츠").anonymous(AnonymousStatus.Anonymous).category_id(1L).build();
		try {
			mockMvc.perform(post("/api/v1/user/login").param("email", "test1@test.com").param("password", "test1")
					.session(session).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
					.andDo(print());
			
			String content = objectMapper.writeValueAsString(dto);
			
			mockMvc.perform(post("/api/v1/post/save").session(session).content(content).contentType(MediaType.APPLICATION_JSON_VALUE))
					.andExpect(status().isOk()).andDo(print());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

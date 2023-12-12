package com.cst438.controller;

import static com.cst438.test.utils.TestUtils.asJsonString;
import static com.cst438.test.utils.TestUtils.fromJsonString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import com.cst438.domain.AttemptRepository;
import com.cst438.dto.MultiplyProblem;
import com.cst438.dto.MultiplyResult;


@SpringBootTest
@AutoConfigureMockMvc
public class TestResultController {


	@Autowired
	MockMvc mvc;
	
	@Autowired
	AttemptRepository attemptRepository;
	
	@Test
	public void correctAnswer() throws Exception {
		
		MockHttpServletResponse response;
		MultiplyProblem mp = new MultiplyProblem("test", 39, 42, 42*39);	
		response = mvc.perform(
				post("/result").
				content(asJsonString(mp)).
				contentType(MediaType.APPLICATION_JSON).
				accept(MediaType.APPLICATION_JSON)).
				andReturn().
				getResponse();
		assertEquals(200, response.getStatus());
		MultiplyResult actual_mr = fromJsonString(
				response.getContentAsString(),
				MultiplyResult.class);
		assertTrue(actual_mr.correct());


		response = mvc.perform(
				get("/result/test").
				accept(MediaType.APPLICATION_JSON)).
				andReturn().
				getResponse();
		assertEquals(200, response.getStatus());
		MultiplyResult[] results = fromJsonString(
				response.getContentAsString(),
				MultiplyResult[].class);
		assertEquals(1, results.length);
		assertEquals(39, results[0].factorA());
		assertEquals(42, results[0].factorB());
		assertEquals("test", results[0].alias());
		assertTrue(results[0].correct());
	}
}

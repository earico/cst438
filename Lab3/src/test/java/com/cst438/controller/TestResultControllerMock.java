package com.cst438.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import com.cst438.controller.ResultController;
import com.cst438.dto.MultiplyProblem;
import com.cst438.dto.MultiplyResult;
import com.cst438.service.MultiplyChecker;
import com.cst438.service.MultiplyHistory;
import static com.cst438.test.utils.TestUtils.fromJsonString;
import static com.cst438.test.utils.TestUtils.asJsonString;


@ContextConfiguration(classes= {ResultController.class})
@WebMvcTest
public class TestResultControllerMock {
	
	@MockBean
	MultiplyChecker checker;
	
	@MockBean
	MultiplyHistory history;
	
	@Autowired
	MockMvc mvc;
	
	@Test
public void correctAnswer() throws Exception {
		
		MockHttpServletResponse response;
		
		MultiplyProblem mp = 
                        new MultiplyProblem("test", 42, 39, 42*39);
		
		/*
		 * Define the mocks for MultiplyChecker and MultiplyHistory
		 */
		MultiplyResult mr = 
            new MultiplyResult(0, "test", 42, 39, 42*39, 42*39, true);
		MultiplyResult[] results = new MultiplyResult[]{ mr};
		
		given(checker.check(mp)).willReturn(mr);
		given(history.getHistory(
                eq("test"),
                any(Integer.class))).
                willReturn(results);
                		
		response = mvc.perform(
				post("/result").
				content(asJsonString(mp)).
				contentType(MediaType.APPLICATION_JSON).
				accept(MediaType.APPLICATION_JSON)).
				andReturn().
				getResponse();
		
		assertEquals(200, response.getStatus());
		verify(checker, times(1)).check(mp);
		verify(history, times(1)).saveHistory(any(), any());
		
		response = mvc.perform(
				get("/result/test").
				accept(MediaType.APPLICATION_JSON)).
				andReturn().
				getResponse();
		
		MultiplyResult[] actual_results = fromJsonString(
				response.getContentAsString(),
				MultiplyResult[].class);
		assertEquals(1, actual_results.length);
		verify(history, times(1)).getHistory("test", 5);
	}
}


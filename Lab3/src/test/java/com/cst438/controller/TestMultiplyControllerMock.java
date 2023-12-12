package com.cst438.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.times;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import com.cst438.dto.MultiplyProblem;
import com.cst438.service.RandomProblem;
import static com.cst438.test.utils.TestUtils.fromJsonString;

@WebMvcTest
public class TestMultiplyControllerMock {
	@Autowired
	MockMvc mvc;
	
	@MockBean
	RandomProblem randomProblem;


	@Test
	public void testGetProblem() throws Exception {
		MockHttpServletResponse response;

		MultiplyProblem mp = new MultiplyProblem(null, 20, 21, 0);

		given(randomProblem.getProblem()).willReturn(mp);

		response = mvc.perform(get("/multiplication/new").
				accept(MediaType.APPLICATION_JSON)).
				andReturn().
				getResponse();
		
		MultiplyProblem actual = fromJsonString(
						response.getContentAsString(), 
						MultiplyProblem.class);


		assertEquals(20, actual.factorA());
		assertEquals(21, actual.factorB());
		verify(randomProblem, times(1)).getProblem();
	}
}


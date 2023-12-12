package com.cst438.controller;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import com.cst438.dto.MultiplyProblem;
import com.cst438.service.RandomProblem;
import static com.cst438.test.utils.TestUtils.fromJsonString;

@ContextConfiguration(classes = {MultiplyController.class, RandomProblem.class})

@WebMvcTest
public class TestMultiplyController {
	@Autowired
	MockMvc mvc;


	@Test
	public void testGetProblem() throws Exception {
		MockHttpServletResponse response;
		response = mvc.perform(get("/multiplication/new").
                          accept(MediaType.APPLICATION_JSON)).
                          andReturn().
                          getResponse();
		MultiplyProblem mp = fromJsonString(
                          response.getContentAsString(), 
                          MultiplyProblem.class);
		assertTrue(mp.factorA() >= 11 && mp.factorA() <= 99);
		assertTrue(mp.factorB() >= 11 && mp.factorB() <= 99);
	}
}


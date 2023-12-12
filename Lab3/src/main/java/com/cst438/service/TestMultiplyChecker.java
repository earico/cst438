package com.cst438.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.cst438.dto.MultiplyProblem;
import com.cst438.dto.MultiplyResult;


public class TestMultiplyChecker {
	
	@Test
	public void testCorrect() {
		MultiplyChecker checker = new MultiplyChecker();
		
		MultiplyProblem mp = new MultiplyProblem("test", 20, 21, 420);
		MultiplyResult mr = checker.check(mp);
		assertTrue(mr.correct());
	}
	
	@Test
	public void testInCorrect() {
		MultiplyChecker checker = new MultiplyChecker();
		
		MultiplyProblem mp = new MultiplyProblem("test", 20, 21, 422);
		MultiplyResult mr = checker.check(mp);
		assertFalse(mr.correct());
		assertEquals(420, mr.answer());
	}
}

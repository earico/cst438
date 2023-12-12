package com.cst438.service;

import org.springframework.stereotype.Service;

import com.cst438.dto.MultiplyProblem;
import com.cst438.dto.MultiplyResult;

@Service
public class MultiplyChecker {
	
	public MultiplyResult check(MultiplyProblem mp) {
		int answer = mp.factorA()*mp.factorB();
		boolean correct =  answer ==mp.attempt();
		MultiplyResult mr = new MultiplyResult(0, mp.alias(), mp.factorA(), mp.factorB(), mp.attempt(), answer, correct);
		return mr;
	}

}

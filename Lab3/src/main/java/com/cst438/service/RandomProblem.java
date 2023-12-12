package com.cst438.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.cst438.dto.MultiplyProblem;

@Service
public class RandomProblem {
	
	private final int LOWER = 11;  // lower and upper limits (inclusive) for factors.
	private final int UPPER = 99;
	
	private Random generator;
	
	public RandomProblem() {
		this.generator = new Random();
	}
	
	protected RandomProblem(Random generator) {
		this.generator = generator;
	}
	
	public MultiplyProblem getProblem() {
		MultiplyProblem mp = new MultiplyProblem(null, getRandomFactor(), getRandomFactor(), 0);
		return mp;
	}
	
	/*
	 * generate a random integer >=LOWER and <=UPPER 
	 */
	private int getRandomFactor() {
		return generator.nextInt(UPPER-LOWER+1)+LOWER;
	}

}

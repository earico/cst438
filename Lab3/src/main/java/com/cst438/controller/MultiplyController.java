package com.cst438.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cst438.dto.MultiplyProblem;
import com.cst438.service.RandomProblem;

//where is this
@RestController
@CrossOrigin
public class MultiplyController {
	
	@Autowired
	RandomProblem randomProblem;
	
	@GetMapping("/multiplication/new")
	public MultiplyProblem getProblem() {
		return randomProblem.getProblem();
	}
}
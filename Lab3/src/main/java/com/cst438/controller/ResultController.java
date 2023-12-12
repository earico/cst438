package com.cst438.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cst438.dto.MultiplyProblem;
import com.cst438.dto.MultiplyResult;
import com.cst438.service.MultiplyChecker;
import com.cst438.service.MultiplyHistory;
import com.cst438.service.MultiplyLevel;


@RestController
@CrossOrigin
public class ResultController {
	
	@Autowired
	private MultiplyChecker checker;
	
	@Autowired
	private MultiplyHistory history;
	
	@Autowired
	private MultiplyLevel level;

	
	@PostMapping("/result")
	public MultiplyResult check(@RequestBody MultiplyProblem mp) {
		System.out.println(mp);
		MultiplyResult mr = checker.check(mp);
		history.saveHistory(mp, mr);
		level.postMessageToLevel(mr);
		System.out.println(mr);
		return mr;
	}	
	
	@GetMapping("/result/{alias}")
	public MultiplyResult[] getLastNresults(
              @PathVariable("alias") String alias,
              @RequestParam("lastN") Optional<Integer> lastN) {
		int n = lastN.orElse(5);
		return history.getHistory(alias, n);
	}
	
	

}

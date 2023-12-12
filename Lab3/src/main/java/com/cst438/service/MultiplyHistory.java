package com.cst438.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.cst438.domain.Attempt;
import com.cst438.domain.AttemptRepository;
import com.cst438.domain.User;
import com.cst438.domain.UserRepository;
import com.cst438.dto.MultiplyProblem;
import com.cst438.dto.MultiplyResult;


@Service
public class MultiplyHistory {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AttemptRepository attemptRepository;


	/*
	 * save attempt 
	 */
	public void saveHistory(MultiplyProblem ma, MultiplyResult mr) {
		/*
		 * if user alias does not exist, then create one
		 */
		User user = userRepository.findByAlias(ma.alias());
		if (user == null) {
			user = new User();
			user.setAlias(ma.alias());
			userRepository.save(user);
		}
		/*
		 * save the attempt
		 */
		Attempt attempt = new Attempt();
		attempt.setAnswer(mr.answer());
		attempt.setAttempt(ma.attempt());
		attempt.setCorrect(mr.correct());
		attempt.setFactorA(mr.factorA());
		attempt.setFactorB(mr.factorB());
		attempt.setUser(user);
		attemptRepository.save(attempt);
	}
	
	/*
	* retrieve the lastN attempts made by the user
	 */
	public MultiplyResult[] getHistory(String alias, int lastN) {
		
		List<Attempt> attempts = 
                      attemptRepository.findByAliasLastN(
                            alias,  
                            PageRequest.of(0, lastN));
		MultiplyResult[] results = 
                      new MultiplyResult[attempts.size()];
		for (int i=0; i<results.length; i++) {
			Attempt a = attempts.get(i);
			results[i] = new MultiplyResult(
					a.getId(),
					alias, 
                              a.getFactorA(), 
                              a.getFactorB(), 
                              a.getAttempt(), 
                              a.getAnswer(),
                              a.isCorrect());
		}
		return results;
	}
}

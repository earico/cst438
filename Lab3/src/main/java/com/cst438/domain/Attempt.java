package com.cst438.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Attempt {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int factorA;
	private int factorB;
	private int attempt;
	private int answer;
	private boolean isCorrect;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
	
	public Attempt() {
		id = 0;
		factorA = 1;
		factorB = 1;
		attempt = 0;
		answer = 1;
		isCorrect = true;
	}
	
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	
	public int getFactorA() {return factorA;}
	public void setFactorA(int factorA) {this.factorA = factorA;}
	
	public int getFactorB() {return factorB;}
	public void setFactorB(int factorB) {this.factorB = factorB;}
	
	public int getAttempt() {return attempt;}
	public void setAttempt(int attempt) {this.attempt = attempt;}
	
	public int getAnswer() {return answer;}
	public void setAnswer(int answer) {this.answer = answer;}
	
	public boolean isCorrect() {return isCorrect;}
	public void setCorrect(boolean isCorrect) {this.isCorrect = isCorrect;}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
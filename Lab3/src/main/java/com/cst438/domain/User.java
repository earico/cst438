package com.cst438.domain;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="user_table")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String alias;
	private String firstName;
	private String lastName;
	private String email; 	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="user")
	private List<Attempt> attempts;
	private String level;
	
	public User() {
		id = 0;
		alias = "Alias";
		firstName = "John";
		lastName = "Doe";
		email = "jdoe@email.com";
	}
	
	int getID() {return this.id;}
	String getAlias() {return this.alias;}
	String getFirstName() {return this.firstName;}
	String getLastName() {return this.lastName;}
	String getEmail() {return this.email;}
	String getLevel() {return this.level;}
	
	public void setID(int iden) {this.id = iden;}
	public void setAlias(String al) {this.alias = al;}
	public void setFirstName(String fn) {this.firstName = fn;}
	public void setLastName(String ln) {this.lastName = ln;}
	public void setEmail(String em) {this.email = em;}
	public void setLevel(String lvl) {this.level = lvl;}
}
package com.fdm.model;

import javax.persistence.*;
import org.springframework.stereotype.Component;

/**
 * The class for users to log in with Contains a username and password for
 * authentication Has-a PlayerCharacter
 * 
 * @author KILA
 * @version 1.0
 *
 */
@Component
@Entity
@Table(name = "account")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	/**
	 * The username
	 */
	private String username;

	/**
	 * The password
	 */
	private String password;

	/**
	 * The playercharacter this account owns
	 */
	@OneToOne(optional = true) // (mappedBy="owner")
	PlayerCharacter playerCharacter;

	public PlayerCharacter getPlayerCharacter() {
		return playerCharacter;
	}

	public void setPlayerCharacter(PlayerCharacter playerCharacter) {
		this.playerCharacter = playerCharacter;
	}

	public Account() {
		super();
	}

	/**
	 * Makes a new account
	 * 
	 * @param username
	 * @param password
	 */
	public Account(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + "]";
	}

}

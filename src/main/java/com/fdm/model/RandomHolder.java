package com.fdm.model;

import java.util.Random;

import javax.persistence.Entity;

import org.springframework.stereotype.Component;

@Component
@Entity
public class RandomHolder {

	
	static RandomHolder instance;
	
	static RandomHolder getInstance() {
		if(instance == null)
			makeInstance();
		return instance;
	}
	
	private static void makeInstance() {
		instance = new RandomHolder();
		instance.seed = 2964590;
		instance.random = new Random(instance.seed);
		
	}

	public Random random;
	public long seed;
	
	
}

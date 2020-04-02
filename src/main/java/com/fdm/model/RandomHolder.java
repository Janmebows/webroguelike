package com.fdm.model;

import java.util.Random;



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

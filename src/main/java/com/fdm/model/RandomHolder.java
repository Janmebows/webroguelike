package com.fdm.model;

import java.util.Random;

import org.apache.log4j.Logger;



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
		Logger.getLogger("rootLogger").info("Instance of random holder generated");
	}

	public Random random;
	public long seed;
	
	
}

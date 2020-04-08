package com.fdm.model;

import java.util.Random;

import org.apache.log4j.Logger;

/**
 * Class to contain any randomness Is a singleton
 * 
 * @author KILA
 * @version 1.0
 *
 */
public class RandomHolder {

	/**
	 * The single instance
	 */
	static RandomHolder instance;

	/**
	 * the public getter If the instance is null, generate an instance
	 * 
	 * @return the single instance of RandomHolder
	 */
	public static RandomHolder getInstance() {
		if (instance == null)
			makeInstance();
		return instance;
	}

	/**
	 * Makes an instance of randomholder
	 */
	private static void makeInstance() {
		instance = new RandomHolder();
		instance.seed = 2964590;
		instance.random = new Random(instance.seed);
		Logger.getLogger("rootLogger").info("Instance of random holder generated");
	}

	/**
	 * The system random
	 */
	public Random random;
	/**
	 * The seed for the random
	 */
	public long seed;

}

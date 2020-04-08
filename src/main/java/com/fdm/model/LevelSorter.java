package com.fdm.model;

import java.util.Comparator;

public class LevelSorter implements Comparator<PlayerCharacter>{

	@Override
	public int compare(PlayerCharacter c1, PlayerCharacter c2) {
		if(c1.getLevel() >= c2.getLevel()) {
			return -1;
		} else {
			return +1;
		}
	}
	

}

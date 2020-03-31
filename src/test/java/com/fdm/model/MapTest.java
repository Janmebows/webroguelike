package com.fdm.model;


import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class MapTest {
	
	Map map;
	
	@Before
	public void init() {
		map = new Map();
	}
	
	@Test
	public void test_map_is_made_properly_and_can_get_elements() {
		assertEquals(1, map.get(0,0));
		assertEquals(0, map.get(1, 1));
	}
	
	@Test
	public void test_map_is_right_size() {
		assertEquals(map.XMAX, map.getMap().length);
		for(int i = 0; i < map.XMAX; i ++) {
			assertEquals(map.YMAX, map.getMap()[i].length);
		}
	}
	
	@Test 
	public void test_map_constants_correct() {
		assertEquals(MapDrawingConstants.WALL_SYMBOL, MapDrawingConstants.charFromValue(map.get(0,0)));
		assertEquals(MapDrawingConstants.EMPTY_SYMBOL, MapDrawingConstants.charFromValue(map.get(1,1)));
	}
	
	@Test
	public void test_map_constants_returns_empty_when_nonbinary_input() {
		assertEquals(' ', MapDrawingConstants.charFromValue(2));
	}
	
	
}

package com.fdm.model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

public class MapTest {


	List<Actor> mockActors;

	Logger mockLogger;
	
	Map map;

	@Before
	public void init() {
		mockActors = mock(List.class);
		mockLogger = mock(Logger.class);
		map = new Map("TestingMap");

	}

	/*** Test of map building with testing map ***/
	@Test
	public void test_map_is_made_properly_and_can_get_elements() {
		assertEquals(Tile.FULL_BLOCK, map.get(0, 0));
		assertEquals(Tile.PERIOD, map.get(1, 0));
		assertEquals(Tile.COMMA, map.get(2, 0));
	}

	@Test
	public void tile_getChar_method_works() {
		assertEquals(',', Tile.COMMA.getChar());
		assertEquals('█', Tile.FULL_BLOCK.getChar());
	}

	@Test
	public void tile_isblocking_method_works() {
		assertEquals(true, Tile.FULL_BLOCK.isBlocking());
		assertEquals(false, Tile.SPACE.isBlocking());
	}

	@Test
	public void test_tile_fallback() {
		assertEquals(Tile.FULL_BLOCK, Tile.tileFromChar('⏰'));
	}

	@Test
	public void test_map_is_right_size() {
		assertEquals(map.getxMax(), map.getMap().length);
		for (int i = 0; i < map.getxMax(); i++) {
			assertEquals(map.getyMax(), map.getMap()[i].length);
		}
	}

	@Test
	public void bad_tiles_are_blocked() {
		assertTrue(map.isBlocked(-1, 0));
		assertTrue(map.isBlocked(0, -1));
		assertTrue(map.isBlocked(-1, -1));
		assertTrue(map.isBlocked(map.getxMax(), 0));
		assertTrue(map.isBlocked(0, map.getyMax()));
		assertTrue(map.isBlocked(map.getxMax(), map.getyMax()));
	}

	@Test
	public void zerozero_is_blocked_since_it_is_a_wall() {
		assertTrue(map.isBlocked(0, 0));
	}

	/*** Finish testing map building ***/

	@Test
	public void test_actor_moving() {
		Actor mockActor = mock(Actor.class);
		map.tryMoveActor(mockActor, Direction.NONE);
	}



	@Test
	public void update_string_map() {
		Map map = new Map("TestingMap");
		PlayerCharacter plc = new PlayerCharacter("player1", 1, 1);
		plc.setCharacterSymbol('ñ');
		plc.setColor(255, 0, 0);
		map.addActor(plc);
		map.updateVisibleStringMap();
		for (int y = 0; y < map.getyMax(); ++y) {
			for (int x = 0; x < map.getxMax(); ++x) {
				System.out.print(map.getStringMap()[x][y]);
			}
			System.out.println();
		}
	}
}
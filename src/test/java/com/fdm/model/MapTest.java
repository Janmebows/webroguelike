package com.fdm.model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("unchecked")
public class MapTest {


	List<Actor> mockActors;

	Logger mockLogger;
	
	Map map;

	@Before
	public void init() {
		mockActors = mock(List.class);
		mockLogger = mock(Logger.class);
		map = new Map("TestingMap");
		map.actorList = mockActors;
		map.logger = mockLogger;

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
	
	@Test
	public void getStringMap_makes_stringMap_for_null() {
		Map map = new Map();
		assertNotNull(map.getStringMap());
	}
	@Test
	public void production_map_has_right_size() {
		map.readMapFromFile();
		assertEquals(40,map.getxMax());
		assertEquals(20,map.getyMax());
	}

	
	@Test
	public void map_id_is_zero_until_saved() {
		assertEquals(0,map.getId());
	}
	
	@Test
	public void read_map_logs_error_if_too_many_lines() {
		map.readMapFromFile("TooBigY");
		verify(mockLogger).error(anyString());
	}

	@Test
	public void add_and_remove_actors_work() {
		map.actorList = new ArrayList<Actor>();
		map.addActors(map.actorList);
		map.addActor(new Enemy("a",1,2));
		
		assertEquals(map.getActors().size(),1);
		map.remove(0);		
		assertEquals(map.getActors().size(),0);
	}
	
	@Test
	public void update_actors_works() {
		map.actorList = new ArrayList<Actor>();
		Actor enemy = new Enemy("a",1,2);
		map.addActor(enemy);
		assertEquals(map.getActors().size(),1);
		map.remove(0);		
		enemy.alive = false;
		map.updateActors();
		assertEquals(map.getActors().size(),0);
		verify(mockLogger).trace(anyString());
	}

}
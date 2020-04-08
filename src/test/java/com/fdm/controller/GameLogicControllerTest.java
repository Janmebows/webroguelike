package com.fdm.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fdm.model.Actor;
import com.fdm.model.Coord;
import com.fdm.model.Enemy;
import com.fdm.model.Map;
import com.fdm.model.PlayerCharacter;

import static org.mockito.Mockito.*;

public class GameLogicControllerTest {
	
	@Mock
	Map mockMap;
	@Mock
	Enemy mockActor1;
	@Mock
	Actor mockActor2;
	@Mock
	PlayerCharacter mockActor3;
	@Mock
	Actor mockActor4;
	@Mock
	PlayerCharacter mockActor5;
	
	List<Actor> actorList;

	GameLogicController gameLogicController;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		actorList = new ArrayList<Actor>();
		actorList.add(mockActor1);
		actorList.add(mockActor2);
		actorList.add(mockActor3);
		gameLogicController = new GameLogicController(mockMap, actorList);
	}
	
	@Test
	public void test_can_find_actor_by_id() {
		when(mockActor1.getId()).thenReturn(1);
		when(mockActor2.getId()).thenReturn(2);
		when(mockActor3.getId()).thenReturn(3);
		
		Actor result = gameLogicController.findActor(2);
		assertEquals(mockActor2, result);
	}
	
	@Test
	public void test_can_add_actor() {
		gameLogicController.addActor(mockActor4);
		verify(mockActor4).setMap(mockMap);
		assertEquals(4,actorList.size());
	}
	
	@Test
	public void test_can_remove_actor() {
		when(mockActor1.getId()).thenReturn(1);
		when(mockActor2.getId()).thenReturn(2);
		when(mockActor3.getId()).thenReturn(3);

		gameLogicController.removeActor(mockActor3);
		verify(mockMap).remove(3);
		assertEquals(2, actorList.size());
		
	}
	
	@Test
	public void test_can_remove_enemy() {
		
		Coord coord = new Coord(1,1);
		List<Coord> validTiles = new ArrayList<Coord>();
		validTiles.add(coord);
		
		when(mockActor1.getId()).thenReturn(1);
		when(mockActor2.getId()).thenReturn(2);
		when(mockActor3.getId()).thenReturn(3);
		when(mockMap.validTiles()).thenReturn(validTiles);

		gameLogicController.removeActor(mockActor1);
		verify(mockMap).remove(1);
		assertEquals(3, actorList.size());
		
	}
	
	@Test
	public void test_try_act_actor_present() {
		
		when(mockActor1.getId()).thenReturn(1);
		when(mockActor2.getId()).thenReturn(2);
		when(mockActor3.getId()).thenReturn(3);
		
		gameLogicController.tryAddActor(mockActor3);
		
		assertEquals(3, actorList.size());	
		
	}
	
	@Test
	public void test_try_act_actor_not_present() {
		
		when(mockActor1.getId()).thenReturn(1);
		when(mockActor2.getId()).thenReturn(2);
		when(mockActor3.getId()).thenReturn(3);
		when(mockActor5.getId()).thenReturn(5);
		
		gameLogicController.tryAddActor(mockActor5);
		
		assertEquals(4, actorList.size());	
		
	}

	
}

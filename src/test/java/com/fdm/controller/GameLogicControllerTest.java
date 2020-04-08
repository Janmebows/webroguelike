package com.fdm.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fdm.model.Actor;
import com.fdm.model.Map;

public class GameLogicControllerTest {
	
	@Mock
	Map mockMap;
	@Mock
	Actor mockActor1;
	@Mock
	Actor mockActor2;
	@Mock
	Actor mockActor3;
	
	List<Actor> actorList = new ArrayList<Actor>();

	GameLogicController gameLogicController;
	
	@Before
	public void init() {
		
	}
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}

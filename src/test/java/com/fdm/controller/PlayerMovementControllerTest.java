package com.fdm.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import com.fdm.model.Direction;
import com.fdm.model.Map;
import com.fdm.model.PlayerCharacter;
import com.fdm.model.Tile;


public class PlayerMovementControllerTest {
	
	
	@Mock 
	PlayerCharacter mockChar;
	
	@Mock
	Map mockMap;

	@InjectMocks
	PlayerCharacterInputController controller = new PlayerCharacterInputController(mockChar);
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void test_translates_input_correctly() {
		assertEquals(Direction.DOWN, controller.processInput("s"));
		assertEquals(Direction.UP, controller.processInput("w"));
		assertEquals(Direction.LEFT, controller.processInput("a"));
		assertEquals(Direction.RIGHT, controller.processInput("d"));
		assertEquals(Direction.NONE, controller.processInput("sadg"));
	}
	
}

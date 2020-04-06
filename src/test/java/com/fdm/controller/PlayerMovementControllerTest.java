package com.fdm.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.fdm.model.Direction;
import com.fdm.model.Map;
import com.fdm.model.PlayerCharacter;


public class PlayerMovementControllerTest {
	
	
	
	@Mock
	Map mockMap;

	@InjectMocks
	PlayerCharacter pc;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void input_translates_down_input_correctly() {
		pc.setInput('s');
		assertEquals(Direction.DOWN, pc.nextDirection);
	}
	@Test
	public void input_translates_right_input_correctly() {
		pc.setInput('d');
		assertEquals(Direction.RIGHT, pc.nextDirection);
	}
	@Test
	public void input_translates_left_input_correctly() {
		pc.setInput('a');
		assertEquals(Direction.LEFT, pc.nextDirection);
	}
	@Test
	public void input_translates_up_input_correctly() {
		pc.setInput('w');
		assertEquals(Direction.UP, pc.nextDirection);
//		assertEquals(Direction.UP, pc.processInput("w"));
//		assertEquals(Direction.LEFT, pc.processInput("a"));
//		assertEquals(Direction.RIGHT, pc.processInput("d"));
//		assertEquals(Direction.NONE, pc.processInput("sadg"));
	}

	@Test
	public void input_translates_fails_on_other() {
		pc.setInput(' ');
		assertEquals(Direction.NONE, pc.nextDirection);
		pc.setInput('b');
		assertEquals(Direction.NONE, pc.nextDirection);
		pc.setInput('c');
		assertEquals(Direction.NONE, pc.nextDirection);
		pc.setInput('f');
		assertEquals(Direction.NONE, pc.nextDirection);
	}
	
}

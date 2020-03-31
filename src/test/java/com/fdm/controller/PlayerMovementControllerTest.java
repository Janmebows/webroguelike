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

import PlaceholderTesting.IView;

public class PlayerMovementControllerTest {
	
	@Mock
	IView mockView;
	
	@Mock 
	PlayerCharacter mockChar;
	
	@Mock
	Map mockMap;

	@InjectMocks
	PlayerMovementController controller = new PlayerMovementController(mockMap, mockChar, mockView);
	
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
	
	@Test
	public void test_controller_starts_and_quits() {
		when(mockChar.isAtPosition(anyInt(), anyInt())).thenReturn(false);
		when(mockMap.get(anyInt(), anyInt())).thenReturn(0);
		when(mockView.getUserInput()).thenReturn("Q");
		
		controller.handle();
		
		verify(mockView).getUserInput();
	}
	
	@Test
	public void test_controller_starts_movesup_and_quits() {
		when(mockChar.isAtPosition(anyInt(), anyInt())).thenReturn(false);
		when(mockMap.get(anyInt(), anyInt())).thenReturn(0);
		when(mockView.getUserInput()).thenReturn("w","Q");
		
		controller.handle();
		
		verify(mockView, times(2)).getUserInput();
		verify(mockChar).moveChar(Direction.UP);
	}
	
	@Test
	public void test_controller_shows_playerChar() {
		when(mockChar.isAtPosition(anyInt(), anyInt())).thenReturn(false);
		when(mockChar.isAtPosition(2, 5)).thenReturn(true);
		when(mockMap.get(anyInt(), anyInt())).thenReturn(0);
		when(mockView.getUserInput()).thenReturn("Q");
		
		controller.handle();
		
		verify(mockView, times(1)).getUserInput();
	}

}

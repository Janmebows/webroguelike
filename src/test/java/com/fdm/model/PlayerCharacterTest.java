package com.fdm.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class PlayerCharacterTest {

	Account account;
	PlayerCharacter playerChar;
	Map map;

	@Before
	public void init() {
		account = new Account("testAccount", "testPass");
		playerChar = new PlayerCharacter("testChar", new Object());
		map = new Map("MovementTestingMap");
		playerChar.setMap(map);
	}

	
	
	
	@Test
	public void test_can_update_position() {
		playerChar.updatePosition(3, 2);
		assertEquals(3, playerChar.getX());
		assertEquals(2, playerChar.getY());
	}

	@Test
	public void test_can_check_position() {
		assertTrue (playerChar.isAtPosition(1, 1));
	}

	@Test
	public void test_can_move_up() {
		playerChar.updatePosition(1, 2);
		map.tryMoveActor(playerChar,Direction.UP);
		assertTrue (playerChar.isAtPosition(1,1));
	}

	@Test
	public void test_can_move_multiple_directions() {
		playerChar.updatePosition(2,1);
		map.tryMoveActor(playerChar,Direction.UP);
		map.tryMoveActor(playerChar,Direction.RIGHT);
		map.tryMoveActor(playerChar,Direction.RIGHT);
		map.tryMoveActor(playerChar,Direction.RIGHT);
		map.tryMoveActor(playerChar,Direction.DOWN);
		map.tryMoveActor(playerChar,Direction.LEFT);
		map.tryMoveActor(playerChar,Direction.LEFT);

		assertTrue (playerChar.isAtPosition(1,2));
	}

	@Test
	public void test_char_doesnt_move_if_wall_up() {
		map.tryMoveActor(playerChar,Direction.UP);
		assertTrue (playerChar.isAtPosition(1, 1));
	}

	@Test
	public void test_char_doesnt_move_if_wall_down_left() {
		playerChar.updatePosition(1, 8);
		map.tryMoveActor(playerChar,Direction.LEFT);
		map.tryMoveActor(playerChar,Direction.DOWN);
		assertTrue (playerChar.isAtPosition(1, 8));
	}

	@Test
	public void test_char_doesnt_move_into_wall_and_moves_fine_after() {
		playerChar.updatePosition(3, 2);
		map.tryMoveActor(playerChar,Direction.RIGHT);
		map.tryMoveActor(playerChar,Direction.UP);
		assertTrue (playerChar.isAtPosition(3,1));
	}

	@Test
	public void test_char_wont_move_if_no_direction() {
		map.tryMoveActor(playerChar,Direction.NONE);
		assertTrue (playerChar.isAtPosition(1, 1));
	}

	@Test
	public void test_print_html() {
		playerChar.setCharacterSymbol('%');
		playerChar.setColor(255,0,0);
		assertEquals("<p style=\"color: #ff0000;\">%</p>",playerChar.getHtmlString());
	}
}

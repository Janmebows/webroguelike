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
		playerChar = new PlayerCharacter("testChar",1,1);
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
	public void test_char_doesnt_move_if_wall_up() {
		map.tryMoveActor(playerChar,Direction.UP);
		assertTrue (playerChar.isAtPosition(1, 1));
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
		assertEquals("<p style=\"padding: 0; margin: 0; color: #ff0000;\">%</p>",playerChar.getHtmlString());
	}
}

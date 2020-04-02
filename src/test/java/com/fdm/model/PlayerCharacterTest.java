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
		playerChar = new PlayerCharacter("testChar", account);
		map = new Map();
		playerChar.setMap(map);
	}

	@Test
	public void test_can_update_position() {
		playerChar.updatePosition(2, 5);
		assertEquals(2, playerChar.getX());
		assertEquals(5, playerChar.getY());
	}

	@Test
	public void test_can_check_position() {
		assert (playerChar.isAtPosition(1, 1));
	}

	@Test
	public void test_can_move_up() {
		playerChar.updatePosition(2, 5);
		playerChar.moveChar(Direction.UP);
		assert (playerChar.isAtPosition(2, 4));
	}

	@Test
	public void test_can_move_multiple_directions() {
		playerChar.updatePosition(2, 5);
		playerChar.moveChar(Direction.UP);
		playerChar.moveChar(Direction.RIGHT);
		playerChar.moveChar(Direction.RIGHT);
		playerChar.moveChar(Direction.RIGHT);
		playerChar.moveChar(Direction.DOWN);
		playerChar.moveChar(Direction.LEFT);

		assert (playerChar.isAtPosition(4, 5));
	}

	@Test
	public void test_char_doesnt_move_if_wall_up() {
		playerChar.moveChar(Direction.UP);
		assert (playerChar.isAtPosition(1, 1));
	}

	@Test
	public void test_char_doesnt_move_if_wall_down_left() {
		playerChar.updatePosition(1, 8);
		playerChar.moveChar(Direction.LEFT);
		playerChar.moveChar(Direction.DOWN);
		assert (playerChar.isAtPosition(1, 8));
	}

	@Test
	public void test_char_doesnt_move_if_wall_right_and_moves_fine_after() {
		playerChar.updatePosition(18, 5);
		playerChar.moveChar(Direction.RIGHT);
		playerChar.moveChar(Direction.DOWN);
		assert (playerChar.isAtPosition(18, 6));
	}

	@Test
	public void test_char_wont_move_if_no_direction() {
		playerChar.moveChar(Direction.NONE);
		assert (playerChar.isAtPosition(1, 1));
	}

}

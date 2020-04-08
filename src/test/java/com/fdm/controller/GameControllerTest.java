package com.fdm.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import com.fdm.dal.AccountRepository;
import com.fdm.dal.ActorRepository;
import com.fdm.dal.EnemyRepository;
import com.fdm.dal.MapRepository;
import com.fdm.model.Account;
import com.fdm.model.Map;
import com.fdm.model.Coord;
import com.fdm.model.PlayerCharacter;

@SuppressWarnings("static-access")
public class GameControllerTest {
	
	@Mock
	AccountRepository mockAccountRepo;
	@Mock
	ActorRepository mockActorRepo;
	@Mock
	EnemyRepository mockEnemyRepo;
	@Mock
	MapRepository mockMapRepo;
	@Mock
	Map mockMap;
	@Mock
	PlayerCharacter mockPC;
	@Mock
	Account mockAccount;
	
	
	@Mock
	SimpMessagingTemplate mockTemplate;
	
	@Mock 
	GameLogicController mockGameLogicController;
	
	@InjectMocks
	GameController controller = new GameController();
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void test_scheduled_method_sends_map() {
		
		mockGameLogicController.instance = mockGameLogicController;
		
		String[][] testMap = new String[][] {{"00","10","20"},{"01","11","21"},{"02","12","22"}};
		when(mockGameLogicController.getMap()).thenReturn(mockMap);
		when(mockMap.getStringMap()).thenReturn(testMap);
		//when(mockGameLogicController.getInstance()).thenReturn(mockGameLogicController);
		controller.autoUpdateMap();
		
		InOrder inOrder = inOrder(mockGameLogicController, mockTemplate);
		inOrder.verify(mockGameLogicController, times(2)).getMap();
		inOrder.verify(mockTemplate).convertAndSend("/topic/game", testMap);	
	}
	
	@Test
	public void test_postGame_postsmap() {
		mockGameLogicController.instance = mockGameLogicController;
		String[][] testMap = new String[][] {{"00","10","20"},{"01","11","21"},{"02","12","22"}};
		when(mockGameLogicController.getMap()).thenReturn(mockMap);
		when(mockMap.getStringMap()).thenReturn(testMap);
		
		String[][] result = controller.postGame();
		assertArrayEquals(testMap, result);
		
	}
	
	@Test
	public void test_postGame_doesntpostsmap_if_null() {
		mockGameLogicController.instance = mockGameLogicController;
		String[][] testMap = new String[][] {{"00","10","20"},{"01","11","21"},{"02","12","22"}};
		when(mockGameLogicController.getMap()).thenReturn(null);
		when(mockMap.getStringMap()).thenReturn(testMap);
		
		String[][] result = controller.postGame();
		assertNull(result);
		
	}
	
	@Test
	public void test_disconnect_removes_actor() {
		mockGameLogicController.instance = mockGameLogicController;
		
		controller.disconnect(mockPC);
		
		verify(mockGameLogicController).removeActor(mockPC);
	}
	
	@Test
	public void test_input_sets_up_pc_to_move_null_return_for_controller() {
		mockGameLogicController.instance = mockGameLogicController;
		mockPC.nextInput = 'w';
		when(mockPC.getId()).thenReturn(1);
		when(mockGameLogicController.findActor(1)).thenReturn(null);
		
		when(mockActorRepo.findById(1)).thenReturn(Optional.of(mockPC));
		
		controller.updatePlayer(mockPC);
		
		InOrder inOrder = inOrder(mockActorRepo, mockPC, mockGameLogicController);
		inOrder.verify(mockGameLogicController).addActor(mockPC);
		inOrder.verify(mockPC).setInput('w');
		inOrder.verify(mockActorRepo).save(mockPC);
		
	}
	
	@Test
	public void test_input_sets_up_pc_to_move() {
		mockGameLogicController.instance = mockGameLogicController;
		mockPC.nextInput = 'w';
		when(mockPC.getId()).thenReturn(1);
		when(mockGameLogicController.findActor(1)).thenReturn(mockPC);
		
		when(mockActorRepo.findById(1)).thenReturn(Optional.of(mockPC));
		
		controller.updatePlayer(mockPC);
		
		InOrder inOrder = inOrder(mockActorRepo, mockPC, mockGameLogicController);
		inOrder.verify(mockGameLogicController, times(0)).addActor(mockPC);
		inOrder.verify(mockPC).setInput('w');
		inOrder.verify(mockActorRepo).save(mockPC);
		
	}
	
	@Test
	public void test_connect_returns_null_if_no_account() {
				
		when(mockAccount.getId()).thenReturn(1);
		when(mockAccountRepo.findById(1)).thenReturn(Optional.empty());
		
		Account result = controller.connect(mockAccount);
		assertNull(result);
		
	}
	
	@Test
	public void test_connect_returns_account() {
		
		when(mockAccount.getId()).thenReturn(1);
		when(mockAccountRepo.findById(1)).thenReturn(Optional.of(mockAccount));
		when(mockAccount.getPlayerCharacter()).thenReturn(mockPC);
		when(mockPC.getId()).thenReturn(1);
		mockGameLogicController.instance = mockGameLogicController;
		when(mockActorRepo.findById(1)).thenReturn(Optional.of(mockPC));
		when(mockActorRepo.save(mockPC)).thenReturn(mockPC);
		when(mockAccountRepo.save(mockAccount)).thenReturn(mockAccount);
		when(mockAccount.getUsername()).thenReturn("hi");
		when(mockGameLogicController.getMap()).thenReturn(mockMap);
		
		
		Account result = controller.connect(mockAccount);
		InOrder inOrder = inOrder(mockGameLogicController, mockActorRepo, mockAccount, mockAccountRepo);
		inOrder.verify(mockGameLogicController).tryAddActor(mockPC);
		inOrder.verify(mockActorRepo).save(any(PlayerCharacter.class));
		inOrder.verify(mockAccount).setPlayerCharacter(mockPC);
		inOrder.verify(mockAccountRepo).save(mockAccount);
		assertEquals(mockAccount, result);
		
	}
	
	@Test
	public void test_connect_returns_account_no_controller() {
		
		when(mockAccount.getId()).thenReturn(1);
		when(mockAccountRepo.findById(1)).thenReturn(Optional.of(mockAccount));
		when(mockAccount.getPlayerCharacter()).thenReturn(mockPC);
		when(mockPC.getId()).thenReturn(1);
		//mockGameLogicController.instance = mockGameLogicController;
		when(mockActorRepo.findById(1)).thenReturn(Optional.of(mockPC));
		when(mockActorRepo.save(mockPC)).thenReturn(mockPC);
		when(mockAccountRepo.save(mockAccount)).thenReturn(mockAccount);
		
		Account result = controller.connect(mockAccount);
		InOrder inOrder = inOrder(mockActorRepo, mockAccount, mockAccountRepo);
		inOrder.verify(mockActorRepo).save(mockPC);
		inOrder.verify(mockAccount).setPlayerCharacter(mockPC);
		inOrder.verify(mockAccountRepo).save(mockAccount);
		assertEquals(mockAccount, result);
		
	}
	
	@Test
	public void test_connect_returns_account_no_pc() {
		
		
		Coord coord = new Coord(1,1);
		List<Coord> validTiles = new ArrayList<Coord>();
		validTiles.add(coord);
		
		when(mockAccount.getId()).thenReturn(1);
		when(mockAccountRepo.findById(1)).thenReturn(Optional.of(mockAccount));
		when(mockAccount.getPlayerCharacter()).thenReturn(mockPC);
		when(mockPC.getId()).thenReturn(0);
		mockGameLogicController.instance = mockGameLogicController;
		when(mockActorRepo.findById(1)).thenReturn(Optional.of(mockPC));
		when(mockActorRepo.save(mockPC)).thenReturn(mockPC);
		when(mockAccountRepo.save(mockAccount)).thenReturn(mockAccount);
		when(mockAccount.getUsername()).thenReturn("hi");
		when(mockGameLogicController.getMap()).thenReturn(mockMap);
		when(mockMap.validTiles()).thenReturn(validTiles);
		
		Account result = controller.connect(mockAccount);
		InOrder inOrder = inOrder(mockActorRepo, mockAccount, mockAccountRepo);
		inOrder.verify(mockAccount, times(1)).setPlayerCharacter(any(PlayerCharacter.class));
		inOrder.verify(mockActorRepo).save(any(PlayerCharacter.class));
		inOrder.verify(mockAccountRepo).save(mockAccount);
		assertEquals(mockAccount, result);
		
	}
	
	

}

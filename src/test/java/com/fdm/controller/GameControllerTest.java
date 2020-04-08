package com.fdm.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.mockito.Mockito.*;

import com.fdm.dal.AccountRepository;
import com.fdm.dal.EnemyRepository;
import com.fdm.dal.MapRepository;


public class GameControllerTest {
	
	@Mock
	AccountRepository mockAccountRepo;
	@Mock
	EnemyRepository mockEnemyRepo;
	@Mock
	MapRepository mockMapRepo;
	
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
		controller.autoUpdateMap();
		
		verify(mockGameLogicController).getInstance();
		
		
	}

}

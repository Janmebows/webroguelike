package com.fdm.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import com.fdm.dal.PlayerCharacterRepository;
import com.fdm.model.PlayerCharacter;
import com.fdm.model.SearchFilters;

public class SearchControllerTest {
	
	@Mock
	PlayerCharacterRepository mockPlayerCharRepo;
	
	@Mock
	PlayerCharacter mockChar1;
	@Mock
	PlayerCharacter mockChar2;
	@Mock 
	PlayerCharacter mockChar3;
	@Mock
	SearchFilters mockSearch;
	
	@InjectMocks
	SearchController controller = new SearchController();

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void test_go_to_search_page() {
		String result = controller.getSearchPage();
		assertEquals("search", result);
	}
	
	@Test
	public void test_find_all_finds_all() {
		List<PlayerCharacter> playerList = new ArrayList<PlayerCharacter>();
		playerList.add(mockChar1);
		playerList.add(mockChar2);
		playerList.add(mockChar3);
		
		when(mockPlayerCharRepo.findAll()).thenReturn(playerList);
		List<PlayerCharacter> result = controller.findAllPlayers();
		assertEquals(3, result.size());
	}
	
	@Test
	public void test_process_search_correctly_reads_filters_greatergreater() {
		
		when(mockSearch.getName()).thenReturn("hi");
		when(mockSearch.getLevel()).thenReturn(1);
		when(mockSearch.getKillCount()).thenReturn(1);
		when(mockSearch.getLevelDirection()).thenReturn("greaterThan");
		when(mockSearch.getKillDirection()).thenReturn("greaterThan");
		
		List<PlayerCharacter> playerList = new ArrayList<PlayerCharacter>();
		playerList.add(mockChar1);
		
		when(mockPlayerCharRepo.findByCharacterNameContainingAndLevelGreaterThanAndKillCountGreaterThan("hi", 1, 1)).thenReturn(playerList);
		
		List<PlayerCharacter> result = controller.processSearch(mockSearch);
		verify(mockPlayerCharRepo).findByCharacterNameContainingAndLevelGreaterThanAndKillCountGreaterThan("hi", 1, 1);
		assertEquals(1, result.size());
	}
	
	@Test
	public void test_process_search_correctly_reads_filters_greaterless() {
		
		when(mockSearch.getName()).thenReturn("hi");
		when(mockSearch.getLevel()).thenReturn(1);
		when(mockSearch.getKillCount()).thenReturn(1);
		when(mockSearch.getLevelDirection()).thenReturn("greaterThan");
		when(mockSearch.getKillDirection()).thenReturn("lessThan");
		
		List<PlayerCharacter> playerList = new ArrayList<PlayerCharacter>();
		playerList.add(mockChar1);
		
		when(mockPlayerCharRepo.findByCharacterNameContainingAndLevelGreaterThanAndKillCountLessThan("hi", 1, 1)).thenReturn(playerList);
		
		List<PlayerCharacter> result = controller.processSearch(mockSearch);
		verify(mockPlayerCharRepo).findByCharacterNameContainingAndLevelGreaterThanAndKillCountLessThan("hi", 1, 1);
		assertEquals(1, result.size());
	}
	
	@Test
	public void test_process_search_correctly_reads_filters_lessgreater() {
		
		when(mockSearch.getName()).thenReturn("hi");
		when(mockSearch.getLevel()).thenReturn(1);
		when(mockSearch.getKillCount()).thenReturn(1);
		when(mockSearch.getLevelDirection()).thenReturn("lessThan");
		when(mockSearch.getKillDirection()).thenReturn("greaterThan");
		
		List<PlayerCharacter> playerList = new ArrayList<PlayerCharacter>();
		playerList.add(mockChar1);
		
		when(mockPlayerCharRepo.findByCharacterNameContainingAndLevelLessThanAndKillCountGreaterThan("hi", 1, 1)).thenReturn(playerList);
		
		List<PlayerCharacter> result = controller.processSearch(mockSearch);
		verify(mockPlayerCharRepo).findByCharacterNameContainingAndLevelLessThanAndKillCountGreaterThan("hi", 1, 1);
		assertEquals(1, result.size());
	}
	
	@Test
	public void test_process_search_correctly_reads_filters_lessless() {
		
		when(mockSearch.getName()).thenReturn("hi");
		when(mockSearch.getLevel()).thenReturn(1);
		when(mockSearch.getKillCount()).thenReturn(1);
		when(mockSearch.getLevelDirection()).thenReturn("lessThan");
		when(mockSearch.getKillDirection()).thenReturn("lessThan");
		
		List<PlayerCharacter> playerList = new ArrayList<PlayerCharacter>();
		playerList.add(mockChar1);
		
		when(mockPlayerCharRepo.findByCharacterNameContainingAndLevelLessThanAndKillCountLessThan("hi", 1, 1)).thenReturn(playerList);
		
		List<PlayerCharacter> result = controller.processSearch(mockSearch);
		verify(mockPlayerCharRepo).findByCharacterNameContainingAndLevelLessThanAndKillCountLessThan("hi", 1, 1);
		assertEquals(1, result.size());
	}

}

package com.fdm.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import com.fdm.dal.AccountRepository;
import com.fdm.dal.PlayerCharacterRepository;
import com.fdm.model.Account;
import com.fdm.model.PlayerCharacter;

public class RegisterControllerTest {
	
	@Mock
	AccountRepository mockAccountRepository;
	@Mock
	PlayerCharacterRepository mockPCharRepo;
	
	@Mock
	Account mockAccount;
	
	@InjectMocks
	RegisterController controller = new RegisterController();
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void test_getRegister() {
		String result = controller.getRegister();
		assertEquals("register", result);
	}
	
	@Test
	public void test_postAccount_called_with_no_account_returns_null() {
		Account result = controller.postAccount(null);
		assertNull(result);
	}
	
	@Test
	public void test_postAccount_called_with_no_username_or_pword_returns_null() {
		Account result = controller.postAccount(mockAccount);
		assertNull(result);
	}
	
	
	@Test
	public void test_postAccount_called_with_no_username_returns_null() {
		when(mockAccount.getUsername()).thenReturn(null);
		when(mockAccount.getPassword()).thenReturn("pword");
		
		Account result = controller.postAccount(mockAccount);
		assertNull(result);
	}
	
	@Test
	public void test_postAccount_called_with_no_password_returns_null() {
		when(mockAccount.getUsername()).thenReturn("uname");
		when(mockAccount.getPassword()).thenReturn(null);
		
		Account result = controller.postAccount(mockAccount);
		assertNull(result);
	}
	
	@Test
	public void test_postAccount_called_with_empty_username_returns_null() {
		when(mockAccount.getUsername()).thenReturn("");
		when(mockAccount.getPassword()).thenReturn("pword");
		
		Account result = controller.postAccount(mockAccount);
		assertNull(result);
	}
	
	@Test
	public void test_postAccountn_called_with_empty_password_returns_null() {
		when(mockAccount.getUsername()).thenReturn("uname");
		when(mockAccount.getPassword()).thenReturn("");
		
		Account result = controller.postAccount(mockAccount);
		assertNull(result);
	}
	
	@Test
	public void test_postAccount_returns_null_if_accountRepo_finds_something() {
		List<Account> accountList = new ArrayList<Account>();
		accountList.add(mockAccount);
		
		when(mockAccount.getUsername()).thenReturn("uname");
		when(mockAccount.getPassword()).thenReturn("pword");
		when(mockAccountRepository.findByUsername("uname")).thenReturn(accountList);
	
		Account result = controller.postAccount(mockAccount);
		assertNull(result);
	}
	
	

}

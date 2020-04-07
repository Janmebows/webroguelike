package com.fdm.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.fdm.dal.AccountRepository;
import com.fdm.model.Account;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

public class LoginControllerTest {
	
	@Mock
	AccountRepository mockAccountRepository;
	
	@Mock
	Account mockAccount;
	@Mock
	Account mockAccount2;
	
	@InjectMocks
	LoginController controller = new LoginController();

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void test_function_called_with_no_account_returns_null() {
		Account result = controller.postLogin(null);
		assertNull(result);
	}
	
	@Test
	public void test_function_called_with_no_username_or_pword_returns_null() {
		Account result = controller.postLogin(mockAccount);
		assertNull(result);
	}
	
	
	@Test
	public void test_function_called_with_no_username_returns_null() {
		when(mockAccount.getUsername()).thenReturn(null);
		when(mockAccount.getPassword()).thenReturn("pword");
		
		Account result = controller.postLogin(mockAccount);
		assertNull(result);
	}
	
	@Test
	public void test_function_called_with_no_password_returns_null() {
		when(mockAccount.getUsername()).thenReturn("uname");
		when(mockAccount.getPassword()).thenReturn(null);
		
		Account result = controller.postLogin(mockAccount);
		assertNull(result);
	}
	
	@Test
	public void test_function_called_with_empty_username_returns_null() {
		when(mockAccount.getUsername()).thenReturn("");
		when(mockAccount.getPassword()).thenReturn("pword");
		
		Account result = controller.postLogin(mockAccount);
		assertNull(result);
	}
	
	@Test
	public void test_function_called_with_empty_password_returns_null() {
		when(mockAccount.getUsername()).thenReturn("uname");
		when(mockAccount.getPassword()).thenReturn("");
		
		Account result = controller.postLogin(mockAccount);
		assertNull(result);
	}
	
	@Test
	public void test_function_searches_repository_by_username_and_password() {
		
		List<Account> accountList = new ArrayList<Account>();
		accountList.add(mockAccount2);
		accountList.add(mockAccount);
		
		when(mockAccount.getUsername()).thenReturn("uname");
		when(mockAccount.getPassword()).thenReturn("pword");
		when(mockAccountRepository.findByUsernameAndPassword("uname", "pword")).thenReturn(accountList);
		
		
		Account result = controller.postLogin(mockAccount);
		assertEquals(mockAccount2, result);	
	}
	
	@Test
	public void test_function_returns_null_if_no_found_accounts() {
		
		List<Account> accountList = new ArrayList<Account>();
		
		when(mockAccount.getUsername()).thenReturn("uname");
		when(mockAccount.getPassword()).thenReturn("pword");
		when(mockAccountRepository.findByUsernameAndPassword("uname", "pword")).thenReturn(accountList);
		
		
		Account result = controller.postLogin(mockAccount);
		assertNull(result);	
	}
}

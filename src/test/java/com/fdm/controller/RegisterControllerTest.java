package com.fdm.controller;

import javax.servlet.http.HttpSession;

import org.junit.*;
import org.mockito.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import org.springframework.ui.Model;

import com.fdm.model.Account;

public class RegisterControllerTest {
	
	RegisterController controller = new RegisterController();
	
	@Mock
	HttpSession mockSession;
	@Mock
	Model mockModel;
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	
	
	@Test
	public void when_getRequestForRegister_withActiveSession_thenReturnHome() {
		
		when(mockSession.getAttribute("activeUser")).thenReturn(new Account());

		String nextPage = controller.getRegister(mockSession,mockModel);
		verify(mockSession,times(1)).getAttribute("activeUser");
		
		assertEquals("home",nextPage);
	}
}

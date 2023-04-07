package com.example.allan.users;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.allan.exceptions.ResourceNotFoundException;

class UserControllerTests {

	@Mock
	private UserService userService;
	private UserController userController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		userController = new UserController(userService);
	}

	@Test
	void testGetAllUsers() {
		User user1 = new User(1L, "Allan", "allanpaulourbiztondo@gmail.com", true);
		User user2 = new User(2L, "Paulo", "allanpaulourbiztondo+1@gmail.com", false);
		List<User> userList = Arrays.asList(user1, user2);
		when(userService.getAllUsers()).thenReturn(userList);
		ResponseEntity<List<User>> response = userController.getAllUsers();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(userList, response.getBody());
	}

	@Test
	void testGetUserById() throws ResourceNotFoundException {
		Long id = 1L;
		User user = new User(id, "Allan", "allanpaulourbiztondo@gmail.com", true);
		when(userService.getUserById(id)).thenReturn(user);
		ResponseEntity<User> response = userController.getUserById(id);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(user, response.getBody());
	}

	@Test
	void testGetUserByIdNotFound() throws ResourceNotFoundException {
		Long id = 1L;
		when(userService.getUserById(id)).thenThrow(new ResourceNotFoundException("User not found with id " + id));
		ResponseEntity<User> response = userController.getUserById(id);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNull(response.getBody());
	}

	@Test
	void testCreateUser() {
		User user = new User(1L, "Allan", "allanpaulourbiztondo@gmail.com", true);
		when(userService.createUser(user)).thenReturn(user);
		ResponseEntity<User> response = userController.createUser(user);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(user, response.getBody());
	}

	@Test
	void testUpdateUser() throws ResourceNotFoundException {
		Long id = 1L;
		User user = new User(id, "Allan", "allanpaulourbiztondo@gmail.com", true);
		User updatedUser = new User(id, "Allan Paulo", "allanpaulourbiztondo+1@gmail.com", true);
		when(userService.updateUser(id, user)).thenReturn(updatedUser);
		ResponseEntity<User> response = userController.updateUser(id, user);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(updatedUser, response.getBody());
	}

	@Test
	void testUpdateUserNotFound() throws ResourceNotFoundException {
		Long id = 1L;
		User user = new User(id, "Allan", "allanpaulourbiztondo@gmail.com", true);
		when(userService.updateUser(id, user)).thenThrow(new ResourceNotFoundException("User not found with id " + id));
		ResponseEntity<User> response = userController.updateUser(id, user);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNull(response.getBody());
	}

	@Test
	void testDeleteUser() throws ResourceNotFoundException {
		Long id = 1L;
		ResponseEntity<Void> response = userController.deleteUser(id);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		verify(userService, times(1)).deleteUser(id);
	}

	@Test
	void testDeleteUserNotFound() throws ResourceNotFoundException {
		Long id = 1L;
		doThrow(new ResourceNotFoundException("User not found with id " + id)).when(userService).deleteUser(id);
		ResponseEntity<Void> result = userController.deleteUser(id);
		assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
	}
}

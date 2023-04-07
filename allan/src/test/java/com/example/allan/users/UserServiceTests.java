package com.example.allan.users;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.allan.exceptions.ResourceNotFoundException;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTests {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	@Test
	void testGetAllUsers() {
		User user1 = new User(1L, "Allan", "allanpaulourbiztondo@gmail.com", true);
		User user2 = new User(2L, "Paulo", "allanpaulourbiztondo+1@gmail.com", false);
		List<User> userList = Arrays.asList(user1, user2);
		when(userRepository.findAll()).thenReturn(userList);

		List<User> result = userService.getAllUsers();
		assertEquals(userList.size(), result.size());
		assertEquals(userList.get(0), result.get(0));
		assertEquals(userList.get(1), result.get(1));
	}

	@Test
	void testGetUserById() throws ResourceNotFoundException {
		Long id = 1L;
		User user = new User(id, "Allan", "allanpaulourbiztondo@gmail.com", true);
		when(userRepository.findById(id)).thenReturn(Optional.of(user));

		User result = userService.getUserById(id);
		assertEquals(user.getId(), result.getId());
		assertEquals(user.getName(), result.getName());
		assertEquals(user.getEmail(), result.getEmail());
		assertEquals(user.isActive(), result.isActive());
	}

	@Test
	void testCreateUser() {
		User user = new User(1L, "Allan", "allanpaulourbiztondo@gmail.com", true);
		when(userRepository.save(user)).thenReturn(user);

		User result = userService.createUser(user);
		assertEquals(user, result);
	}

	@Test
	void testUpdateUser() throws ResourceNotFoundException {
		Long id = 1L;
		User user = new User(id, "Allan", "allanpaulourbiztondo@gmail.com", true);
		User updatedUser = new User(id, "Allan Paulo", "allanpaulourbiztondo+1@gmail.com", true);
		when(userRepository.findById(id)).thenReturn(Optional.of(user));
		when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

		User result = userService.updateUser(id, updatedUser);
		assertEquals(user.getId(), result.getId());
		assertEquals(updatedUser.getName(), result.getName());
		assertEquals(updatedUser.getEmail(), result.getEmail());
		assertEquals(updatedUser.isActive(), result.isActive());
	}

	@Test
	void testDeleteUser() throws ResourceNotFoundException {
		Long id = 1L;
		User user = new User(id, "Allan", "allanpaulourbiztondo@gmail.com", true);
		when(userRepository.findById(id)).thenReturn(Optional.of(user));

		userService.deleteUser(1L);

		verify(userRepository, times(1)).delete(user);
	}

}

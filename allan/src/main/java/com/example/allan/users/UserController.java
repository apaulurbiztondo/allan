package com.example.allan.users;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.allan.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping("/users")
public class UserController {

	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	@Cacheable
	public List<User> getAllUsers() {
		logger.info("Getting all users");
		return userService.getAllUsers();
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		logger.info("Getting user with id: {}", id);
		try {
			User user = userService.getUserById(id);
			return ResponseEntity.ok().body(user);
		} catch (ResourceNotFoundException e) {
			logger.error(e.getMessage());
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) {
		logger.info("Creating new user");
		User createdUser = userService.createUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
		logger.debug("Updating user with id: {}", id);
		try {
			User updatedUser = userService.updateUser(id, user);
			return ResponseEntity.status(HttpStatus.CREATED).body(updatedUser);
		} catch (ResourceNotFoundException e) {
			logger.error(e.getMessage());
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		logger.debug("Deleting user with id: {}", id);
		try {
			userService.deleteUser(id);
			return ResponseEntity.noContent().build();
		} catch (ResourceNotFoundException e) {
			logger.error(e.getMessage());
			return ResponseEntity.notFound().build();
		}
	}

}

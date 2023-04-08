package com.example.demo.users;

import java.util.List;

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

import com.example.demo.exceptions.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	@Cacheable
	public ResponseEntity<List<User>> getAllUsers() {
		log.info("Getting all users");
		List<User> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		log.info("Getting user with id: {}", id);
		try {
			User user = userService.getUserById(id);
			return ResponseEntity.ok().body(user);
		} catch (ResourceNotFoundException e) {
			log.error(e.getMessage());
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) {
		log.info("Creating new user");
		User createdUser = userService.createUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
		log.info("Updating user with id: {}", id);
		try {
			User updatedUser = userService.updateUser(id, user);
			return ResponseEntity.status(HttpStatus.CREATED).body(updatedUser);
		} catch (ResourceNotFoundException e) {
			log.error(e.getMessage());
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		log.info("Deleting user with id: {}", id);
		try {
			userService.deleteUser(id);
			return ResponseEntity.noContent().build();
		} catch (ResourceNotFoundException e) {
			log.error(e.getMessage());
			return ResponseEntity.notFound().build();
		}
	}

}

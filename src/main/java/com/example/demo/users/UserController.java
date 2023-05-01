package com.example.demo.users;

import java.util.List;

import com.example.demo.common.ErrorResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	public ResponseEntity<User> getUserById(@PathVariable Long id) throws ResourceNotFoundException {
		log.info("Getting user with id: {}", id);
		User user = userService.getUserById(id);
		return ResponseEntity.ok().body(user);
	}

	@GetMapping("/active")
	public ResponseEntity<List<User>> findActiveUsers() {
		log.info("Getting active users...");
		return ResponseEntity.ok().body(userService.findActiveUsers());
	}

	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) {
		log.info("Creating new user");
		User createdUser = userService.createUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) throws ResourceNotFoundException {
		log.info("Updating user with id: {}", id);
		User updatedUser = userService.updateUser(id, user);
		return ResponseEntity.status(HttpStatus.CREATED).body(updatedUser);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws ResourceNotFoundException {
		log.info("Deleting user with id: {}", id);
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(ResourceNotFoundException e){
		log.error(e.getMessage());
		ErrorResponse error = ErrorResponse.builder()
				.status(HttpStatus.NOT_FOUND.value())
				.message(e.getMessage())
				.timestamp(System.currentTimeMillis())
				.build();
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

}

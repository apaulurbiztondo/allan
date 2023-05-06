package com.example.demo.users;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.error.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;

import static com.example.demo.Constants.USERS_API_URL;

@RestController
@RequestMapping(USERS_API_URL)
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
optim		return ResponseEntity.ok(userService.getAllUsers());
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) throws ResourceNotFoundException {
		log.info("Getting user with id: {}", id);
		return ResponseEntity.ok().body(userService.getUserById(id));
	}

	@GetMapping("/active")
	public ResponseEntity<List<User>> findActiveUsers() {
		log.info("Getting active users...");
		return ResponseEntity.ok().body(userService.findActiveUsers());
	}

	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) {
		log.info("Creating new user");
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) throws ResourceNotFoundException {
		log.info("Updating user with id: {}", id);
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.updateUser(id, user));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws ResourceNotFoundException {
		log.info("Deleting user with id: {}", id);
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}

}

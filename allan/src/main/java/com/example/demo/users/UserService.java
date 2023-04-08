package com.example.demo.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.exceptions.ResourceNotFoundException;

@Service
public class UserService {

	private UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User getUserById(@PathVariable Long id) throws ResourceNotFoundException {
		return userRepository.findById(id).orElseThrow(this::userNotFound);
	}

	public User createUser(@RequestBody User user) {
		return userRepository.save(user);
	}

	public User updateUser(@PathVariable Long id, @RequestBody User user) throws ResourceNotFoundException {
		User existingUser = userRepository.findById(id).orElseThrow(this::userNotFound);

		existingUser = User.builder()
				.id(existingUser.getId())
				.name(user.getName())
				.email(user.getEmail())
				.active(user.isActive())
				.build();

		return userRepository.save(existingUser);
	}

	public void deleteUser(@PathVariable Long id) throws ResourceNotFoundException {
		User user = userRepository.findById(id).orElseThrow(this::userNotFound);
		userRepository.delete(user);
	}

	private ResourceNotFoundException userNotFound() {
		return new ResourceNotFoundException("User not found!");
	}

}

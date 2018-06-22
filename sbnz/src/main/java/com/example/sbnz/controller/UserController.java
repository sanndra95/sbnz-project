package com.example.sbnz.controller;

import com.example.sbnz.model.*;
import com.example.sbnz.security.JwtTokenUtil;
import com.example.sbnz.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@RestController
@RequestMapping(value = "/api/user")
@CrossOrigin
public class UserController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	UserService userService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Value("${jwt.header}")
	private String tokenHeader;


	@PostMapping(value = "/registerDoctor", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> registerDoctor(@RequestBody User user) {
		logger.info("> user: {} {} {} {}", user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
		PasswordEncoder enc = new BCryptPasswordEncoder();
		String encoded = enc.encode(user.getPassword());
		user.setPassword(encoded);
		user.setRole(UserRole.ROLE_DOCTOR);

		User u = userService.create(user);

		logger.info("> create user: {}", u.getEmail());
		return new ResponseEntity<>(u, HttpStatus.CREATED);
	}

	@GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAll() {
		logger.info("> getting all users");
		Collection<User> users = userService.getAll();
		logger.info("size: {}", users.size());
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getUser(@PathVariable Long id) {
		User user = userService.findById(id);
		logger.info("getting user: {} {}", user.getFirstName(), user.getLastName());
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
	public ResponseEntity<?> update(@RequestBody User user, HttpServletRequest request) {
		String token = request.getHeader(tokenHeader);
		String username = jwtTokenUtil.getUsernameFromToken(token);
		User u = userService.findByUsername(username);
		logger.info("> updating user: {} {}", u.getFirstName(), u.getLastName());
		u.setFirstName(user.getFirstName());
		u.setLastName(user.getLastName());
		u.setEmail(user.getEmail());
		PasswordEncoder enc = new BCryptPasswordEncoder();
		String encoded = enc.encode(user.getPassword());
		u.setPassword(encoded);
		User updated = userService.create(u);
		return new ResponseEntity<>(updated, HttpStatus.OK);
	}
	

}

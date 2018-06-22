package com.example.sbnz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sbnz.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String username);

}

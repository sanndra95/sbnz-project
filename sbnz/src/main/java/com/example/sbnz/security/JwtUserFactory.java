package com.example.sbnz.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.sbnz.model.User;
import com.example.sbnz.model.UserRole;


public final class JwtUserFactory {

	private JwtUserFactory() {
	}

	public static JwtUser create(User user) {
		return new JwtUser(user.getId(), user.getEmail(), user.getPassword(),
				mapToGrantedAuthorities(user.getRole()), true);
	}

	private static List<GrantedAuthority> mapToGrantedAuthorities(
			UserRole userType) {
		List<GrantedAuthority> retVal = new ArrayList<>();
		retVal.add(new SimpleGrantedAuthority(userType.name()));
		return retVal;
	}
}

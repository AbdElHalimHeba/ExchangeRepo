package com.xische.mapping;

import java.time.LocalDate;

import org.springframework.security.core.userdetails.UserDetails;

import com.xische.dto.UserDto;
import com.xische.entity.User;

public class UserMapper {

	public static UserDetails mapUserToUserDetails(User user) {
		UserDto dto = new UserDto();
		
		dto.setUsername(user.getUsername());
		dto.setPassword(user.getPassword());
		dto.setRole(user.getRole().name());
		dto.setTenure(user.getTenure());
		
		return dto;
	}
	
	public static UserDto mapUserToUserDetails(String username, String password, String role, LocalDate tenure) {
		UserDto dto = new UserDto();
		
		dto.setUsername(username);
		dto.setPassword(password);
		dto.setRole(role);
		dto.setTenure(tenure);
		
		return dto;
	}
	
}

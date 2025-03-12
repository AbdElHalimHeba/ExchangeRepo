package com.xische.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.xische.dto.UserDto;
import com.xische.entity.User;
import com.xische.mapping.UserMapper;
import com.xische.repository.UserRepository;
import com.xische.service.UserService;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

	@Autowired
	private UserRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User is not found"));

		return UserMapper.mapUserToUserDetails(user);
	}
	
	@Override
	public UserDto getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if(authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) return null;
        return ((UserDto) authentication.getPrincipal());
    }
	
}

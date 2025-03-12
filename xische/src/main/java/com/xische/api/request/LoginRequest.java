package com.xische.api.request;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Validated
public class LoginRequest {

	@NotNull(message = "Username can not be null")
	private String username;
	
	@NotNull(message = "Password can not be null")
	private String password;
}

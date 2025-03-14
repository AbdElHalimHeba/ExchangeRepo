package com.xische.entity;

import java.time.LocalDate;

import com.xische.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
    private String username;
    
    private String password;

    @Enumerated(EnumType.STRING)
	@Column(nullable = false)
    private Role role;
    
    private LocalDate tenure;
}

package com.xische.entity;

import java.math.BigDecimal;

import com.xische.enums.Category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
@Entity
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@Positive
	@Column(nullable = false)
	private BigDecimal price;
	
	@Column(nullable = false)
	private String currency;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Category category;
}

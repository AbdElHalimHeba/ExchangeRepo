package com.xische.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xische.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

}

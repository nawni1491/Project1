package com.stock.userproflie.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stock.userproflie.model.UserProfiledata;



@Repository
public interface UserProfileRepository extends JpaRepository<UserProfiledata, Long>{
	Page<UserProfiledata> findAll(Pageable pageable);
}
package com.prajjawal.Trading_Platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prajjawal.Trading_Platform.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}

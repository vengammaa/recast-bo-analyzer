package com.recast.recast.bo.analyzer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recast.recast.bo.analyzer.entity.User;


public interface UserRepository extends JpaRepository<User, String> {

	List<User> findByUserNameNotIn(List<String> usernames);

}

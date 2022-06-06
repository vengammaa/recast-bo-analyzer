package com.recast.recast.bo.analyzer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recast.recast.bo.analyzer.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

}

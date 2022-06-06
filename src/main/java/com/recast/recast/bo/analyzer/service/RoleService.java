package com.recast.recast.bo.analyzer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recast.recast.bo.analyzer.model.RoleModel;
import com.recast.recast.bo.analyzer.repository.RoleRepository;
import com.recast.recast.bo.analyzer.util.ModelBuilder;

@Service
public class RoleService {
	private Logger logger = LoggerFactory.getLogger(RoleService.class);
	
	@Autowired
	private RoleRepository roleRepository;
	
	public List<RoleModel> getAllRoles() {
		logger.debug("Inside RoleService -> getAllRoles");
		return roleRepository.findAll().stream().map(ModelBuilder::roleModelBuilder).collect(Collectors.toList());
	}
	
}

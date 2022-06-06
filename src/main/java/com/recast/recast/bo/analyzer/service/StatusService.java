package com.recast.recast.bo.analyzer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recast.recast.bo.analyzer.model.StatusModel;
import com.recast.recast.bo.analyzer.repository.StatusRepository;
import com.recast.recast.bo.analyzer.util.ModelBuilder;

@Service
public class StatusService {
	
	@Autowired
	private StatusRepository repository;
	
	@Transactional
	public StatusModel getStatusByCode(String statusCode) {
		return ModelBuilder.statusModelBuilder(repository.getOne(statusCode));
	}
	
}

package com.recast.recast.bo.analyzer.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.recast.recast.bo.analyzer.entity.Product;


public interface ProductRepository extends CrudRepository<Product,Integer>,JpaSpecificationExecutor<Product>
{

	
}

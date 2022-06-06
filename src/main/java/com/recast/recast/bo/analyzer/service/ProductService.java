package com.recast.recast.bo.analyzer.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.recast.recast.bo.analyzer.entity.Product;
import com.recast.recast.bo.analyzer.repository.ProductRepository;


@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepo;
	
	@Transactional
	public List<Product> getFilteredData(Product pm) {
		System.out.println("Inside Product Model Service");
		
		System.out.println("product id:"+pm.getProductId());
		int productId = pm.getProductId();
		String productName = pm.getProductName();
		String segment = pm.getSegment();
		String country = pm.getCountry();
		String fpc = pm.getFpc();
		
		//return ModelBuilder.projectModelBuilder(projectRepository.getOne(projectId));
		return productRepo.findAll(new Specification<Product>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				
				 List<Predicate> predicates = new ArrayList<>();
				 
				if(productId!=0)
				{
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("productId"), productId)));
				}
				if(productName!=null)
				{
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("productName"), productName)));
				}
				if(segment!=null)
				{
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("segment"), segment)));
				}
				if(country!=null)
				{
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("country"), country)));
				}
				if(fpc!=null)
				{
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("fpc"), fpc)));
				}
			
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
			
		 });
	}
}

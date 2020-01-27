package org.diallo.angular.dao;

import java.util.List;

import org.diallo.angular.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin("*")
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	@RestResource(path = "/newCollectionProducts")
	public List<Product> findByNewCollectionIsTrue();
	
	@RestResource(path = "/productsByKeyword")
	public List<Product> findByNameContains(@Param("motCle") String mc);

	@RestResource(path = "/promoProducts")
	public List<Product> findByPromotionIsTrue();
	@RestResource(path = "/availableProducts")
	public List<Product> findByAvailableIsTrue();

}

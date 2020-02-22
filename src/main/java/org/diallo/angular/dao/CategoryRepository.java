package org.diallo.angular.dao;


import java.util.List;

import org.diallo.angular.entities.Category;
import org.diallo.angular.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
//croos pour autoriser les url venant du front
@CrossOrigin("*")
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	@RestResource(path = "/categoryByDescription")
	public List<Category> findByNameContains(@Param("motCle") String mc);

}


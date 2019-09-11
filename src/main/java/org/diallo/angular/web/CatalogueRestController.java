package org.diallo.angular.web;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.diallo.angular.dao.ProductRepository;
import org.diallo.angular.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CatalogueRestController {
	@Autowired
	private ProductRepository productRepository;

//	public CatalogueRestController(ProductRepository productRepository) {
//		super();
//		this.productRepository = productRepository;
//	}
//	
	
@GetMapping(path="/photoProduct/{id}", produces= MediaType.IMAGE_PNG_VALUE)
	public byte[] getPhoto( @PathVariable("id") Long id) throws Exception{
	Product p = productRepository.findById(id).get();
	return Files.readAllBytes(Paths.get(System.getProperty("user.home")+"/ecom/products/"+p.getPhotoName()));
	 
	
}
}

package org.diallo.angular.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.diallo.angular.dao.ProductRepository;
import org.diallo.angular.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin("*")
public class CatalogueRestController {
	@Autowired
	private ProductRepository productRepository;


//	public CatalogueRestController(ProductRepository productRepository) {
//		super();
//		this.productRepository = productRepository;
//	}
//

	@GetMapping(path = "/photoProduct/{id}", produces = MediaType.IMAGE_PNG_VALUE)
	public byte[] getPhoto(@PathVariable("id") Long id) throws Exception {
		Product p = productRepository.findById(id).get();
		return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/ecom/products/" + p.getPhotoName()));

	}
	
	
	//donner le meme nom del'objet MultiPartFile "file" que dans le front angular 
	//dans la methode UploadPhotoProduct
	
	//postmaping parceque ds le front, une methode POST est utilisée
	@PostMapping( path = "/uploadPhoto/{id}")
	public void uploadPhoto(MultipartFile file, @PathVariable Long id) throws IOException {
		Product p = productRepository.findById(id).get();
		//enregistre la photo avec son nom
		p.setPhotoName(file.getOriginalFilename());
		 //p.setPhotoName(id+".png");
		Files.write(Paths.get(System.getProperty("user.home")+"/ecom/products/"+p.getPhotoName()),file.getBytes());
		productRepository.save(p);
	
	}
}

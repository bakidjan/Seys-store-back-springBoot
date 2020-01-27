package org.diallo.angular;

import java.util.Random;

import org.diallo.angular.dao.CategoryRepository;
import org.diallo.angular.dao.ProductRepository;
import org.diallo.angular.dao.UserRepository;
import org.diallo.angular.entities.Category;
import org.diallo.angular.entities.Product;
import org.diallo.angular.entities.Role;
import org.diallo.angular.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import net.bytebuddy.utility.RandomString;

@SpringBootApplication
public class AngularApplication implements CommandLineRunner{
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	//anable id in jason data
	@Autowired
	private RepositoryRestConfiguration repositoryRestConfiguration;
	@Autowired
	private UserRepository userRepository;

	
	public static void main(String[] args) {
		SpringApplication.run(AngularApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		repositoryRestConfiguration.exposeIdsFor(Product.class, Category.class);
		categoryRepository.save(new Category(null, "Computers", null, null, null));
		categoryRepository.save(new Category(null, "Printers", null, null, null));
		categoryRepository.save(new Category(null, "Phones", null, null, null));
		//categoryRepository.save(new Category(null, "Tools", null, "daba", null));
		
		Random rd = new Random();
		categoryRepository.findAll().forEach(c->{
			//parcours des produits
			for (int i = 0; i < 10; i++) {
				Product p = new Product();
				p.setName(RandomString.make(7));
				p.setAvailable(rd.nextBoolean());
				p.setPromotion(rd.nextBoolean());
				p.setNewCollection(rd.nextBoolean());
				p.setCategory(c);
				p.setCurrentPrice(100+rd.nextInt(700));
				p.setPrice(100+rd.nextInt(700));
				p.setPhotoName("unknown.png");
			    productRepository.save(p);
				
			}
		});
		userRepository.save(new User(null, "admin", "admin@gmail.com", "admin", Role.ADMIN));
		userRepository.save(new User(null, "user", "user@gmail.com", "user", Role.USER));

	}

}

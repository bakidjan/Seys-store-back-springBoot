package org.diallo.angular.web;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import org.diallo.angular.dao.CategoryRepository;
import org.diallo.angular.dao.ProductRepository;
import org.diallo.angular.dao.UserRepository;
import org.diallo.angular.entities.Product;
import org.diallo.angular.entities.Role;
import org.diallo.angular.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
public class CatalogueRestController extends RuntimeException {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;


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
    @PostMapping(path = "/uploadPhoto/{id}")
    public void uploadPhoto(MultipartFile file, @PathVariable Long id) throws IOException {
        Product p = productRepository.findById(id).get();
        //enregistre la photo avec son nom
        p.setPhotoName(file.getOriginalFilename());
        //p.setPhotoName(id+".png");
        Files.write(Paths.get(System.getProperty("user.home") + "/ecom/products/" + p.getPhotoName()), file.getBytes());
        productRepository.save(p);

    }

    @PostMapping("category/{catId}/product")
    public Optional<@Valid Product> createProduct(@PathVariable(value = "catId") Long catId,
                                                  @Valid @RequestBody Product product) {
        return categoryRepository.findById(catId).map(post -> {
            product.setCategory(post);
            return productRepository.save(product);
        });
    }

    @DeleteMapping("/category/{cattId}")
    public Optional<ResponseEntity<Object>> deleteCategory(@PathVariable Long cattId) {
        return categoryRepository.findById(cattId).map(post -> {
            categoryRepository.delete(post);
            return ResponseEntity.ok().build();
        });
    }

    @PostMapping("/addUser")
    public User userUser(@RequestBody User updateUser) throws DataIntegrityViolationException{

        userRepository.findAll().forEach(u->{
            System.out.println(u);
            if (u.getEmail()!=updateUser.getEmail()){
                updateUser.setRole(Role.USER);
                userRepository.save(updateUser);
            } else {
                throw new DataIntegrityViolationException("pas bon mec");
            }
        });
        return updateUser;
        //throw new DataIntegrityViolationException('sdfsd')





       /*User user = userRepository.findOneByEmail(updateUser.getEmail());
       if(user==null){
           user.setRole(Role.USER);
           userRepository.save(user);
       } else{
           throw new DataIntegrityViolationException("L'email " + updateUser.getEmail().toUpperCase() + " est déjà utilisé !");
       }

        System.out.println(user);
        return user;*/

        /*System.out.print(updateUser.getId());
        User user = userRepository.findOneById(updateUser.getId());
        if (user == null) {
            user = new User();
        }

        if (updateUser.getEmail() != null && !updateUser.getEmail().equals("")) {
            User userCheck = userRepository.findOneByEmail(updateUser.getEmail());
            if (userCheck == null) {
                user.setEmail(updateUser.getEmail());
                user.setRole(Role.USER);
            } else {
                throw new DataIntegrityViolationException("L'email " + updateUser.getEmail().toUpperCase() + " est déjà utilisé !");
            }
        }
        return this.userRepository.save(user);*/
    }

    @PostMapping("/addAdmin")
    public User userAdmin(@RequestBody User admin) {
        admin.setRole(Role.ADMIN);
        return userRepository.save(admin);
    }
}

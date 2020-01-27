package org.diallo.angular.entities;

import java.util.Collection;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data @NoArgsConstructor @AllArgsConstructor @ToString
@Entity
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	private String name;
    private String photo;
	private String description;
	@OneToMany(mappedBy = "category")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Collection<Product> products;
	
}

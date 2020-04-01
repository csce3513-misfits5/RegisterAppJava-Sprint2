package edu.uark.registerapp.models.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import edu.uark.registerapp.models.entities.ProductEntity;

public interface ProductRepository extends CrudRepository<ProductEntity, UUID> {
	Optional<ProductEntity> findById(UUID id);
	Optional<ProductEntity> findByLookupCode(String lookupCode);
}
//CRUD interface defines methods like save(), findAll(), findById(), deleteById()
//At runtime, Spring Data JPA generates implementation code
//Note: specify type of the model class and type of primary key field

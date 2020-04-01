package edu.uark.registerapp.models.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import edu.uark.registerapp.models.entities.EmployeeEntity;

public interface EmployeeRepository extends CrudRepository<EmployeeEntity, UUID> {
	boolean existsByIsActive(boolean isActive);
	boolean existsByEmployeeId(int employeeId);
	Optional<EmployeeEntity> findById(UUID id);
	Optional<EmployeeEntity> findByEmployeeId(int employeeId);
}
//CRUD interface defines methods like save(), findAll(), findById(), deleteById()
//At runtime, Spring Data JPA generates implementation code
//Note: specify type of the model class and type of primary key field
package br.edu.atitus.api_example.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.atitus.api_example.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID>{

}

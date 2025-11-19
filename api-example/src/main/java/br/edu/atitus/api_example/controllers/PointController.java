package br.edu.atitus.api_example.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.api_example.entities.PointEntity;
import br.edu.atitus.api_example.repositories.PointRepository;

@RestController
@RequestMapping("/ws/point")
public class PointController {
	
	private final PointRepository repository;

	public PointController(PointRepository repository) {
		super();
		this.repository = repository;
	}

	@PostMapping
	public ResponseEntity<PointEntity> save(@RequestBody PointEntity point) {
		repository.save(point);
		return ResponseEntity.status(HttpStatus.CREATED).body(point);
	}
	
	@GetMapping
	public ResponseEntity<List<PointEntity>> findAll() {
		return ResponseEntity.ok(repository.findAll());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable UUID id) {
		repository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}

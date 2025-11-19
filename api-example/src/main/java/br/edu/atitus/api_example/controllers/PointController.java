package br.edu.atitus.api_example.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.api_example.entities.PointEntity;
import br.edu.atitus.api_example.repositories.PointRepository;

@RestController
@RequestMapping("/ws/point") // Define o endereço que o Front procura
public class PointController {
	
	private final PointRepository repository;

	public PointController(PointRepository repository) {
		super();
		this.repository = repository;
	}

	@PostMapping
	public ResponseEntity<PointEntity> save(@RequestBody PointEntity point) {
		// Salva o ponto no banco de dados
		repository.save(point);
		// Retorna o ponto salvo com status 201 (Created)
		return ResponseEntity.status(HttpStatus.CREATED).body(point);
	}
	
	@GetMapping
	public ResponseEntity<List<PointEntity>> findAll() {
		// Busca todos os pontos para mostrar no mapa
		return ResponseEntity.ok(repository.findAll());
	}
}

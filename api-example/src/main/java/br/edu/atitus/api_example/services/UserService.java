package br.edu.atitus.api_example.services;

import org.springframework.stereotype.Service;

import br.edu.atitus.api_example.entities.UserEntity;
import br.edu.atitus.api_example.repositories.UserRepository;

@Service
public class UserService {
	
	private final UserRepository repository;
	
	public UserService(UserRepository repository) {
		super();
		this.repository = repository;
	}

	public UserEntity save(UserEntity user) throws Exception {
		if (user == null)
			throw new Exception("Objeto Nulo");
		
		if (user.getName() == null || user.getName().isEmpty())
			throw new Exception("Nome inválido");
		user.setName(user.getName().trim());
		
		if (user.getEmail() == null || user.getEmail().isEmpty())
			throw new Exception("E-mail inválido");
		user.setEmail(user.getEmail().trim());
		
		if (user.getPassword() == null 
				|| user.getPassword().isEmpty()
				|| user.getPassword().length() < 8)
			throw new Exception("Password inválido");
		
		//TODO criar hash da senha
		
		//TODO validar permissão cadastro Admins
		
		repository.save(user);
		
		return user;
	}

}

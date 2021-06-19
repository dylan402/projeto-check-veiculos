package br.checkveiculos.negocio;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.checkveiculos.entidades.Cliente;
import br.checkveiculos.persistencia.ClienteRepository;

@Service
public class ClienteService {
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private final ClienteRepository clienteRepository;

	public ClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	public List<Cliente> getClientes() {
		if (logger.isInfoEnabled()) {
			logger.info("Buscando clientes...");
		}

		Iterable<Cliente> clientes = this.clienteRepository.findAll();

		if (clientes == null) {
			return new ArrayList<Cliente>();
		}

		return IteratorUtils.toList(clientes.iterator());
	}

	public Cliente getClienteById(String id) {
		if (logger.isInfoEnabled()) {
			logger.info("Buscando cliente por id...");
		}

		Optional<Cliente> cliente = this.clienteRepository.findById(id);

		if (!cliente.isPresent()) {
			throw new RuntimeException("O cliente não foi encontrado.");
		}

		return cliente.get();
	}

	public Cliente criarCliente(Cliente cliente) {
		if (logger.isInfoEnabled()) {
			logger.info("Salvando cliente...");
		}
		
		if (this.isClienteExistsByEmail(cliente.getEmail())) {
			throw new RuntimeException("Este e-mail já está em uso.");
		}
		
		cliente.setSenha(this.criarHashSenha(cliente.getSenha()));

		return this.clienteRepository.save(cliente);
	}

	public Cliente atualizarCliente(Cliente cliente) {
		if (logger.isInfoEnabled()) {
			logger.info("Salvando cliente...");
		}
		

		if (!this.isClienteExists(cliente)) {
			throw new RuntimeException("O cliente não foi encontrado.");
		} else if (this.isEmailFromAnotherCliente(cliente)) {
			throw new RuntimeException("Este e-mail já está em uso.");
		}
		
		cliente.setSenha(this.criarHashSenha(cliente.getSenha()));

		return this.clienteRepository.save(cliente);
	}

	public void deletarCliente(String id) {
		if (logger.isInfoEnabled()) {
			logger.info("Deletando cliente...");
		}

		if (!this.isClienteExists(id)) {
			throw new RuntimeException("O cliente não foi encontrado.");
		}

		this.clienteRepository.deleteById(id);
	}
	
	public Cliente logarCliente(Cliente cliente) {
		if (logger.isInfoEnabled()) {
			logger.info("Logando cliente...");
		}

		Optional<Cliente> clienteEncontrado = this.clienteRepository.findByEmail(cliente.getEmail());
		
		if (!clienteEncontrado.isPresent()) {
			throw new RuntimeException("Nenhum cliente com este e-mail foi encontrado.");
		}
		
		BCryptPasswordEncoder crypt = new BCryptPasswordEncoder(); 
		
		if (!crypt.matches(cliente.getSenha(), clienteEncontrado.get().getSenha())) {
			throw new RuntimeException("Senha não confere.");
		}
		
		return clienteEncontrado.get();
	}

	public boolean isClienteExists(Cliente cliente) {
		Optional<Cliente> clienteEncontrado = this.clienteRepository.findById(cliente.getId());

		return clienteEncontrado.isPresent();
	}

	public boolean isClienteExists(String id) {
		Optional<Cliente> clienteEncontrado = this.clienteRepository.findById(id);

		return clienteEncontrado.isPresent();
	}
	
	public boolean isClienteExistsByEmail(String email) {
		Optional<Cliente> clienteEncontrado = this.clienteRepository.findByEmail(email);

		return clienteEncontrado.isPresent();
	}
	
	public boolean isEmailFromAnotherCliente (Cliente cliente) {
		Optional<Cliente> clienteEncontrado = this.clienteRepository.findByEmail(cliente.getEmail());
		
		if (clienteEncontrado.isPresent() && !cliente.getId().equals(clienteEncontrado.get().getId())) {
			return true;	
		}
		return false;
	}
	
	public String criarHashSenha(String senha) {
		return new BCryptPasswordEncoder().encode(senha);
	}
}

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
		
		cliente.setSenha(new BCryptPasswordEncoder().encode(cliente.getSenha()));

		return this.clienteRepository.save(cliente);
	}

	public Cliente atualizarCliente(Cliente cliente) {
		if (logger.isInfoEnabled()) {
			logger.info("Salvando cliente...");
		}

		if (!this.isClienteExists(cliente)) {
			throw new RuntimeException("O cliente não foi encontrado.");
		}

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

	public boolean isClienteExists(Cliente cliente) {
		Optional<Cliente> clienteEncontrado = this.clienteRepository.findById(cliente.getId());

		return clienteEncontrado.isPresent();
	}

	public boolean isClienteExists(String id) {
		Optional<Cliente> clienteEncontrado = this.clienteRepository.findById(id);

		return clienteEncontrado.isPresent();
	}
}

package br.checkveiculos.rest;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.checkveiculos.negocio.ClienteService;
import br.checkveiculos.entidades.Cliente;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "cliente")
public class ClienteController {
	private final ClienteService clienteService;

	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@GetMapping
	public List<Cliente> getClientes() {
		return clienteService.getClientes();
	}

	@GetMapping(value = "{id}")
	public Cliente getClienteById(@PathVariable String id) throws Exception {
		if (!ObjectUtils.isEmpty(id)) {
			return clienteService.getClienteById(id);
		}

		throw new Exception("O cliente não foi encontrado.");
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Cliente criarCliente(@RequestBody @NotNull Cliente cliente) throws Exception {
		return clienteService.criarCliente(cliente);
	}

	@PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Cliente atualizarCliente(@PathVariable String id, @RequestBody @NotNull Cliente cliente) throws Exception {
		if (!id.equals(cliente.getId())) {
			throw new Exception("O id informado não está correto.");
		}

		if (ObjectUtils.isEmpty(id)) {
			throw new Exception("É preciso informar um id.");
		}

		return this.clienteService.atualizarCliente(cliente);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value = "{id}")
	public boolean deletarCliente(@PathVariable String id) throws Exception {
		if (!clienteService.isClienteExists(id)) {
			throw new Exception("O cliente não foi encontrado.");
		}

		clienteService.deletarCliente(id);
		return true;
	}
}

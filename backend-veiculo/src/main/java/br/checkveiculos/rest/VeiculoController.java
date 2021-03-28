package br.checkveiculos.rest;

import org.springframework.web.bind.annotation.RestController;

import br.checkveiculos.entidades.Veiculo;
import br.checkveiculos.negocio.VeiculoService;

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

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "veiculo")
public class VeiculoController {

	private final VeiculoService veiculoService;

	public VeiculoController(VeiculoService veiculoService) {
		this.veiculoService = veiculoService;
	}

	@GetMapping
	public List<Veiculo> getVeiculos() {
		return veiculoService.getVeiculos();
	}

	@GetMapping(value = "{id}")
	public Veiculo getVeiculoById(@PathVariable String id) throws Exception {
		if (ObjectUtils.isEmpty(id)) {
			throw new Exception("É preciso informar um id.");
		}

		return veiculoService.getVeiculoById(id);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Veiculo criarVeiculo(@RequestBody @NotNull Veiculo veiculo) throws Exception {
		return veiculoService.criarVeiculo(veiculo);
	}

	@PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Veiculo atualizarVeiculo(@PathVariable String id, @RequestBody @NotNull Veiculo veiculo) throws Exception {
		if (!id.equals(veiculo.getId())) {
			throw new Exception("Os ids não coincidem.");
		}

		if (!veiculoService.isVeiculoExists(veiculo)) {
			throw new Exception("Não existe um veículo com id.");
		}

		return veiculoService.atualizarVeiculo(veiculo);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value = "{id}")
	public boolean deletarVeiculo(@PathVariable String id) throws Exception {
		if (veiculoService.isVeiculoExists(id)) {
			throw new Exception("O veículo não existe.");
		}

		veiculoService.deletarVeiculo(id);
		return true;
	}
}

package br.checkveiculos.negocio;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.checkveiculos.entidades.Veiculo;
import br.checkveiculos.persistencia.VeiculoRepository;

@Service
public class VeiculoService {
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private final VeiculoRepository veiculoRepository;

	public VeiculoService(VeiculoRepository veiculoRepository) {
		this.veiculoRepository = veiculoRepository;
	}

	public List<Veiculo> getVeiculos() {
		if (logger.isInfoEnabled()) {
			logger.info("Buscando veiculos...");
		}

		Iterable<Veiculo> veiculos = this.veiculoRepository.findAll();

		if (veiculos == null) {
			return new ArrayList<Veiculo>();
		}

		return IteratorUtils.toList(veiculos.iterator());
	}

	public Veiculo getVeiculoById(String id) {
		if (logger.isInfoEnabled()) {
			logger.info("Buscando veiculo por id...");
		}

		Optional<Veiculo> veiculo = this.veiculoRepository.findById(id);

		if (!veiculo.isPresent()) {
			throw new RuntimeException("O veículo não foi encontrado.");
		}

		return veiculo.get();
	}

	public Veiculo getVeiculoByPlaca(String placa) {
		if (logger.isInfoEnabled()) {
			logger.info("Buscando veiculo por placa...");
		}

		Optional<Veiculo> veiculo = this.veiculoRepository.findByPlaca(placa);

		if (!veiculo.isPresent()) {
			throw new RuntimeException("O veículo não foi encontrado.");
		}

		return veiculo.get();
	}

	public Veiculo criarVeiculo(Veiculo veiculo) {
		if (logger.isInfoEnabled()) {
			logger.info("Criando veiculo...");
		}

		return this.veiculoRepository.save(veiculo);
	}

	public Veiculo atualizarVeiculo(Veiculo veiculo) {
		if (logger.isInfoEnabled()) {
			logger.info("Criando veiculo...");
		}

		if (!this.isVeiculoExists(veiculo)) {
			throw new RuntimeException("O veículo não foi encontrado.");
		}

		return this.veiculoRepository.save(veiculo);
	}

	public void deletarVeiculo(String id) {
		if (logger.isInfoEnabled()) {
			logger.info("Criando veiculo...");
		}

		if (!this.isVeiculoExists(id)) {
			throw new RuntimeException("O veículo não foi encontrado.");
		}

		this.veiculoRepository.deleteById(id);
	}

	public boolean isVeiculoExists(Veiculo veiculo) {
		Optional<Veiculo> veiculoEncontrado = this.veiculoRepository.findById(veiculo.getId());

		return veiculoEncontrado.isPresent();
	}

	public boolean isVeiculoExists(String id) {
		Optional<Veiculo> veiculoEncontrado = this.veiculoRepository.findById(id);

		return veiculoEncontrado.isPresent();
	}
}

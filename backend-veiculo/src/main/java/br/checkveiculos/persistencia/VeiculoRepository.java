package br.checkveiculos.persistencia;

import java.util.Optional;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import br.checkveiculos.entidades.Veiculo;

@EnableScan()
public interface VeiculoRepository extends CrudRepository<Veiculo, String> {
	Optional<Veiculo> findByPlaca(String placa);
	Iterable<Veiculo> findByIdCliente(String idCliente);
}

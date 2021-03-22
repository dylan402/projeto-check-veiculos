package br.checkveiculos.persistencia;

import java.util.Optional;
import java.util.UUID;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import br.checkveiculos.entidades.Veiculo;

@EnableScan()
public interface VeiculoRepository extends CrudRepository<Veiculo, UUID> {
	Optional<Veiculo> findByPlaca(String placa);
}

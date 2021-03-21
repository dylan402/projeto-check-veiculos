package br.checkveiculos.persistencia;

import java.util.UUID;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import br.checkveiculos.entidades.Cliente;

@EnableScan()
public interface ClienteRepository extends CrudRepository<Cliente, UUID> {
}

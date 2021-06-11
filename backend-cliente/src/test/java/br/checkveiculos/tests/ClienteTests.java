package br.checkveiculos.tests;

import static org.junit.Assert.assertNotNull;

import java.text.ParseException;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

import br.checkveiculos.entidades.Cliente;
import br.checkveiculos.persistencia.ClienteRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { PropertyPlaceholderAutoConfiguration.class, ClienteTests.DynamoDBConfig.class })
public class ClienteTests {

	private static Logger LOGGER = LoggerFactory.getLogger(ClienteTests.class);

	@Configuration
	@EnableDynamoDBRepositories(basePackageClasses = { ClienteRepository.class })
	public static class DynamoDBConfig {
		@Value("${amazon.aws.accesskey}")
		private String amazonAWSAccessKey;

		@Value("${amazon.aws.secretkey}")
		private String amazonAWSSecretKey;

		public AWSCredentialsProvider amazonAWSCredentialsProvider() {
			return new AWSStaticCredentialsProvider(amazonAWSCredentials());
		}

		@Bean
		public AWSCredentials amazonAWSCredentials() {
			return new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
		}

		@Bean
		public AmazonDynamoDB amazonDynamoDB() {
			return AmazonDynamoDBClientBuilder.standard().withCredentials(amazonAWSCredentialsProvider())
					.withRegion(Regions.US_EAST_2).build();
		}
	}

	@Autowired
	private ClienteRepository repositorio;

	@Test
	public void teste1Criacao() throws ParseException {
		LOGGER.info("Criando objetos...");

		String senhaHash = new BCryptPasswordEncoder().encode("123456");

		Cliente c1 = new Cliente("Cliente 1", "c1@email.com", senhaHash);
		Cliente c2 = new Cliente("Cliente 2", "c2@email.com", senhaHash);
		Cliente c3 = new Cliente("Cliente 3", "c3@email.com", senhaHash);
		Cliente c4 = new Cliente("Cliente 4", "c4@email.com", senhaHash);

		LOGGER.info("Salvando objetos no repositório...");

		repositorio.save(c1);
		repositorio.save(c2);
		repositorio.save(c3);
		repositorio.save(c4);

		LOGGER.info("Listando objetos do repositório...");

		Iterable<Cliente> clientes = repositorio.findAll();
		assertNotNull(clientes.iterator());

		for (Cliente cliente : clientes) {
			LOGGER.info(cliente.toString());
		}

		LOGGER.info("Finalizando teste de criação...");
	}
}

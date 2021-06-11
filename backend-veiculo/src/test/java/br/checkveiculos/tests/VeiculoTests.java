package br.checkveiculos.tests;

import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import org.junit.Test;
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
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

import br.checkveiculos.entidades.Veiculo;
import br.checkveiculos.persistencia.VeiculoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { PropertyPlaceholderAutoConfiguration.class, VeiculoTests.DynamoDBConfig.class })
public class VeiculoTests {

	private static Logger LOGGER = LoggerFactory.getLogger(VeiculoTests.class);

	@Configuration
	@EnableDynamoDBRepositories(basePackageClasses = { VeiculoRepository.class })
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
	private VeiculoRepository repositorio;

	@Test
	public void teste1Criacao() throws ParseException {
		LOGGER.info("Criando objetos...");

		Veiculo v1 = new Veiculo("68786843-2131-4915-ab9b-464b91e3f703", "Volkswagen", "Polo", "2021", "ABC-1234");
		Veiculo v2 = new Veiculo("f6413fa2-4625-40f3-9842-dd946e59cc99", "BMW", "320i", "2021", "DEF-5678");
		Veiculo v3 = new Veiculo("d20e1ffc-69d4-4147-8c35-8c3dc5d82f27", "Mercedes-benz", "C-180", "1995", "GHI-9012");

		LOGGER.info("Salvando objetos no banco...");

		repositorio.save(v1);
		repositorio.save(v2);
		repositorio.save(v3);

		LOGGER.info("Listando objetos do salvos...");

		Iterable<Veiculo> veiculos = repositorio.findAll();
		assertNotNull(veiculos.iterator());

		for (Veiculo veiculo : veiculos) {
			LOGGER.info(veiculo.toString());
		}

		LOGGER.info("Finalizando teste de criação...");
	}
}

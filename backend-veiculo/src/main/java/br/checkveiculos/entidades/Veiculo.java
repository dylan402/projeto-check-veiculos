package br.checkveiculos.entidades;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "veiculo")
public class Veiculo {
	private String id;
	private String idCliente;
	private String marca;
	private String modelo;
	private String ano;
	private String placa;

	public Veiculo() {
		super();
	}

	public Veiculo(String idCliente, String marca, String modelo, String ano, String placa) {
		super();
		setIdCliente(idCliente);
		setMarca(marca);
		setModelo(modelo);
		setAno(ano);
		setPlaca(placa);
	}

	@DynamoDBHashKey
	@DynamoDBAutoGeneratedKey
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@DynamoDBAttribute
	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	@DynamoDBAttribute
	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	@DynamoDBAttribute
	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	@DynamoDBAttribute
	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	@DynamoDBAttribute
	public String getPlaca() {
		return ano;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	@Override
	public String toString() {
		return "[Veiculo id=" + this.id + ", idCliente=" + this.idCliente + ", marca=" + this.marca + ", modelo="
				+ this.modelo + ", ano=" + this.ano + ", placa" + this.placa + "]";
	}
}
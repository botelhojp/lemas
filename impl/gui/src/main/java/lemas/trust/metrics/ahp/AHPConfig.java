package lemas.trust.metrics.ahp;

import java.io.FileReader;
import java.io.Reader;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AHPConfig {

	public List<AHPCliente> clientes;
	public List<AHPFornecedor> fornecedores;

	public AHPConfig() {
		clientes = new ArrayList<AHPCliente>();
		fornecedores = new ArrayList<AHPFornecedor>();
	}

	public AHPConfig(String file) {
		this();
		try {
			Gson gson = createGson();
			Reader reader = new FileReader(file);
			AHPConfig obj = gson.fromJson(reader, AHPConfig.class);
			System.out.println(gson.toJson(obj));
		} catch (Exception ex) {
			throw new RuntimeException("erro", ex);
		}
	}

	public Gson createGson() {
		Gson gson = new GsonBuilder().enableComplexMapKeySerialization().serializeNulls().setDateFormat(DateFormat.LONG)
				.setPrettyPrinting().setVersion(1.0).create();
		return gson;
	}

	public static void main(String[] args) {
		new AHPConfig("/home/vanderson/Dropbox/Doutorado/trabalho-otto/preferencias.json");
	}

}

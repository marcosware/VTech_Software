package VdeVigilancia.Projeto_OS.Application;

import VdeVigilancia.Projeto_OS.Dominio.Clientes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static VdeVigilancia.Projeto_OS.Application.Programa.em;

public class ProjetoOSApplication extends Application{

	@Override
	public void start(Stage stage) throws Exception {
		List<Clientes> resultados = em.createQuery("FROM Clientes", Clientes.class).getResultList();
		System.out.println("Qtd clientes: " + resultados.size()); // Deve exibir > 0
		resultados.forEach(c -> System.out.println(c.getNome()));
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/TelaPrincipal.fxml"));

		AnchorPane root = loader.load();

		Scene scene = new Scene(root);
		stage.setTitle("V de Vigilância");
		stage.setScene(scene);
		stage.show();
	}

}



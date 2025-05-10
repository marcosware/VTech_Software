package VdeVigilancia.Projeto_OS;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.Scanner;

@SpringBootApplication
public class ProjetoOsApplication extends Application {
	public static final Scanner sc = new Scanner(System.in);
	public static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA_V_Tech");
	public static final EntityManager em = emf.createEntityManager();
	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(ProjetoOsApplication.class.getResource("/TelaPrincipal.fxml"));
		AnchorPane root = fxmlLoader.load();
		Scene scene = new Scene(root);
		stage.setTitle("Hello!");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}


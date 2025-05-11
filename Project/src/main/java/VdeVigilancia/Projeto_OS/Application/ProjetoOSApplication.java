package VdeVigilancia.Projeto_OS.Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ProjetoOSApplication extends Application{

	@Override
	public void start(Stage stage) throws IOException {
		/* String fxmlPath = "TelaPrincipal.fxml"; // sem a barra inicial
		URL resource = getClass().getClassLoader().getResource(fxmlPath);
		if (resource == null) {
			System.err.println("Arquivo FXML não encontrado no caminho: " + fxmlPath);
			return;
		} */

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/TelaPrincipial.fxml"));
		AnchorPane root = loader.load();

		Scene scene = new Scene(root);
		stage.setTitle("V de Vigilância");
		stage.setScene(scene);
		stage.show();
	}

}



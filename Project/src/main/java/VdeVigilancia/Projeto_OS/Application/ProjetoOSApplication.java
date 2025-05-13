package VdeVigilancia.Projeto_OS.Application;

import VdeVigilancia.Projeto_OS.Database.DatabaseManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.stage.Stage;

@SpringBootApplication
public class ProjetoOSApplication extends Application {

	public static void main(String[] args) {

		// SpringApplication.run(BancoInterApplication.class, args);
		try {
			DatabaseManager.connectDB();
			DatabaseManager.defaultTables();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		launch(args);
	}

	@Override
	public void start (Stage primaryStage) {
		try{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/TelaLogin.fxml"));
			AnchorPane root = loader.load();
			Scene scene = new Scene(root);
			//primaryStage.getIcons().add(
			//		new Image(getClass().getResourceAsStream("/images/favicon.png")));
			primaryStage.setScene(scene);
			primaryStage.setTitle("VTech");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


}
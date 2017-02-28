package application;
	

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	static BorderPane root;
	
	@Override
	public void start(Stage primaryStage) {  
		try {
			
			root = (BorderPane) FXMLLoader.load(Main.class.getResource("RootLayout.fxml"));
			AnchorPane page = (AnchorPane) FXMLLoader.load(Main.class.getResource("HomePage.fxml"));			
			Scene scene = new Scene(root);
			root.setCenter(page);
			primaryStage.setScene(scene);
			primaryStage.setTitle("AlgoDomino");
			primaryStage.show();	       

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) {
		launch(args);
	}
}

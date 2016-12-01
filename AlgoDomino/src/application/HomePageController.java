package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class HomePageController {
	@FXML
    public void Compare(ActionEvent e) throws IOException {

		AnchorPane page = (AnchorPane) FXMLLoader.load(Main.class.getResource("ComparingPage.fxml"));
    	Main.root.setCenter(page);
    }
	
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

  }
}

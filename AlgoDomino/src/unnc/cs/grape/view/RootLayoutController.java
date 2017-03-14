/**
 * 
 */
package unnc.cs.grape.view;


import java.io.IOException;
import javafx.fxml.FXML;
import unnc.cs.grape.MainApp;


public class RootLayoutController {
	private MainApp mainapp;
	
	@FXML
	private void switchToMainFrame() throws IOException{
		mainapp.showMainFrame();
	}
	
	@FXML
	private void showEfficiencyInterface() throws IOException{
		mainapp.showEfficiencyInterface();
	}
	
	
	
	
}

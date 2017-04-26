package unnc.cs.grape.controller;

import com.jfoenix.controls.JFXColorPicker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class PreferenceController {

	 private Stage dialogStage;
	 public static Color color=Color.valueOf("#ADD8E6");

	 @FXML
	 private JFXColorPicker colorPicker;

	  /**
	   * Initializes the controller class. This method is automatically called
	   ** after the fxml file has been loaded.
	   */
	  @FXML
	  private void initialize() {
		  colorPicker.setValue(color);
	  }

	  /**
	   * Sets the stage of this dialog.
	   **
	   * @param dialogStage
	   */
	  public void setDialogStage(Stage dialogStage) {
	      this.dialogStage = dialogStage;
	  }

	    @FXML
	    private void handleApply() {
	    	color=colorPicker.getValue();
	    	int length= MainFrameController.getRectangle().size();
	    	for(int i=0;i<length;i++)
	    	{
	    		MainFrameController.getRectangle().get(i).setFill(color);
	    	}
	    	//System.out.println(colorPicker.getValue());
	        dialogStage.close();
	    }


}

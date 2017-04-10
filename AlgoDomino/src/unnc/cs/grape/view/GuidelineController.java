package unnc.cs.grape.view;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class GuidelineController {

	 private Stage dialogStage;

	  /**
	   * Initializes the controller class. This method is automatically called
	   ** after the fxml file has been loaded.
	   */
	  @FXML
	  private void initialize() {
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
	    private void handleGuide() {
	            dialogStage.close();
	    }
}

package unnc.cs.grape.view;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class PreferenceController {

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
	    private void handleApply() {
	            dialogStage.close();
	    }
}

package unnc.cs.grape.view;

import javafx.fxml.FXML;
import javafx.stage.Stage;


public class HelpBoxController {

	 private Stage dialogStage;
	 private boolean okClicked = false;


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
	  /**
	     * Returns true if the user clicked OK, false otherwise.
	     *
	     * @return
	     */
	    public boolean isOkClicked() {
	        return okClicked;
	    }

	    @FXML
	    private void handleOk() {
	            okClicked = true;
	            dialogStage.close();
	        }
}

package unnc.cs.grape.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;


public class HelpBoxController {

    private Stage dialogStage;

    /**
     * Sets the stage of this dialog.
     * *
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    @FXML
    private void handleOk() {
        dialogStage.close();
    }
}

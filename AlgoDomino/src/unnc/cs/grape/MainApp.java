package unnc.cs.grape;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import unnc.cs.grape.model.AlgorithmCode;
import unnc.cs.grape.controller.GuidelineController;
import unnc.cs.grape.controller.HelpBoxController;
import unnc.cs.grape.controller.PreferenceController;

/**
 * The type Main app.
 */
public class MainApp extends Application {

    private static Stage primaryStage;
    private static AnchorPane rootLayout;
    private static Stage dialogStage1;
    private static Stage dialogStage2;
    private static Stage dialogStage3;
    private static Scene mainScene;
    //static Scene colorScene=null;


    @Override
    public void start(Stage primarystage) {
        primaryStage = primarystage;
        primaryStage.setTitle("Visualizing sorting algorithms");
        primaryStage.setWidth(1034);
        primaryStage.setHeight(660);
        primaryStage.setResizable(false);
        initRootLayout();
        //showMainFrame();

        primaryStage.setOnCloseRequest(event -> {
            //System.out.print("Program close");
            if(dialogStage1!=null)
            {
                 dialogStage1.close();
            }
            if(dialogStage2!=null)
            {
                dialogStage2.close();
            }
            if(dialogStage3!=null)
            {
                dialogStage3.close();
            }
        });
    }

    /**
     * Initializes the root layout.
     */

    private void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainFrame.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
             mainScene = new Scene(rootLayout);
            primaryStage.setScene(mainScene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Show efficiency interface.
     */


    public static void showHelpBox() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/HelpBox.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            dialogStage1 = new Stage();
            dialogStage1.setTitle("About Domino");
            Scene scene = new Scene(page);
            dialogStage1.setScene(scene);

            // Set the person into the controller.
            HelpBoxController controller = loader.getController();
            controller.setDialogStage(dialogStage1);

            // Show the dialog and wait until the user closes it
            dialogStage1.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showPreference() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
        	FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(MainApp.class.getResource("view/Preference.fxml"));
    		AnchorPane page = loader.load();
    		Scene scene=new Scene(page);
            dialogStage2 = new Stage();
            dialogStage2.setTitle("Preference");
            dialogStage2.setScene(scene);
            PreferenceController controller = loader.getController();
            controller.setDialogStage(dialogStage2);
            // Show the dialog and wait until the user closes it
            dialogStage2.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showGuideline() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Guideline.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            dialogStage3 = new Stage();
            dialogStage3.setTitle("Domino Help");
            Scene scene = new Scene(page);
            dialogStage3.setScene(scene);

            // Set the person into the controller.
            GuidelineController controller = loader.getController();
            controller.setDialogStage(dialogStage3);

            // Show the dialog and wait until the user closes it
            dialogStage3.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void QuitPrograme() {
        primaryStage.close();
        if(dialogStage1!=null)
        {
        	 dialogStage1.close();
        }
        if(dialogStage2!=null)
        {
        	dialogStage2.close();
        }
        if(dialogStage3!=null)
        {
        	dialogStage3.close();
        }
    }

    public static void Screenshot() {
    	 WritableImage image = mainScene.snapshot(null);
         FileChooser fileChooser = new FileChooser();
         fileChooser.setTitle("Save Screenshot");
         FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
 		 fileChooser.getExtensionFilters().add(extFilter);
         File file = fileChooser.showSaveDialog(primaryStage);
         if(file != null)
         {
             try {
                 ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
             }
             catch (IOException e) {
                 System.out.println("Couldn't Save.");
             }
         }
    }

    public static void SaveAlgorithmCode(Integer algo, int language) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Algorithm Code");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(primaryStage);
        String code=null;
        if(algo==null)
        	System.out.println("No algorithm select");
        if(language==0)
        {
        	if(algo==0)
        		code= AlgorithmCode.javaBubble;
        	else if(algo==1)
        		code=AlgorithmCode.javaInsertion;
        	else if(algo==2)
        		code=AlgorithmCode.javaSelection;
        	else if(algo==3)
        		code=AlgorithmCode.javaQuick;
        	else if(algo==4)
        		code=AlgorithmCode.javaMerge;
        	else if(algo==5)
        		code=AlgorithmCode.javaHeap;
        }
        else if(language==1)
        {
        	if(algo==0)
        		code=AlgorithmCode.javaScriptBubble;
        	else if(algo==1)
        		code=AlgorithmCode.javaScriptInsertion;
        	else if(algo==2)
        		code=AlgorithmCode.javaScriptSelection;
        	else if(algo==3)
        		code=AlgorithmCode.javaScriptQuick;
        	else if(algo==4)
        		code=AlgorithmCode.javaScriptMerge;
        	else if(algo==5)
        		code=AlgorithmCode.javaScriptHeap;
        }

        if(file != null)
        {
            try {
        		FileWriter fileWriter;
        		fileWriter = new FileWriter(file);
        		fileWriter.write(code);
        		fileWriter.close();
            }
            catch (IOException e) {
                System.out.println("Couldn't Save.");
            }
        }
   }


    /**
     * Returns the main stage.
     *
     * @return primary stage
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}

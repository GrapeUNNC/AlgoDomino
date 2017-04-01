package unnc.cs.grape;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import unnc.cs.grape.view.GuidelineController;
import unnc.cs.grape.view.HelpBoxController;
import unnc.cs.grape.view.PreferenceController;

/**
 * The type Main app.
 */
public class MainApp extends Application {

    private static Stage primaryStage;
    private static BorderPane rootLayout;
    private static Stage dialogStage1;
    private static Stage dialogStage2;
    private static Stage dialogStage3;
    private static Scene mainScene;
    //static Scene colorScene=null;


    @Override
    public void start(Stage primarystage) {
        primaryStage = primarystage;
        primaryStage.setTitle("Visualizing sorting algorithms");
        primaryStage.setWidth(1024);
        primaryStage.setHeight(690);
        primaryStage.setResizable(false);
        initRootLayout();
        showMainFrame();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
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
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
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
     * Shows the person overview inside the root layout.
     */
    public static void showMainFrame() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Frame.fxml"));
            AnchorPane MainFrame = loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(MainFrame);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Show efficiency interface.
     */
    public static void showEfficiencyInterface() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/efficiency.fxml"));
            AnchorPane EfficiencyInterface = loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(EfficiencyInterface);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showHelpBox() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/HelpBox.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            dialogStage1 = new Stage();
            dialogStage1.setTitle("About Domino");
            //dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
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
    		AnchorPane page = (AnchorPane) loader.load();
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
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            dialogStage3 = new Stage();
            dialogStage3.setTitle("Domino Help");
            //dialogStage.initModality(Modality.WINDOW_MODAL);
            //dialogStage.initOwner(primaryStage);
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

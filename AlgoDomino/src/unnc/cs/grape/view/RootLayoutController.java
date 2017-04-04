package unnc.cs.grape.view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;

import javafx.animation.SequentialTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import unnc.cs.grape.MainApp;
import unnc.cs.grape.algorithm.AlgorithmCode;
import unnc.cs.grape.algorithm.AlgorithmController;

public class RootLayoutController extends AlgorithmController implements Initializable {
    private MainApp mainapp;

    private int[] input;
    private int[] defaultInput = { 4, 3, 2, 1, 5, 6, 9, 7, 8 };
    private double duration = 600;
    private Integer selectAlgo;
    private ArrayList<StackPane> list = new ArrayList<>();
    private SequentialTransition sq;

    @FXML
    private Button random;

    @FXML
    private TextField inputString;

    @FXML
    private HBox hbox;

    @FXML
    private ToggleButton bubble;

    @FXML
    private ToggleButton insertion;

    @FXML
    private ToggleButton selection;

    @FXML
    private ToggleButton quick;

    @FXML
    private ToggleButton merge;

    @FXML
    private ToggleButton heap;

    @FXML
    private ToggleGroup togglegroup;

    @FXML
    private JFXButton playButton;

    @FXML
    private Pane pane;

    @FXML
    private JFXSlider slider;

    @FXML
    private TextArea codeDisplay;

    @FXML
    private TextArea hintDisplay;

    @FXML
    private void switchToMainFrame() throws IOException {
        mainapp.showMainFrame();
    }

    @FXML
    private void showEfficiencyInterface() throws IOException {
        mainapp.showEfficiencyInterface();
    }

    /**
     * Press to start sorting
     */

    @FXML
    public void sortStart() {
        if(hbox.getChildren() == null || input == null) {
            System.out.println("Please initialize first...");
        } else if (selectAlgo == null) {
            System.out.println("Please choose a algorithm to run...");
        } else {
            sort(selectAlgo);
        }
    }

    @FXML
    public void speedChange() {
    	Slider slider = new Slider(100, 4000, 600);
        slider.valueProperty().addListener((observable, oldValue, newValue) -> duration = (double) newValue);
    }

    private void checkInput(String str) {
        // if has input
        if (str.length() > 0) {
            String[] sp = str.split("\\D+");
            int[] userInput = Stream.of(sp).mapToInt(Integer::parseInt).toArray();
            input = userInput;
            //System.out.println(Arrays.toString(userInput));
        } else {
            System.out.println("No input, use default input...");
            input = defaultInput;
        }
    }

    public void intializeRec() {
        // clear
        list.clear();
        hbox.getChildren().clear();

        // detected input part
        System.out.println("Get input...");

        String str = inputString.getText();
        checkInput(str);

        // generate rectangles
        generateRec();
    }

    public void randomInput() {
        System.out.println("Generate a random input");
        int[] random = new int[15];
        for (int i = 0; i < 15; i++) {
            random[i] = (int)(Math.random()*20 +1);
        }
        input = random;
        String strInput = Arrays.toString(random);
        inputString.clear();
        inputString.setText(strInput.substring(1, strInput.length()-1));
        //System.out.println(Arrays.toString(random));

        list.clear();
        hbox.getChildren().clear();
        generateRec();
    }

    /**
     * Create rectangles into Hbox
     */
    private void generateRec() {

    	Color shapeColor=PreferenceController.color;
        if (input == null || input.length == 0) {
            input = defaultInput;
        }

        for (int i = 0; i < input.length; i++) {
            Rectangle rectangle = new Rectangle(20, 20 * input[i]);
            rectangle.setFill(shapeColor);
            Text text = new Text(String.valueOf(input[i]));
            StackPane stackPane = new StackPane();
            stackPane.setPrefSize(rectangle.getWidth(), rectangle.getHeight());
            stackPane.setId(String.valueOf(input[i]));
            stackPane.getChildren().addAll(rectangle, text);
            stackPane.setAlignment(Pos.BOTTOM_CENTER);
            list.add(stackPane);
        }

        hbox.getChildren().addAll(list);
    }

    /**
     * Choose which algorithm to use
     */
    @FXML
    public void chooseAlgo() {
        // Choose algorithm
        bubble.setOnMouseClicked(event -> {
            selectAlgo = 0;
            System.out.println("Choose BubbleSort...");
            codeDisplay.setText(AlgorithmCode.javaBubble);
            hintDisplay.setText(AlgorithmCode.hintBubble);
        });
        insertion.setOnMouseClicked(event -> {
            selectAlgo = 1;
            System.out.println("Choose InsertionSort...");
            codeDisplay.setText(AlgorithmCode.javaInsertion);
            hintDisplay.setText(AlgorithmCode.hintInsertion);
        });
        selection.setOnMouseClicked(event -> {
            selectAlgo = 2;
            System.out.println("Choose SelectionSort...");
            codeDisplay.setText(AlgorithmCode.javaSelection);
            hintDisplay.setText(AlgorithmCode.hintSelection);
        });
        quick.setOnMouseClicked(event -> {
            selectAlgo = 3;
            System.out.println("Choose QuickSort...");
            codeDisplay.setText(AlgorithmCode.javaQuick);
            hintDisplay.setText(AlgorithmCode.hintQuick);
        });
        merge.setOnMouseClicked(event -> {
            selectAlgo = 4;
            System.out.println("Choose MergeSort...");
            codeDisplay.setText(AlgorithmCode.javaMerge);
            hintDisplay.setText(AlgorithmCode.hintMerge);
        });
        heap.setOnMouseClicked(event -> {
            selectAlgo = 5;
            System.out.println("Choose HeapSort...");
            codeDisplay.setText(AlgorithmCode.javaHeap);
            hintDisplay.setText(AlgorithmCode.hintHeap);
        });
    }

    private void sort(int selectAlgo) {
        sq = new SequentialTransition();

        switch (selectAlgo) {
            case 0:
                // BubbleSort
                sq = BubbleSort(input, list, duration);
                break;
            case 1:
                // InsertionSort
                sq = InsertionSort(input, list, duration);
                break;
            case 2:
                // SelectionSort
                sq = SelectionSort(input, list, duration);
                break;
            case 3:
                // QuickSort
                sq = quickSort(input, list, sq, duration);
                break;
            case 4:
                // MergeSort
                sq = BubbleSort(input, list, duration);
                break;
            case 5:
                // HeapSort
                sq = HeapSort(input, list, duration);
                break;
            default:
                break;
        }

        inputString.setEditable(false);
        sq.play();
        inputString.setEditable(true);
    }

    @FXML
    private void handleHelpBox() {
        MainApp.showHelpBox();
    }

    @FXML
    private void handlePreference() {
        MainApp.showPreference();
    }

    @FXML
    private void handleGuideline() {
        MainApp.showGuideline();
    }

    @FXML
    private void handleQuit() {
       MainApp.QuitPrograme();
    }

    @FXML
    private void handleScreenshot() {
       MainApp.Screenshot();
    }

    // haven`t use now
    public void setMainApp(MainApp mainApp) {
        this.mainapp = mainApp;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub

    }
}

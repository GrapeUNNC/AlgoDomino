/**
 *
 */
package unnc.cs.grape.view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import unnc.cs.grape.MainApp;

public class RootLayoutController implements Initializable {
    private MainApp mainapp;
    
    private int[] input;
    private int[] defaultInput = { 4, 3, 2, 1, 5, 9 };
    private final int duration = 600;
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
    private Button bubble;
    
    @FXML
    private Button insertion;
    
    @FXML
    private Button selection;
    
    @FXML
    private Button quick;
    
    @FXML
    private Button merge;
    
    @FXML
    private Button heap;
    
    @FXML
    private JFXButton playButton;
    
    @FXML
    private void switchToMainFrame() throws IOException {
        mainapp.showMainFrame();
    }
    
    @FXML
    private void showEfficiencyInterface() throws IOException {
        mainapp.showEfficiencyInterface();
    }
    
    /**
     * Press to start sorting getInput() part is not completed yet - JiayingSun
     */
    @FXML
    public void sortStart() {
        list.clear();
        hbox.getChildren().clear();
        System.out.println("Clear...");
        
        System.out.println("Pressing Start...");
        System.out.println("Generate Rectangles...");
        generateRec();
        
        if (selectAlgo == null) {
            System.out.println("Please choose a algorithm to run...");
        } else {
            sort(selectAlgo);
        }
        
        // System.out.println("Get input...");
        // if (input == null) {
        // input = defaultInput;
        // } else {
        // String str = inputString.getText();
        // if (!checkInput(str)) {
        // System.out.println("Please input correct string format");
        // inputString.clear();
        // }
        // }
        
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
        });
        insertion.setOnMouseClicked(event -> {
            selectAlgo = 1;
            System.out.println("Choose InsertionSort...");
        });
        selection.setOnMouseClicked(event -> {
            selectAlgo = 2;
            System.out.println("Choose SelectionSort...");
        });
        quick.setOnMouseClicked(event -> {
            selectAlgo = 3;
            System.out.println("Choose QuickSort...");
        });
        merge.setOnMouseClicked(event -> {
            selectAlgo = 4;
            System.out.println("Choose MergeSort...");
        });
        heap.setOnMouseClicked(event -> {
            selectAlgo = 5;
            System.out.println("Choose HeapSort...");
        });
    }
    
    public void randomInput() {
        System.out.println("Generate a random input");
    }
    
    /**
     * Create rectangles into Hbox
     */
    private void generateRec() {
        
        if (input == null) {
            // System.out.println("Use default input...");
            input = defaultInput;
        }
        
        for (int i = 0; i < input.length; i++) {
            Rectangle rectangle = new Rectangle(20, 20 * input[i]);
            rectangle.setFill(Color.valueOf("#ADD8E6"));
            Text text = new Text(String.valueOf(input[i]));
            StackPane stackPane = new StackPane();
            stackPane.setPrefSize(rectangle.getWidth(), rectangle.getHeight());
            stackPane.setId(String.valueOf(input[i]));
            stackPane.getChildren().addAll(rectangle, text);
            list.add(stackPane);
        }
        
        hbox.getChildren().addAll(list);
    }
    
    private ParallelTransition swap(StackPane l1, StackPane l2, ArrayList<StackPane> list, double speed) {
        TranslateTransition t1 = new TranslateTransition();
        TranslateTransition t2 = new TranslateTransition();
        t1.setDuration(Duration.millis(speed));
        t2.setDuration(Duration.millis(speed));
        ParallelTransition pl = new ParallelTransition();
        t1.setNode(l1);
        t2.setNode(l2);
        t1.setByX(30);
        t2.setByX(-30);
        pl.getChildren().addAll(t1, t2);
        Collections.swap(list, list.indexOf(l1), list.indexOf(l2));
        return pl;
    }
    
    /**
     * Something wrong about this function
     *
     * @param l1
     * @param l2
     * @param list
     * @param speed
     * @return
     */
    private ParallelTransition swapSelect(StackPane l1, StackPane l2, ArrayList<StackPane> list, double speed) {
        int num = 1;
        StackPane sp1 = null, sp2 = null, fSp = null;
        TranslateTransition t1 = new TranslateTransition();
        TranslateTransition t2 = new TranslateTransition();
        ParallelTransition pl = new ParallelTransition();
        t1.setNode(l1);
        t2.setNode(l2);
        t1.setDuration(Duration.millis(speed));
        t2.setDuration(Duration.millis(speed));
        boolean outerBreak = false;
        for (int i = 0; i < list.size(); i++) {
            if (outerBreak)
                break;
            if (list.get(i) == l1 || list.get(i) == l2) {
                fSp = list.get(i);
                for (int j = list.indexOf(fSp) + 1; j < list.size(); j++) {
                    if ((list.get(j) == l1 && list.get(j) != fSp) || (list.get(j) == l2 && list.get(j) != fSp)) {
                        outerBreak = true;
                        num = j - i;
                        break;
                    }
                }
            }
        }
        num *= 30;
        t1.setByX(num);
        t2.setByX(-num);
        Collections.swap(list, list.indexOf(l1), list.indexOf(l2));
        pl.getChildren().addAll(t1, t2);
        return pl;
    }
    
    /**
     * BubbleSort
     *
     * @param arr
     * @param list
     * @return
     */
    private SequentialTransition BubbleSort(int arr[], ArrayList<StackPane> list) {
        SequentialTransition sq = new SequentialTransition();
        int temp;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 1; j < arr.length - i; j++) {
                if (arr[j - 1] < arr[j]) {
                    temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                    sq.getChildren().add(swap(list.get(j - 1), list.get(j), list, duration));
                }
            }
        }
        return sq;
    }
    
    /**
     * Insertion Sort
     *
     * @param arr
     * @param list
     * @return
     */
    private SequentialTransition InsertionSort(int[] arr, ArrayList<StackPane> list) {
        SequentialTransition sq = new SequentialTransition();
        int temp;
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (arr[j] < arr[j - 1]) {
                    temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                    sq.getChildren().add(swap(list.get(j - 1), list.get(j), list, duration));
                } else {
                    break;
                }
            }
        }
        return sq;
    }
    
    /**
     * Selection Sort
     *
     * @param arr
     * @param list
     * @return
     */
    private SequentialTransition SelectionSort(int arr[], ArrayList<StackPane> list) {
        SequentialTransition sq = new SequentialTransition();
        int i, j, minIndex, tmp;
        int n = arr.length;
        for (i = 0; i < n - 1; i++) {
            minIndex = i;
            for (j = i + 1; j < n; j++)
                if (arr[j] < arr[minIndex])
                    minIndex = j;
            if (minIndex != i) {
                tmp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = tmp;
                sq.getChildren().add(swapSelect(list.get(i), list.get(minIndex), list, duration));
            }
        }
        return sq;
    }
    
    private void QuickSort() {
        // quick sort algorithm
    }
    
    private void MergeSort() {
        // merge sort algorithm
    }
    
    private void HeapSort() {
        // heap sort algorithm
    }
    
    private void restart() {
        // press to restart
    }
    
    private void sort(int selectAlgo) {
        sq = new SequentialTransition();
        
        switch (selectAlgo) {
            case 0:
                // BubbleSort
                sq = BubbleSort(input, list);
                break;
            case 1:
                // InsertionSort
                sq = InsertionSort(input, list);
                break;
            case 2:
                // SelectionSort
                sq = SelectionSort(input, list);
                break;
            case 3:
                // MergeSort
                sq = BubbleSort(input, list);
                break;
            case 4:
                // QuickSort
                sq = BubbleSort(input, list);
                break;
            case 5:
                // HeapSort
                sq = BubbleSort(input, list);
                break;
            default:
                break;
        }
        
        sq.play();
    }
    
    /**
     * this part's logic need to be changed Created by JiayingSun
     * 
     * @param str
     * @return
     */
    private Boolean checkInput(String str) {
        // TODO Auto-generated method stub
        boolean matchFormat = true;
        for (int i = 0; i < str.length(); i++) {
            // test all character consist of digit and ,
            if (!(Character.isDigit(str.charAt(i)) || str.charAt(i) == ',')) {
                matchFormat = false;
            }
            // test if two ,
            if (str.charAt(i) == ',' && str.charAt(i + 1) == ',') {
                matchFormat = false;
            }
        }
        // test first and last char is digit
        if (!Character.isDigit(str.charAt(str.length() - 1)) || !Character.isDigit(str.charAt(0))) {
            matchFormat = false;
        }
        
        if (matchFormat) {
            String[] split = str.split("\\D+");
            input = new int[split.length];
            for (int i = 0; i < split.length; i++) {
                input[i] = Integer.parseInt(split[i]);
            }
        }
        return matchFormat;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        
    }
}

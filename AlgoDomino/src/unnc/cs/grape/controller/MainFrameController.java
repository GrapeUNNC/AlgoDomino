package unnc.cs.grape.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;

import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import unnc.cs.grape.MainApp;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;


public class MainFrameController implements Initializable {
    private final MainApp mainapp = new MainApp();

    private static final int SPACING = 30;
    private int[] helper;
    private StackPane[] helperNodes;
    private static final int SORT_GROUP_MOVE_DELTA = 150;
    private static final Duration SPEED = Duration.millis(400);

    private int[] input;
    private final int[] defaultInput = {4, 3, 2, 1, 5, 6, 9, 7, 8};
    private double duration = 600;
    private String selectAlgo = null;
    private String languageSelect = "Java";
    private final ArrayList<StackPane> list = new ArrayList<>();
    private final ArrayList<StackPane> mergelist = new ArrayList<>();
    private static final ArrayList<Rectangle> recList = new ArrayList<>();
    private final ArrayList<ToggleButton> toggleList = new ArrayList<>();
    private final Image pause = new Image("unnc/cs/grape/view/assets/icon/pause.png", 44, 46, false, false);

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
    private ImageView playbutton;

    @FXML
    private Pane pane;

    @FXML
    private JFXSlider slider;

    @FXML
    private JFXSlider slider_c;

    @FXML
    private TextArea codeDisplay;

    @FXML
    private TextArea hintDisplay;

    @FXML
    private JFXButton startButton;

    @FXML
    private MenuItem bubble_c;

    @FXML
    private MenuItem selection_c;

    @FXML
    private MenuItem insertion_c;

    @FXML
    private MenuItem quick_c;

    @FXML
    private MenuItem merge_c;

    @FXML
    private MenuItem heap_c;

    @FXML
    private MenuItem bubble_c_2;

    @FXML
    private MenuItem selection_c_2;

    @FXML
    private MenuItem insertion_c_2;

    @FXML
    private MenuItem quick_c_2;

    @FXML
    private MenuItem merge_c_2;

    @FXML
    private MenuItem heap_c_2;

    @FXML
    private MenuButton algo1;

    @FXML
    private MenuButton algo2;

    @FXML
    private TextField inputString_c;

    @FXML
    private Label label_left;

    @FXML
    private Label label_right;
    
    @FXML
    private Label firstAlgoComplex;
    
    @FXML
    private Label secondAlgoComplex;

    @FXML
    private JFXComboBox<Label> combo1, combo2;
    private String compareAlgo1, compareAlgo2;


    /**
     * Press to start sorting
     */
    @FXML
    public void sortStart() {
        if (hbox.getChildren() == null || input == null) {
            System.out.println("Please initialize first...");
        } else if (selectAlgo == null) {
            System.out.println("Please choose a algorithm to run...");
        } else {
            playbutton.setImage(pause);
            sort(selectAlgo);
        }
    }

    @FXML
    public void speedChange() {
        slider.valueProperty().addListener((observable, oldValue, newValue) -> duration = 100 * (double) (newValue));
    }

    private void checkInput(String str) {
        // if has input
        if (str.length() > 0) {
            String[] sp = str.split("\\D+");
            input = Stream.of(sp).mapToInt(Integer::parseInt).toArray();
            // System.out.println(Arrays.toString(userInput));
        } else {
            System.out.println("No input, use default input...");
            input = defaultInput;
        }
    }

    public void clear() {
        inputString.clear();
    }

    public void intializeRec() {
        // clear
        mergelist.clear();
        pane.getChildren().clear();
        list.clear();
        hbox.getChildren().clear();

        // detected input part
        System.out.println("Get input...");
        String str = inputString.getText();
        checkInput(str);

        // generate rectangles
        generateRec();
    }

    private void intializeMergeRec() {
        // clear
        mergelist.clear();
        pane.getChildren().clear();
        list.clear();
        hbox.getChildren().clear();

        // detected input part
        System.out.println("Get input...");
        playbutton.setImage(pause);
        String str = inputString.getText();
        checkInput(str);

        // generate rectangles
        generateMergeRec();
    }

    public void randomInput() {
        System.out.println("Generate a random input");
        int[] random = new int[15];
        for (int i = 0; i < 15; i++) {
            random[i] = (int) (Math.random() * 15 + 0);
        }
        input = random;
        String strInput = Arrays.toString(random);
        inputString.clear();
        inputString.setText(strInput.substring(1, strInput.length() - 1));
        // System.out.println(Arrays.toString(random));

        mergelist.clear();
        list.clear();
        hbox.getChildren().clear();
        pane.getChildren().clear();
        generateRec();
    }

    /**
     * Create rectangles into Hbox
     */
    private void generateRec() {
        Color shapeColor = PreferenceController.color;

        if (input == null || input.length == 0) {
            input = defaultInput;
        }

        for (int anInput : input) {
            Rectangle rectangle = new Rectangle(20, 20 * anInput);
            rectangle.setFill(shapeColor);
            recList.add(rectangle);
            Text text = new Text(String.valueOf(anInput));
            StackPane stackPane = new StackPane();
            stackPane.setPrefSize(rectangle.getWidth(), rectangle.getHeight());
            stackPane.setId(String.valueOf(anInput));
            stackPane.getChildren().addAll(rectangle, text);
            stackPane.setAlignment(Pos.BOTTOM_CENTER);
            list.add(stackPane);
        }

        hbox.getChildren().addAll(list);


    }


    private void generateMergeRec() {

        if (input == null || input.length == 0) {
            input = defaultInput;
        }

        for (int i = 0; i < input.length; i++) {
            Rectangle rectangle = new Rectangle(20, 20 * input[i]);
            rectangle.setFill((Color.valueOf("#FF7F50")));
            recList.add(rectangle);
            Text text = new Text(String.valueOf(input[i]));
            StackPane stackPane = new StackPane();
            stackPane.setPrefSize(rectangle.getWidth(), rectangle.getHeight());
            stackPane.setId(String.valueOf(input[i]));
            stackPane.getChildren().addAll(rectangle, text);
            stackPane.setAlignment(Pos.BOTTOM_CENTER);
            stackPane.setTranslateX(SPACING * i);
            mergelist.add(stackPane);
        }


        pane.getChildren().addAll(mergelist);
    }

    private void displayCode(String language, String algo) {
        String fileName = "./code/" + algo + language + ".txt";
        try {
            codeDisplay.setText(new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Choose which algorithm to use
     */
    @FXML
    public void chooseAlgo() {
        toggleList.forEach(button -> button.setOnMouseClicked(event -> {
            selectAlgo = button.getAccessibleText();
            System.out.println(selectAlgo);
            displayCode(languageSelect, selectAlgo);
        }));

//		// Choose algorithm
//		bubble.setOnMouseClicked(event -> {
//			selectAlgo = "bubble";
//			System.out.println("Choose BubbleSort...");
//            displayCode(languageSelect, "bubble");
//		});
//		insertion.setOnMouseClicked(event -> {
//			selectAlgo = "insert";
//			System.out.println("Choose InsertionSort...");
//            displayCode(languageSelect, "insert");
//		});
//		selection.setOnMouseClicked(event -> {
//			selectAlgo = "select";
//			System.out.println("Choose SelectionSort...");
//            displayCode(languageSelect, "select");
//		});
//		quick.setOnMouseClicked(event -> {
//			selectAlgo = "quick";
//			System.out.println("Choose QuickSort...");
//            displayCode(languageSelect, "quick");
//		});
//		merge.setOnMouseClicked(event -> {
//			selectAlgo = "merge";
//			System.out.println("Choose MergeSort...");
//            displayCode(languageSelect, "merge");
//		});
//		heap.setOnMouseClicked(event -> {
//			selectAlgo = "heap";
//			System.out.println("Choose HeapSort...");
//            displayCode(languageSelect, "heap");
//		});
    }

    private void sort(String selectAlgo) {
        // inputString.setDisable(true);
        SequentialTransition sq = new SequentialTransition();

        switch (selectAlgo) {
            case "bubble":
                intializeRec();
                sq = BubbleSort(input, list, duration);
                break;
            case "insert":
                intializeRec();
                sq = InsertionSort(input, list, duration);
                break;
            case "select":
                intializeRec();
                sq = SelectionSort(input, list, duration);
                break;
            case "quick":
                intializeRec();
                sq = quickSort(input, list, sq, duration);
                break;
            case "merge":
                intializeMergeRec();
                sq = MergeSort(input, mergelist, sq);
                break;
            case "heap":
                intializeRec();
                sq = HeapSort(input, list, duration);
                break;
            default:
                break;
        }

        sq.play();
        String str = inputString.getText();
        checkInput(str);
        // inputString.setDisable(false);
    }

    @FXML
    private void handleHelpBox() {
        mainapp.showHelpBox();
    }

    @FXML
    private void handlePreference() {
        mainapp.showPreference();
    }

    @FXML
    private void handleSaveCode() {
        mainapp.SaveAlgorithmCode(selectAlgo, languageSelect);
    }

    @FXML
    private void handleGuideline() {
        mainapp.showGuideline();
    }

    @FXML
    private void handleQuit() {
        mainapp.QuitProgram();
    }

    @FXML
    private void handleScreenshot() {
        mainapp.Screenshot();
    }

    @FXML
    private void handleJava() {
        languageSelect = "Java";
        displayCode(languageSelect, selectAlgo);
    }

    @FXML
    private void handleJavaScript() {
        languageSelect = "JS";
        displayCode(languageSelect, selectAlgo);
    }

    @FXML
    private void handleC() {
        languageSelect = "C";
        displayCode(languageSelect, selectAlgo);
    }

    @FXML
    private void handlePython() {
        languageSelect = "Python";
        displayCode(languageSelect, selectAlgo);
    }

    // sort control part
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

    private ParallelTransition swap(StackPane l1, StackPane l2, int xLength, ArrayList<StackPane> list, double speed) {
        if (xLength < 0) {
            xLength = 0 - xLength;
        }

        TranslateTransition t1 = new TranslateTransition();
        TranslateTransition t2 = new TranslateTransition();
        t1.setDuration(Duration.millis(speed));
        t2.setDuration(Duration.millis(speed));
        ParallelTransition pl = new ParallelTransition();
        t1.setNode(l1);
        t2.setNode(l2);
        t1.setByX(xLength * 30);
        t2.setByX((0 - xLength) * 30);
        pl.getChildren().addAll(t1, t2);
        Collections.swap(list, list.indexOf(l1), list.indexOf(l2));
        return pl;
    }

    /**
     * Function working for selection sort
     *
     * @param l1
     * @param l2
     * @param list
     * @param speed
     * @return
     */
    private ParallelTransition swapSelect(StackPane l1, StackPane l2, ArrayList<StackPane> list, double speed) {
        int num = 1;
        StackPane fSp;
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
    private SequentialTransition BubbleSort(int arr[], ArrayList<StackPane> list, double duration) {
        SequentialTransition sq = new SequentialTransition();
        int temp;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 1; j < arr.length - i; j++) {
                if (arr[j] < arr[j - 1]) {
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
    private SequentialTransition InsertionSort(int[] arr, ArrayList<StackPane> list, double duration) {
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
    private SequentialTransition SelectionSort(int arr[], ArrayList<StackPane> list, double duration) {
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

    private ParallelTransition swapHeap1(StackPane l1, StackPane l2, ArrayList<StackPane> list, double speed,
                                         int parent, int child) {
        TranslateTransition t1 = new TranslateTransition();
        TranslateTransition t2 = new TranslateTransition();
        t1.setDuration(Duration.millis(speed));
        t2.setDuration(Duration.millis(speed));
        ParallelTransition pl = new ParallelTransition();
        t1.setNode(l1);
        t2.setNode(l2);
        int num = child - parent;
        num *= 30;
        t1.setByX(num);
        t2.setByX(-num);
        pl.getChildren().addAll(t1, t2);
        Collections.swap(list, list.indexOf(l1), list.indexOf(l2));
        return pl;
    }

    private ParallelTransition swapHeap2(StackPane l1, StackPane l2, ArrayList<StackPane> list, double speed,
                                         int parent, int child) {
        TranslateTransition t1 = new TranslateTransition();
        TranslateTransition t2 = new TranslateTransition();
        t1.setDuration(Duration.millis(speed));
        t2.setDuration(Duration.millis(speed));
        ParallelTransition pl = new ParallelTransition();
        t1.setNode(l1);
        t2.setNode(l2);
        int num = child - parent;
        num *= 30;
        t1.setByX(num);
        t1.setToY(0);
        t2.setByX(-num);
        t2.setToY(0);
        pl.getChildren().addAll(t1, t2);
        Collections.swap(list, list.indexOf(l1), list.indexOf(l2));
        return pl;
    }

    private ParallelTransition swapHeap3(StackPane l1, StackPane l2, double speed) {
        TranslateTransition t1 = new TranslateTransition();
        TranslateTransition t2 = new TranslateTransition();
        t1.setDuration(Duration.millis(speed));
        t2.setDuration(Duration.millis(speed));
        ParallelTransition pl = new ParallelTransition();
        t1.setNode(l1);
        t2.setNode(l2);
        t1.setToY(-100);
        t2.setToY(-100);
        pl.getChildren().addAll(t1, t2);
        return pl;
    }

    private SequentialTransition HeapSort(int arr[], ArrayList<StackPane> list, double duration) {
        SequentialTransition sq = new SequentialTransition();
        for (int i = arr.length / 2; i >= 0; i--) {
            int parent = i;
            int temp = arr[parent];
            int child = 2 * parent + 1;
            while (child < arr.length) {
                if (child + 1 < arr.length && arr[child] < arr[child + 1]) {
                    child++;
                }
                if (temp >= arr[child])
                    break;
                temp = arr[child];
                arr[child] = arr[parent];
                arr[parent] = temp;
                temp = arr[child];
                sq.getChildren().add(swapHeap1(list.get(parent), list.get(child), list, duration, parent, child));
                parent = child;
                child = 2 * child + 1;
            }
        }

        for (int i = arr.length - 1; i > 0; i--) {
            int temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;
            sq.getChildren().add(swapHeap3(list.get(0), list.get(i), duration));
            sq.getChildren().add(swapHeap2(list.get(0), list.get(i), list, duration, 0, i));
            int parent = 0;
            int tempFather = arr[parent];
            int child = 2 * parent + 1;
            while (child < i) {
                if (child + 1 < i && arr[child] < arr[child + 1]) {
                    child++;
                }
                if (tempFather >= arr[child])
                    break;
                tempFather = arr[child];
                arr[child] = arr[parent];
                arr[parent] = tempFather;
                tempFather = arr[child];
                sq.getChildren().add(swapHeap1(list.get(parent), list.get(child), list, duration, parent, child));
                parent = child;
                child = 2 * child + 1;
            }
        }
        return sq;
    }

    private List<Animation> quickSortRec(int arr[], int start, int end, ArrayList<StackPane> list,
                                         List<Animation> animationsList, double duration) {
        if (start >= end)
            return animationsList;
        int mid = arr[end];
        int left = start, right = end - 1;
        while (left < right) {
            while (arr[left] <= mid && left < right)
                left++;
            while (arr[right] >= mid && left < right)
                right--;
            int temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            // System.out.println("SWAP " + list.get(left) + " AND " +
            // list.get(right));
            animationsList.add(swap(list.get(left), list.get(right), left - right, list, duration));
        }
        if (arr[left] >= arr[end]) {
            int temp = arr[left];
            arr[left] = arr[end];
            arr[end] = temp;
            // System.out.println("SWAP " + list.get(left) + " AND " +
            // list.get(end));
            animationsList.add(swap(list.get(left), list.get(end), left - end, list, duration));
        } else
            left++;

        quickSortRec(arr, start, left - 1, list, animationsList, duration);
        quickSortRec(arr, left + 1, end, list, animationsList, duration);
        return animationsList;
    }

    private SequentialTransition quickSort(int arr[], ArrayList<StackPane> list, SequentialTransition sq,
                                           double duration) {
        List<Animation> animationList = new ArrayList<>();
        animationList = quickSortRec(arr, 0, arr.length - 1, list, animationList, duration);
        sq.getChildren().addAll(animationList);
        return sq;
    }

    private TranslateTransition move(StackPane sp, int X) {
        TranslateTransition t = new TranslateTransition();
        t.setNode(sp);
        t.setDuration(SPEED);
        t.setToX(X);
        t.setToY(SORT_GROUP_MOVE_DELTA);
        return t;

    }

    private SequentialTransition MergeSort(int arr[], ArrayList<StackPane> list, SequentialTransition sq) {
        int number = arr.length;
        this.helper = new int[number];
        this.helperNodes = new StackPane[number];
        sortRange(0, number - 1, arr, sq, list);
        return sq;
    }

    private void sortRange(int low, int high, int arr[], SequentialTransition sq, ArrayList<StackPane> list) {
        // check if low is smaller then high, if not then the array is sorted
        if (low < high) {
            // Get the index of the element which is in the middle
            int middle = low + (high - low) / 2;
            // Sort the left side of the array
            sortRange(low, middle, arr, sq, list);
            // Sort the right side of the array
            sortRange(middle + 1, high, arr, sq, list);
            // Combine them both
            merge(low, middle, high, arr, list, sq);
        }
    }


    private void merge(int low, int middle, int high, int arr[], ArrayList<StackPane> list, SequentialTransition sq) {
        // Copy both parts into the helper array
        for (int i = low; i <= high; i++) {
            helper[i] = arr[i];
            helperNodes[i] = list.get(i);
        }

        int i = low;
        int j = middle + 1;
        int k = low;
        // Copy the smallest values from either the left or the right side back
        // to the original array

        while (i <= middle && j <= high) {
            if (helper[i] <= helper[j]) {
                arr[k] = helper[i];
                list.set(k, helperNodes[i]);
                sq.getChildren().add(move(helperNodes[i], k * SPACING));
                i++;
            } else {
                arr[k] = helper[j];
                list.set(k, helperNodes[j]);
                sq.getChildren().add(move(helperNodes[j], k * SPACING));
                j++;
            }
            k++;
        }
        // Copy the rest of the left side of the array into the target array
        while (i <= middle) {
            arr[k] = helper[i];
            list.set(k, helperNodes[i]);
            sq.getChildren().add(move(helperNodes[i], k * SPACING));
            k++;
            i++;
        }

        // Even if we didn't move in the array because it was already ordered,
        // move on screen for any remaining nodes in the target array.
        while (j <= high) {
            sq.getChildren().add(move(helperNodes[j], k * SPACING));
            k++;
            j++;
        }

        ParallelTransition moveUp = new ParallelTransition();

        for (int z = low; z <= high; z++) {
            TranslateTransition moveNodeUp = new TranslateTransition();
            moveNodeUp.setNode(helperNodes[z]);
            moveNodeUp.setDuration(SPEED);
            moveNodeUp.setByY(-SORT_GROUP_MOVE_DELTA);
            moveUp.getChildren().add(moveNodeUp);
        }

        sq.getChildren().add(moveUp);
    }


    public void chooseFirstAlgo() {
        compareAlgo1 = combo1.getSelectionModel().getSelectedItem().getAccessibleText();
        label_right.setText(combo1.getSelectionModel().getSelectedItem().getText());
        firstAlgoComplex.setText(combo1.getSelectionModel().getSelectedItem().getText());
    }

    public void chooseSecAlgo() {
        compareAlgo2 = combo2.getSelectionModel().getSelectedItem().getAccessibleText();
        label_left.setText(combo2.getSelectionModel().getSelectedItem().getText());
        secondAlgoComplex.setText(combo1.getSelectionModel().getSelectedItem().getText());
    }


    public static ArrayList<Rectangle> getRectangle() {
        return recList;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        toggleList.add(bubble);
        toggleList.add(insertion);
        toggleList.add(selection);
        toggleList.add(quick);
        toggleList.add(merge);
        toggleList.add(heap);
    }
}

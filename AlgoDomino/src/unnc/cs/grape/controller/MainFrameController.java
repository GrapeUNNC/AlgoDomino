package unnc.cs.grape.controller;

import com.jfoenix.controls.JFXButton;
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
import unnc.cs.grape.model.AlgorithmCode;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class MainFrameController implements Initializable {
	private MainApp mainapp;

	private int[] input;
	private int[] defaultInput = { 4, 3, 2, 1, 5, 6, 9, 7, 8 };
	private double duration = 600;
	private static Integer selectAlgo;
	private static int languageSelect = 0;
	private String displayCode = null;
	private ArrayList<StackPane> list = new ArrayList<>();
	private static ArrayList<Rectangle> recList = new ArrayList<>();
	private SequentialTransition sq;
	private Image pause = new Image("unnc/cs/grape/view/assets/icon/pause.png", 44, 46, false, false);

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
	private TextArea codeDisplay;

	@FXML
	private TextArea hintDisplay;

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
			int[] userInput = Stream.of(sp).mapToInt(Integer::parseInt).toArray();
			input = userInput;
			// System.out.println(Arrays.toString(userInput));
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
		playbutton.setImage(pause);
		String str = inputString.getText();
		checkInput(str);

		// generate rectangles
		generateRec();
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

		list.clear();
		hbox.getChildren().clear();
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

		for (int i = 0; i < input.length; i++) {
			Rectangle rectangle = new Rectangle(20, 20 * input[i]);
			rectangle.setFill(shapeColor);
			recList.add(rectangle);
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
			if (languageSelect == 0)
				displayCode = AlgorithmCode.javaBubble;
			else if (languageSelect == 1)
				displayCode = AlgorithmCode.javaScriptBubble;

			codeDisplay.setText(displayCode);
			hintDisplay.setText(AlgorithmCode.hintBubble);
		});
		insertion.setOnMouseClicked(event -> {
			selectAlgo = 1;
			System.out.println("Choose InsertionSort...");
			if (languageSelect == 0)
				displayCode = AlgorithmCode.javaInsertion;
			else if (languageSelect == 1)
				displayCode = AlgorithmCode.javaScriptInsertion;

			codeDisplay.setText(displayCode);
			hintDisplay.setText(AlgorithmCode.hintInsertion);
		});
		selection.setOnMouseClicked(event -> {
			selectAlgo = 2;
			System.out.println("Choose SelectionSort...");
			if (languageSelect == 0)
				displayCode = AlgorithmCode.javaSelection;
			else if (languageSelect == 1)
				displayCode = AlgorithmCode.javaScriptSelection;

			codeDisplay.setText(displayCode);
			hintDisplay.setText(AlgorithmCode.hintSelection);
		});
		quick.setOnMouseClicked(event -> {
			selectAlgo = 3;
			System.out.println("Choose QuickSort...");
			if (languageSelect == 0)
				displayCode = AlgorithmCode.javaQuick;
			else if (languageSelect == 1)
				displayCode = AlgorithmCode.javaScriptQuick;

			codeDisplay.setText(displayCode);
			hintDisplay.setText(AlgorithmCode.hintQuick);
		});
		merge.setOnMouseClicked(event -> {
			selectAlgo = 4;
			System.out.println("Choose MergeSort...");
			if (languageSelect == 0)
				displayCode = AlgorithmCode.javaMerge;
			else if (languageSelect == 1)
				displayCode = AlgorithmCode.javaScriptMerge;

			codeDisplay.setText(displayCode);
			hintDisplay.setText(AlgorithmCode.hintMerge);
		});
		heap.setOnMouseClicked(event -> {
			selectAlgo = 5;
			System.out.println("Choose HeapSort...");
			if (languageSelect == 0)
				displayCode = AlgorithmCode.javaHeap;
			else if (languageSelect == 1)
				displayCode = AlgorithmCode.javaScriptHeap;

			codeDisplay.setText(displayCode);
			hintDisplay.setText(AlgorithmCode.hintHeap);
		});
	}

	private void sort(int selectAlgo) {
		// inputString.setDisable(true);
		intializeRec();
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

		sq.play();
		String str = inputString.getText();
		checkInput(str);
		// inputString.setDisable(false);
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
	private void handleSaveCode() {
		MainApp.SaveAlgorithmCode(selectAlgo, languageSelect);
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

	@FXML
	private void handleJava() {
		languageSelect = 0;
		String code = null;
		if (selectAlgo == 0)
			code = AlgorithmCode.javaBubble;
		else if (selectAlgo == 1)
			code = AlgorithmCode.javaInsertion;
		else if (selectAlgo == 2)
			code = AlgorithmCode.javaSelection;
		else if (selectAlgo == 3)
			code = AlgorithmCode.javaQuick;
		else if (selectAlgo == 4)
			code = AlgorithmCode.javaMerge;
		else if (selectAlgo == 5)
			code = AlgorithmCode.javaHeap;

		codeDisplay.setText(code);

	}

	@FXML
	private void handleJavaScript() {
		languageSelect = 1;
		String code = null;
		if (selectAlgo == 0)
			code = AlgorithmCode.javaScriptBubble;
		else if (selectAlgo == 1)
			code = AlgorithmCode.javaScriptInsertion;
		else if (selectAlgo == 2)
			code = AlgorithmCode.javaScriptSelection;
		else if (selectAlgo == 3)
			code = AlgorithmCode.javaScriptQuick;
		else if (selectAlgo == 4)
			code = AlgorithmCode.javaScriptMerge;
		else if (selectAlgo == 5)
			code = AlgorithmCode.javaScriptHeap;

		codeDisplay.setText(code);
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
	protected SequentialTransition BubbleSort(int arr[], ArrayList<StackPane> list, double duration) {
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
	protected SequentialTransition InsertionSort(int[] arr, ArrayList<StackPane> list, double duration) {
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
	protected SequentialTransition SelectionSort(int arr[], ArrayList<StackPane> list, double duration) {
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

	protected SequentialTransition HeapSort(int arr[], ArrayList<StackPane> list, double duration) {
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

	protected SequentialTransition quickSort(int arr[], ArrayList<StackPane> list, SequentialTransition sq,
			double duration) {
		List<Animation> animationList = new ArrayList<>();
		animationList = quickSortRec(arr, 0, arr.length - 1, list, animationList, duration);
		sq.getChildren().addAll(animationList);
		return sq;
	}

	protected void MergeSort() {
		// merge sort algorithm
	}

	// Compare Part
	
	
	// haven`t use now
	public void setMainApp(MainApp mainApp) {
		this.mainapp = mainApp;
	}

	public static ArrayList<Rectangle> getRectangle() {
		return recList;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
}

package unnc.cs.grape.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
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
import unnc.cs.grape.algorithm.Algorithm_c;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

/**
 * The type Main frame controller.
 */
public class MainFrameController extends Algorithm_c implements Initializable {
	private final MainApp mainapp = new MainApp();

	private SequentialTransition st = new SequentialTransition();
	private double dist;
	private int[] input;
	private int[] input_c;
	private final int[] defaultInput = { 4, 3, 2, 1, 5, 6, 9, 7, 8 };
	private int sortOrder = 0;
	private double duration = 600;
	private double duration_c = 10;
	private String selectAlgo = null;
	private String languageSelect = "Java";
	private final ArrayList<StackPane> list = new ArrayList<>();
	private final ArrayList<StackPane> list_l = new ArrayList<>();
	private final ArrayList<StackPane> list_r = new ArrayList<>();
	private static ArrayList<Rectangle> recList = new ArrayList<>();
	private final ArrayList<ToggleButton> toggleList = new ArrayList<>();
	private static Color color_change = Color.valueOf("#1565C0");

	private final Image pause = new Image("unnc/cs/grape/view/assets/icon/pause.png", 44, 46, false, false);
	private final Image play = new Image("unnc/cs/grape/view/assets/icon/play.png", 44, 46, false, false);
	private final Image bubbleGif = new Image("unnc/cs/grape/view/assets/icon/bubble.gif", 92, 76, false, false);
	private final Image bubbleJpg = new Image("unnc/cs/grape/view/assets/icon/bubble.jpg", 92, 76, false, false);

	@FXML
	private TextField inputString;

	@FXML
	private HBox hbox;

	@FXML
	private HBox hbox_left;

	@FXML
	private HBox hbox_right;

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
	private ImageView playbutton;

	@FXML
	private ImageView bubbleImg;
	@FXML
	private ImageView insertionImg;
	@FXML
	private ImageView selectionImg;
	@FXML
	private ImageView quickImg;
	@FXML
	private ImageView mergeImg;
	@FXML
	private ImageView heapImg;

	@FXML
	private Pane pane;

	@FXML
	private JFXSlider volumeSlider;

	@FXML
	private Slider timeSlider;

	@FXML
	private JFXSlider slider_c;

	@FXML
	private TextArea codeDisplay;

	@FXML
	private TextArea hintDisplay;

	@FXML
	private JFXButton start;

	@FXML
	private TextField inputString_c;

	@FXML
	private Label label_left;

	@FXML
	private Label label_right;

	@FXML
	private Label time_left;

	@FXML
	private Label time_right;

	@FXML
	private JFXComboBox<Label> combo1, combo2;

	@FXML
	private Label algo1, algo2, c1, c2;

	private String compareAlgo1, compareAlgo2;

	/**
	 * Press to start sorting
	 */
	@FXML
	public void sortStart() {
		if (hbox.getChildren() == null || input == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Please initialize first!");
			alert.showAndWait();

			System.out.println("Please initialize first...");
		} else if (selectAlgo == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Please choose an algorithm to run!");
			alert.showAndWait();
		} else {
			sort(selectAlgo);
		}
	}

	private void changePlayButton() {
		playbutton.setId("playbutton");
		playbutton.setImage(play);
		start.setOnAction(event -> sortStart());
	}

	private void changePauseButton() {
		playbutton.setId("pausebutton");
		playbutton.setImage(pause);
		start.setOnAction(event -> {
			st.pause();
			changeReplayButton();
		});
	}

	private void changeReplayButton() {
		playbutton.setId("replaybutton");
		playbutton.setImage(play);
		start.setOnAction(event -> {
			st.play();
			timeSlider.setDisable(false);
			changePauseButton();
		});
	}

	public void timeChange() {
		timeSlider.valueProperty().addListener(e -> {
			if (timeSlider.isValueChanging() && timeSlider.getValue() != 0) {
				st.pause();
				st.playFrom(st.getTotalDuration().multiply(timeSlider.getValue() / 100));
				if (playbutton.getId() == "replaybutton") {
					st.pause();
				}
			}
		});
	}

	private void updateProgress() {
		Platform.runLater(() -> {
			Duration currentTime = st.getCurrentTime();
			Duration duration = st.getTotalDuration();
			if (!currentTime.equals(duration)) {
				timeSlider.setValue(currentTime.divide(duration.toMillis()).toMillis() * 100);
			} else {
				timeSlider.setDisable(true);
				st.stop();
			}
		});
	}

	/**
	 * Speed change.
	 */
	@FXML
	public void speedChange() {
		volumeSlider.valueProperty()
				.addListener((observable, oldValue, newValue) -> st.setRate((double) (newValue) / 40));
	}

	@FXML
	public void bubbleEnter() {
		bubbleImg.setImage(bubbleGif);
	}

	@FXML
	public void bubbleExit() {
		bubbleImg.setImage(bubbleJpg);
	}

	@FXML
	public void insertionEnter() {
		insertionImg.setImage(new Image("unnc/cs/grape/view/assets/icon/insertion.gif", 92, 76, false, false));
	}

	@FXML
	public void insertionExit() {
		insertionImg.setImage(new Image("unnc/cs/grape/view/assets/icon/insertion.jpg", 92, 76, false, false));
	}

	@FXML
	public void selectionEnter() {
		selectionImg.setImage(new Image("unnc/cs/grape/view/assets/icon/selection.gif", 92, 76, false, false));
	}

	@FXML
	public void selectionExit() {
		selectionImg.setImage(new Image("unnc/cs/grape/view/assets/icon/selection.jpg", 92, 76, false, false));
	}

	@FXML
	public void quickEnter() {
		quickImg.setImage(new Image("unnc/cs/grape/view/assets/icon/quick.gif", 92, 76, false, false));
	}

	@FXML
	public void quickExit() {
		quickImg.setImage(new Image("unnc/cs/grape/view/assets/icon/quick.jpg", 92, 76, false, false));
	}

	@FXML
	public void mergeEnter() {
		mergeImg.setImage(new Image("unnc/cs/grape/view/assets/icon/merge.gif", 92, 76, false, false));
	}

	@FXML
	public void mergeExit() {
		mergeImg.setImage(new Image("unnc/cs/grape/view/assets/icon/merge.jpg", 92, 76, false, false));
	}

	@FXML
	public void heapEnter() {
		heapImg.setImage(new Image("unnc/cs/grape/view/assets/icon/heap.gif", 92, 76, false, false));
	}

	@FXML
	public void heapExit() {
		heapImg.setImage(new Image("unnc/cs/grape/view/assets/icon/heap.jpg", 92, 76, false, false));
	}

	/**
	 * Clear.
	 */
	public void clear() {
		inputString.clear();
	}

	/**
	 * Initialize rec.
	 */
	public void initializeRec() {
		// clear
		// mergeList.clear();s
		pane.getChildren().clear();
		recList.clear();
		list.clear();
		hbox.getChildren().clear();

		String str = inputString.getText();
		checkInput(str);
		try {
			if (input.length > 15) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning Dialog");
				alert.setHeaderText(null);
				alert.setContentText("The input size should less than 15!");
				alert.showAndWait();
			} else {
				if (!checkMaxAndMinInput()) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning Dialog");
					alert.setHeaderText(null);
					alert.setContentText("The input integer shouldn't be larger than 15 or smaller than 1!");
					alert.showAndWait();
				} else {
					generateRec();
				}
			}
		} catch (NullPointerException e) {
			System.out.println("---Exception---");
		}
	}

	private boolean checkMaxAndMinInput() {
		for (int i : input) {
			if (i > 15)
				return false;
			if (i < 1)
				return false;
		}
		return true;
	}

	private void checkInput(String str) {
		// if has input
		if (str.length() > 0) {
			char ch = str.charAt(0);
			if (Character.isDigit(ch)) {
				String[] sp = str.split("\\D+");
				input = Stream.of(sp).mapToInt(Integer::parseInt).toArray();
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning Dialog");
				alert.setHeaderText(null);
				alert.setContentText("The input should start with an integer!");
				alert.showAndWait();
			}
		} else {
			System.out.println("No input, use default input...");
			input = defaultInput;
		}
	}

	/**
	 * Random input.
	 */
	public void randomInput() {
		System.out.println("Generate a random input");
		int[] random = new int[15];
		for (int i = 0; i < 15; i++) {
			random[i] = (int) (Math.random() * 15 + 1);
		}
		input = random;
		String strInput = Arrays.toString(random);
		inputString.clear();
		inputString.setText(strInput.substring(1, strInput.length() - 1));

		recList.clear();
		list.clear();
		hbox.getChildren().clear();
		pane.getChildren().clear();
		generateRec();
        st.stop();
        changePlayButton();
		timeSlider.setValue(0);
	}

	/**
	 * Create rectangles into Hbox
	 */
	private void generateRec() {
		Color shapeColor = PreferenceController.color;

		if (input == null || input.length == 0) {
			input = defaultInput.clone();
		}

		for (int anInput : input) {
			Rectangle rectangle = new Rectangle(20, 20 * anInput);
			rectangle.setFill(shapeColor);
			recList.add(rectangle);

			Text text = new Text(String.valueOf(anInput));
			StackPane sp = new StackPane();
			sp.setPrefSize(rectangle.getWidth(), rectangle.getHeight());
			sp.setId(String.valueOf(anInput));
			sp.getChildren().addAll(rectangle, text);
			sp.setAlignment(Pos.BOTTOM_CENTER);
			list.add(sp);
		}

		recList.get(0).accessibleTextProperty().addListener(e -> {
            Animation.Status s = st.getStatus();
            Duration d = st.getCurrentTime();
            sort(selectAlgo);
            st.playFrom(d);
            if (s.equals(Animation.Status.PAUSED)) {
                st.pause();
                changeReplayButton();
            }
		});

		hbox.getChildren().addAll(list);
	}

	private void displayCode(String language, String algo) {
		String fileName = "./code/" + algo + language + ".txt";
		try {
			codeDisplay.setText(new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void displayHint(String algo) {
		String fileName = "./code/" + algo + "Hint.txt";
		try {
			hintDisplay.setText(new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8));
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
			inputString.setDisable(false);
			changePlayButton();
			selectAlgo = button.getAccessibleText();
			displayCode(languageSelect, selectAlgo);
			displayHint(selectAlgo);
		}));
	}

	private void sort(String selectAlgo) {
		inputString.setDisable(true);
		changePauseButton();
		st = new SequentialTransition();
		initializeRec();

		switch (selectAlgo) {
		case "bubble":
			st = BubbleSort(input, list, duration);
			break;
		case "insert":
			st = InsertionSort(input, list, duration);
			break;
		case "select":
			st = SelectionSort(input, list, duration);
			break;
		case "quick":
			st = QuickSort(input, st, duration);
			break;
		case "merge":
			st = MergeSort(input, st, duration);
			break;
		case "heap":
			st = HeapSort(input, list, duration);
			break;
		default:
			break;
		}

		st.currentTimeProperty().addListener(observable -> {
			updateProgress();
		});
		st.setRate(volumeSlider.getValue() / 40);
		st.setOnFinished(event -> {
			inputString.setDisable(false);
			changePlayButton();
		});
		st.play();
		timeSlider.setDisable(false);

		String str = inputString.getText();
		checkInput(str);
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

	@FXML
	private void handlePHP() {
		languageSelect = "PHP";
		displayCode(languageSelect, selectAlgo);
	}

	@FXML
	private void handleAscendingOrder() {
		sortOrder = 0;
		;
	}

	@FXML
	private void handleDescendingOrder() {
		sortOrder = 1;
	}

	private ParallelTransition changeColor(StackPane l1, StackPane l2, Color from, Color to) {
		Rectangle rec1 = (Rectangle) l1.getChildren().get(0);
		Rectangle rec2 = (Rectangle) l2.getChildren().get(0);

		FillTransition f1 = new FillTransition(Duration.millis(10), rec1, from, to);
		FillTransition f2 = new FillTransition(Duration.millis(10), rec2, from, to);

		ParallelTransition pl = new ParallelTransition();
		pl.getChildren().addAll(f1, f2);

		return pl;
	}

	// sort control part
	private ParallelTransition swap(StackPane l1, StackPane l2, ArrayList<StackPane> list, double speed) {
		TranslateTransition t1 = new TranslateTransition(Duration.millis(speed), l1);
		TranslateTransition t2 = new TranslateTransition(Duration.millis(speed), l2);

		ParallelTransition pl = new ParallelTransition();
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

		TranslateTransition t1 = new TranslateTransition(Duration.millis(speed), l1);
		TranslateTransition t2 = new TranslateTransition(Duration.millis(speed), l2);
		ParallelTransition pl = new ParallelTransition();
		t1.setByX(xLength * 30);
		t2.setByX((0 - xLength) * 30);
		pl.getChildren().addAll(t1, t2);
		Collections.swap(list, list.indexOf(l1), list.indexOf(l2));
		return pl;
	}

	private SequentialTransition BubbleSort(int arr[], ArrayList<StackPane> list, double duration) {
		SequentialTransition sq = new SequentialTransition();
		int temp;
		for (int i = 0; i < arr.length - 1; i++) {
			for (int j = 1; j < arr.length - i; j++) {
				if (sortOrder == 0) {
					if (arr[j] < arr[j - 1]) {
						temp = arr[j - 1];
						arr[j - 1] = arr[j];
						arr[j] = temp;
						// change color and move
						sq.getChildren().add(
								changeColor(list.get(j - 1), list.get(j), PreferenceController.color, color_change));
						sq.getChildren().add(swap(list.get(j - 1), list.get(j), list, duration));
						sq.getChildren().add(
								changeColor(list.get(j - 1), list.get(j), color_change, PreferenceController.color));
					}
				} else {
					if (arr[j] > arr[j - 1]) {
						temp = arr[j - 1];
						arr[j - 1] = arr[j];
						arr[j] = temp;
						// change color and move
						sq.getChildren().add(
								changeColor(list.get(j - 1), list.get(j), PreferenceController.color, color_change));
						sq.getChildren().add(swap(list.get(j - 1), list.get(j), list, duration));
						sq.getChildren().add(
								changeColor(list.get(j - 1), list.get(j), color_change, PreferenceController.color));
					}
				}
			}
		}
		return sq;
	}

	private SequentialTransition InsertionSort(int[] arr, ArrayList<StackPane> list, double duration) {
		SequentialTransition sq = new SequentialTransition();
		int temp;
		for (int i = 1; i < arr.length; i++) {
			for (int j = i; j > 0; j--) {
				if (sortOrder == 0) {
					if (arr[j] < arr[j - 1]) {
						temp = arr[j];
						arr[j] = arr[j - 1];
						arr[j - 1] = temp;
						sq.getChildren().add(changeColor(list.get(j - 1), list.get(j), PreferenceController.color, color_change));
						sq.getChildren().add(swapInsertion(list.get(j - 1), list.get(j), list, duration));
						sq.getChildren().add(swapInsertion1(list.get(j - 1), list.get(j), list, duration));
						sq.getChildren().add(swapInsertion2(list.get(j - 1), list.get(j), list, duration));
						sq.getChildren().add(changeColor(list.get(j - 1), list.get(j), color_change, PreferenceController.color));
					} else {
						break;
					}
				} else {
					if (arr[j] > arr[j - 1]) {
						temp = arr[j];
						arr[j] = arr[j - 1];
						arr[j - 1] = temp;
						sq.getChildren().add(
								changeColor(list.get(j - 1), list.get(j), PreferenceController.color, color_change));
						sq.getChildren().add(swapInsertion(list.get(j - 1), list.get(j), list, duration));
						sq.getChildren().add(swapInsertion1(list.get(j - 1), list.get(j), list, duration));
						sq.getChildren().add(swapInsertion2(list.get(j - 1), list.get(j), list, duration));
						sq.getChildren().add(
								changeColor(list.get(j - 1), list.get(j), color_change, PreferenceController.color));
					} else {
						break;
					}
				}
			}
		}
		return sq;
	}

	private SequentialTransition SelectionSort(int arr[], ArrayList<StackPane> list, double duration) {
		SequentialTransition sq = new SequentialTransition();
		int i, j, minIndex, tmp;
		int n = arr.length;
		for (i = 0; i < n - 1; i++) {
			if (sortOrder == 0) {
				minIndex = i;
				for (j = i + 1; j < n; j++)
					if (arr[j] < arr[minIndex])
						minIndex = j;
				if (minIndex != i) {
					tmp = arr[i];
					arr[i] = arr[minIndex];
					arr[minIndex] = tmp;
					sq.getChildren().add(changeColor(list.get(i), list.get(minIndex), PreferenceController.color, color_change));
					sq.getChildren().add(swapSelect1(list.get(i), list.get(minIndex), list, duration,minIndex-i));
					sq.getChildren().add(swapSelect2(list.get(i), list.get(minIndex), list, duration,minIndex-i));
					sq.getChildren().add(swapSelect3(list.get(i), list.get(minIndex), list, duration,minIndex-i));
					sq.getChildren().add(changeColor(list.get(i), list.get(minIndex), color_change, PreferenceController.color));
				}
			} else {
				minIndex = i;
				for (j = i + 1; j < n; j++)
					if (arr[j] > arr[minIndex])
						minIndex = j;
				if (minIndex != i) {
					tmp = arr[i];
					arr[i] = arr[minIndex];
					arr[minIndex] = tmp;
					sq.getChildren().add(changeColor(list.get(i), list.get(minIndex), PreferenceController.color, color_change));
					sq.getChildren().add(swapSelect1(list.get(i), list.get(minIndex), list, duration,minIndex-i));
					sq.getChildren().add(swapSelect2(list.get(i), list.get(minIndex), list, duration,minIndex-i));
					sq.getChildren().add(swapSelect3(list.get(i), list.get(minIndex), list, duration,minIndex-i));
					sq.getChildren().add(changeColor(list.get(i), list.get(minIndex), color_change, PreferenceController.color));
				}
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

	private ParallelTransition swapInsertion(StackPane l1, StackPane l2, ArrayList<StackPane> list, double speed) {
		TranslateTransition t1 = new TranslateTransition(Duration.millis(speed), l1);
		TranslateTransition t2 = new TranslateTransition(Duration.millis(speed), l2);
		t1.setDuration(Duration.millis(speed));
		t2.setDuration(Duration.millis(speed));
		ParallelTransition pl = new ParallelTransition();
		t1.setNode(l1);
		t2.setNode(l2);
		t2.setToY(-100);
		pl.getChildren().addAll(t1,t2);
		return pl;
	}

	private ParallelTransition swapInsertion1(StackPane l1, StackPane l2, ArrayList<StackPane> list, double speed) {
		TranslateTransition t1 = new TranslateTransition(Duration.millis(speed), l1);
		TranslateTransition t2 = new TranslateTransition(Duration.millis(speed), l2);
		t1.setDuration(Duration.millis(speed));
		t2.setDuration(Duration.millis(speed));
		ParallelTransition pl = new ParallelTransition();
		t1.setNode(l1);
		t2.setNode(l2);
		t1.setByX(30);
		t2.setByX(-30);
		pl.getChildren().addAll(t1,t2);
		return pl;
	}

	private ParallelTransition swapInsertion2(StackPane l1, StackPane l2, ArrayList<StackPane> list, double speed) {
		TranslateTransition t1 = new TranslateTransition(Duration.millis(speed), l1);
		TranslateTransition t2 = new TranslateTransition(Duration.millis(speed), l2);
		ParallelTransition pl = new ParallelTransition();
		t1.setNode(l1);
		t2.setNode(l2);
		t2.setToY(0);
		pl.getChildren().addAll(t1,t2);
		Collections.swap(list, list.indexOf(l1), list.indexOf(l2));
		return pl;
	}

	private ParallelTransition swapSelect1(StackPane l1, StackPane l2, ArrayList<StackPane> list, double speed, int distance) {
		TranslateTransition t2 = new TranslateTransition(Duration.millis(speed), l2);
		ParallelTransition pl = new ParallelTransition();
		t2.setToY(-100);
		pl.getChildren().addAll(t2);
		return pl;
	}

	private ParallelTransition swapSelect2(StackPane l1, StackPane l2, ArrayList<StackPane> list, double speed, int distance) {
		TranslateTransition t1 = new TranslateTransition(Duration.millis(speed), l1);
		TranslateTransition t2 = new TranslateTransition(Duration.millis(speed), l2);
		t1.setDuration(Duration.millis(speed));
		t2.setDuration(Duration.millis(speed));
		ParallelTransition pl = new ParallelTransition();
		t1.setNode(l1);
		t2.setNode(l2);
		int num = Math.abs(distance);
		num *= 30;
		t1.setByX(num);
		t2.setByX(-num);
		pl.getChildren().addAll(t1, t2);
		return pl;
	}

	private ParallelTransition swapSelect3(StackPane l1, StackPane l2, ArrayList<StackPane> list, double speed, int distance) {
		TranslateTransition t1 = new TranslateTransition(Duration.millis(speed), l1);
		TranslateTransition t2 = new TranslateTransition(Duration.millis(speed), l2);
		t1.setDuration(Duration.millis(speed));
		t2.setDuration(Duration.millis(speed));
		ParallelTransition pl = new ParallelTransition();
		t1.setNode(l1);
		t2.setNode(l2);
		t2.setToY(0);
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
		if (sortOrder == 0) {
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
					sq.getChildren().add(
							changeColor(list.get(parent), list.get(child), PreferenceController.color, color_change));
					sq.getChildren().add(swapHeap1(list.get(parent), list.get(child), list, duration, parent, child));
					sq.getChildren().add(
							changeColor(list.get(parent), list.get(child), color_change, PreferenceController.color));
					parent = child;
					child = 2 * child + 1;
				}
			}
			for (int i = arr.length - 1; i > 0; i--) {
				int temp = arr[i];
				arr[i] = arr[0];
				arr[0] = temp;
				sq.getChildren().add(changeColor(list.get(0), list.get(i), PreferenceController.color, color_change));
				sq.getChildren().add(swapHeap3(list.get(0), list.get(i), duration));
				sq.getChildren().add(swapHeap2(list.get(0), list.get(i), list, duration, 0, i));
				sq.getChildren().add(changeColor(list.get(0), list.get(i), color_change, PreferenceController.color));
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
					sq.getChildren().add(
							changeColor(list.get(parent), list.get(child), PreferenceController.color, color_change));
					sq.getChildren().add(swapHeap1(list.get(parent), list.get(child), list, duration, parent, child));
					sq.getChildren().add(
							changeColor(list.get(parent), list.get(child), color_change, PreferenceController.color));
					parent = child;
					child = 2 * child + 1;
				}
			}
		} else {
			for (int i = arr.length / 2; i >= 0; i--) {
				int parent = i;
				int temp = arr[parent];
				int child = 2 * parent + 1;
				while (child < arr.length) {
					if (child + 1 < arr.length && arr[child] > arr[child + 1]) {
						child++;
					}
					if (temp <= arr[child])
						break;
					temp = arr[child];
					arr[child] = arr[parent];
					arr[parent] = temp;
					temp = arr[child];
					sq.getChildren().add(
							changeColor(list.get(parent), list.get(child), PreferenceController.color, color_change));
					sq.getChildren().add(swapHeap1(list.get(parent), list.get(child), list, duration, parent, child));
					sq.getChildren().add(
							changeColor(list.get(parent), list.get(child), color_change, PreferenceController.color));
					parent = child;
					child = 2 * child + 1;
				}
			}
			for (int i = arr.length - 1; i > 0; i--) {
				int temp = arr[i];
				arr[i] = arr[0];
				arr[0] = temp;
				sq.getChildren().add(changeColor(list.get(0), list.get(i), PreferenceController.color, color_change));
				sq.getChildren().add(swapHeap3(list.get(0), list.get(i), duration));
				sq.getChildren().add(swapHeap2(list.get(0), list.get(i), list, duration, 0, i));
				sq.getChildren().add(changeColor(list.get(0), list.get(i), color_change, PreferenceController.color));
				int parent = 0;
				int tempFather = arr[parent];
				int child = 2 * parent + 1;
				while (child < i) {
					if (child + 1 < i && arr[child] > arr[child + 1]) {
						child++;
					}
					if (tempFather <= arr[child])
						break;
					tempFather = arr[child];
					arr[child] = arr[parent];
					arr[parent] = tempFather;
					tempFather = arr[child];
					sq.getChildren().add(
							changeColor(list.get(parent), list.get(child), PreferenceController.color, color_change));
					sq.getChildren().add(swapHeap1(list.get(parent), list.get(child), list, duration, parent, child));
					sq.getChildren().add(
							changeColor(list.get(parent), list.get(child), color_change, PreferenceController.color));
					parent = child;
					child = 2 * child + 1;
				}
			}
		}
		return sq;
	}

	private ArrayList<Animation> quickSortRec(int arr[], int start, int end, ArrayList<Animation> animationList,
			double duration) {
		if (start >= end)
			return animationList;
		int mid = arr[end];
		animationList.add(changeColor(list.get(end), list.get(end), PreferenceController.color, Color.DARKCYAN));
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
			animationList.add(changeColor(list.get(left), list.get(right), PreferenceController.color, color_change));
			animationList.add(swap(list.get(left), list.get(right), left - right, list, duration));
			animationList.add(changeColor(list.get(left), list.get(right), color_change, PreferenceController.color));
		}
		if (arr[left] >= arr[end]) {
			int temp = arr[left];
			arr[left] = arr[end];
			arr[end] = temp;
			// System.out.println("SWAP " + list.get(left) + " AND " +
			// list.get(end));
			animationList.add(changeColor(list.get(left), list.get(end), PreferenceController.color, color_change));
			animationList.add(swap(list.get(left), list.get(end), left - end, list, duration));
			animationList.add(changeColor(list.get(left), list.get(end), color_change, PreferenceController.color));
		} else {
			left++;
		}
		animationList.add(changeColor(list.get(end), list.get(end), Color.DARKCYAN, PreferenceController.color));
		quickSortRec(arr, start, left - 1, animationList, duration);
		quickSortRec(arr, left + 1, end, animationList, duration);
		return animationList;
	}

	private SequentialTransition QuickSort(int arr[], SequentialTransition sq, double duration) {
		ArrayList<Animation> animationList = new ArrayList<>();
		sq.getChildren().addAll(quickSortRec(arr, 0, arr.length - 1, animationList, duration));
		return sq;
	}

	private TranslateTransition moveUp(StackPane l1, double speed) {
		TranslateTransition t1 = new TranslateTransition(Duration.millis(speed), l1);
		t1.setByY(-40);
		return t1;
	}

	private TranslateTransition moveDown(StackPane l1, double speed, int distance) {
		distance *= 30;
		TranslateTransition t1 = new TranslateTransition(Duration.millis(speed), l1);
		t1.setByY(40);
		t1.setByX(distance);
		return t1;
	}

	private ArrayList<Animation> mergeSortRec(int arr[], int left, int right, ArrayList<Animation> animationList,
			double duration) {

		if (left >= right) {
			return animationList;
		}
		int center = (left + right) / 2;
		mergeSortRec(arr, left, center, animationList, duration);
		mergeSortRec(arr, center + 1, right, animationList, duration);
		animationList = merge(arr, animationList, left, center, right, duration);
		return animationList;
	}

	private ArrayList<Animation> merge(int[] arr, ArrayList<Animation> animationList, int left, int center, int right,
			double duration) {
		int[] tempArray = new int[arr.length];
		ArrayList<StackPane> tempList = new ArrayList<>(list);
		int mid = center + 1;
		int third = left;
		int temp = left;

		for (int i = left; i <= right; i++) {
			animationList.add(moveUp(list.get(i), 250));
		}

		while (left <= center && mid <= right) {
			if (arr[left] <= arr[mid]) {
				animationList.add(moveDown(list.get(left), duration, third - left));
				tempList.set(third, list.get(left));
				tempArray[third++] = arr[left++];
			} else {
				animationList.add(moveDown(list.get(mid), duration, third - mid));
				tempList.set(third, list.get(mid));
				tempArray[third++] = arr[mid++];
			}
		}

		while (mid <= right) {
			animationList.add(moveDown(list.get(mid), duration, third - mid));
			tempList.set(third, list.get(mid));
			tempArray[third++] = arr[mid++];
		}
		while (left <= center) {
			animationList.add(moveDown(list.get(left), duration, third - left));
			tempList.set(third, list.get(left));
			tempArray[third++] = arr[left++];
		}

		while (temp <= right) {
			list.set(temp, tempList.get(temp));
			arr[temp] = tempArray[temp++];
		}

		return animationList;
	}

	private SequentialTransition MergeSort(int arr[], SequentialTransition sq, double duration) {
		ArrayList<Animation> animationList = new ArrayList<>();
		animationList = mergeSortRec(arr, 0, arr.length - 1, animationList, duration);
		sq.getChildren().addAll(animationList);
		return sq;
	}

	/** Compare Part **/
	public void chooseFirstAlgo() {
		compareAlgo1 = combo1.getSelectionModel().getSelectedItem().getAccessibleText();
		label_left.setText(combo1.getSelectionModel().getSelectedItem().getText());
	}

	public void chooseSecAlgo() {
		compareAlgo2 = combo2.getSelectionModel().getSelectedItem().getAccessibleText();
		label_right.setText(combo2.getSelectionModel().getSelectedItem().getText());
	}

	@FXML
	public void startCompare() {
		clear_c();
		if (compareAlgo1 == null || compareAlgo2 == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText(null);
			alert.setContentText("Please choose the algorithms to compare!");
			alert.showAndWait();
		} else {
			if (!inputString_c.getText().isEmpty()) {
				input_c = getInput();
				if (input_c == null) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning Dialog");
					alert.setHeaderText(null);
					alert.setContentText("Please input with a right format!");
					alert.showAndWait();
				} else {
					int[] copyof = Arrays.copyOf(input_c, input_c.length);
					generate_c(list_l, hbox_left, input_c);
					generate_c(list_r, hbox_right, input_c);
					ArrayList<Animation> l1 = new ArrayList<Animation>();
					ArrayList<Animation> l2 = new ArrayList<Animation>();

					l1 = sort_c(compareAlgo1, list_l, duration_c, input_c);
					showTime(getTime(), time_left);

					l2 = sort_c(compareAlgo2, list_r, duration_c, copyof);
					showTime(getTime(), time_right);
					SequentialTransition sq = new SequentialTransition();
					sq = playTwoAnimate(l1, l2);
					sq.play();

					complexityTable(compareAlgo1, compareAlgo2);
				}
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning Dialog");
				alert.setHeaderText(null);
				alert.setContentText("Please input first!");
				alert.showAndWait();
			}
		}
	}

	private SequentialTransition playTwoAnimate(ArrayList<Animation> sq1, ArrayList<Animation> sq2) {
		ArrayList<Animation> animationList = new ArrayList<>();
		SequentialTransition sq = new SequentialTransition();

		int size = sq1.size() <= sq2.size() ? sq1.size() : sq2.size();
		for (int i = 0; i < size; i++) {
			animationList.add(sq1.get(i));
			animationList.add(sq2.get(i));
		}

		if (size == sq1.size()) {
			for (int m = size; m < sq2.size(); m++) {
				animationList.add(sq2.get(m));
			}
		} else {
			for (int m = size; m < sq1.size(); m++) {
				animationList.add(sq1.get(m));
			}
		}
		sq.getChildren().addAll(animationList);
		return sq;
	}

	private void showTime(long time, Label label) {
		label.setText("time: " + time + "millis");
	}

	private ArrayList<Animation> sort_c(String compareAlgo, ArrayList<StackPane> list, double duration, int[] input) {
		ArrayList<Animation> sq_c = new ArrayList<>();

		switch (compareAlgo) {
		case "bubble":
			sq_c = BubbleSort_c(input, list, duration, dist);
			break;
		case "insertion":
			sq_c = InsertionSort_c(input, list, duration, dist);
			break;
		case "select":
			sq_c = SelectionSort_c(input, list, duration, dist);
			break;
		case "quick":
			sq_c = QuickSort_c(input, list, duration, dist);
			break;
		case "merge":
			sq_c = MergeSort_c(input, list, duration, dist);
			break;
		case "heap":
			sq_c = HeapSort_c(input, list, duration, dist);
			break;
		default:
			break;
		}
		return sq_c;
	}

	private void clear_c() {
		list_l.clear();
		list_r.clear();

		if (!hbox_left.getChildren().isEmpty())
			hbox_left.getChildren().clear();
		if (!hbox_right.getChildren().isEmpty())
			hbox_right.getChildren().clear();
	}

	private int[] getInput() {
		int[] input_c = null;
		int randomNum;

		String str = inputString_c.getText();

		int[] in = new int[1];
		String[] sp = str.split("\\D+");
		in = Stream.of(sp).mapToInt(Integer::parseInt).toArray();
		if (in.length == 1) {
			input_c = new int[in[0]];

			for (int i = 0; i < input_c.length; i++) {
				randomNum = ThreadLocalRandom.current().nextInt(1, in[0]);
				input_c[i] = randomNum;
			}
		}

		return input_c;
	}

	private void generate_c(ArrayList<StackPane> list, HBox hbox, int[] input) {
		Color shapeColor = Color.BLACK;
		double height;

		int max = input[0];
		for (int i : input) {
			if (i >= max)
				max = i;
		}

		dist = hbox.getWidth() / input.length;
		BigDecimal bg = new BigDecimal(dist).setScale(5, RoundingMode.UP);
		dist = bg.doubleValue();

		for (int i = 0; i < input.length; i++) {
			height = (hbox.getWidth() / max) * input[i];
			Rectangle rectangle = new Rectangle(dist, height);
			rectangle.setFill(shapeColor);
			StackPane stackPane = new StackPane();
			stackPane.setPrefSize(rectangle.getWidth(), rectangle.getHeight());
			stackPane.setId(String.valueOf(input[i]));
			stackPane.getChildren().add(rectangle);
			stackPane.setAlignment(Pos.BOTTOM_CENTER);
			list.add(stackPane);
		}

		hbox.setSpacing(0);
		hbox.getChildren().addAll(list);
	}

	private void complexityTable(String a1, String a2) {
		switch (a1) {
		case "bubble":
			algo1.setText("Bubble Sort");
			c1.setText("O(n^2)");
			break;
		case "insertion":
			algo1.setText("Insertion Sort");
			c1.setText("O(n^2)");
			break;
		case "select":
			algo1.setText("Selection Sort");
			c1.setText("O(n^2)");
			break;
		case "quick":
			algo1.setText("Quick Sort");
			c1.setText("O(nlogn)");
			break;
		case "merge":
			algo1.setText("Merge Sort");
			c1.setText("O(nlogn)");
			break;
		case "heap":
			algo1.setText("Heap Sort");
			c1.setText("O(nlogn)");
			break;
		default:
			break;
		}

		switch (a2) {
		case "bubble":
			algo2.setText("Bubble Sort");
			c2.setText("O(n^2)");
			break;
		case "insertion":
			algo2.setText("Insertion Sort");
			c2.setText("O(n^2)");
			break;
		case "select":
			algo2.setText("Selection Sort");
			c2.setText("O(n^2)");
			break;
		case "quick":
			algo2.setText("Quick Sort");
			c2.setText("O(nlogn)");
			break;
		case "merge":
			algo2.setText("Merge Sort");
			c2.setText("O(nlogn)");
			break;
		case "heap":
			algo2.setText("Heap Sort");
			c2.setText("O(nlogn)");
			break;
		default:
			break;
		}
	}

	/**
	 * Gets rectangle.
	 *
	 * @return the rectangle
	 */
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

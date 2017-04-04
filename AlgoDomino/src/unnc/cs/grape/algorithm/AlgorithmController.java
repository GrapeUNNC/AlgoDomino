package unnc.cs.grape.algorithm;

import java.util.*;
import java.util.function.Consumer;

import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class AlgorithmController {
    /**
     * Function working for bubble and insertion sort
     *
     * @param l1
     * @param l2
     * @param list
     * @param speed
     * @return
     */
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
        t1.setByX(xLength*30);
        t2.setByX((0-xLength)*30);
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
    protected SequentialTransition BubbleSort(int arr[], ArrayList<StackPane> list, double duration) {
        SequentialTransition sq = new SequentialTransition();
        int temp;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 1; j < arr.length - i; j++) {
                if (arr[j] > arr[j - 1]) {
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
                if (arr[j] > arr[j - 1]) {
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
                if (arr[j] > arr[minIndex])
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

    private ParallelTransition swapHeap1(StackPane l1, StackPane l2, ArrayList<StackPane> list, double speed, int parent, int child) {
        TranslateTransition t1 = new TranslateTransition();
        TranslateTransition t2 = new TranslateTransition();
        t1.setDuration(Duration.millis(speed));
        t2.setDuration(Duration.millis(speed));
        ParallelTransition pl = new ParallelTransition();
        t1.setNode(l1);
        t2.setNode(l2);
        int num=child-parent;
        num *= 30;
        t1.setByX(num);
        t2.setByX(-num);
        pl.getChildren().addAll(t1, t2);
        Collections.swap(list, list.indexOf(l1), list.indexOf(l2));
        return pl;
    }

    private ParallelTransition swapHeap2(StackPane l1, StackPane l2, ArrayList<StackPane> list, double speed, int parent, int child) {
        TranslateTransition t1 = new TranslateTransition();
        TranslateTransition t2 = new TranslateTransition();
        t1.setDuration(Duration.millis(speed));
        t2.setDuration(Duration.millis(speed));
        ParallelTransition pl = new ParallelTransition();
        t1.setNode(l1);
        t2.setNode(l2);
        int num=child-parent;
        num *= 30;
        t1.setByX(num);
        t1.setToY(0);
        t2.setByX(-num);
        t2.setToY(0);
        pl.getChildren().addAll(t1, t2);
        Collections.swap(list, list.indexOf(l1), list.indexOf(l2));
        return pl;
    }

    private ParallelTransition swapHeap3(StackPane l1, StackPane l2, ArrayList<StackPane> list, double speed) {
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

    protected SequentialTransition HeapSort (int arr[], ArrayList<StackPane> list, double duration) {
    	SequentialTransition sq = new SequentialTransition();
    	for (int i = arr.length / 2; i >= 0; i--)
        {
    		int parent=i;
            int temp = arr[parent];
		    int child = 2 * parent + 1;
		    while (child < arr.length)
		    {
		    	if (child + 1 < arr.length && arr[child] < arr[child + 1]) {
		    		child++;
			    }
		        if (temp >= arr[child])
		        	break;
		         temp=arr[child];
		         arr[child]=arr[parent];
			     arr[parent] =temp;
			     temp=arr[child];
			     sq.getChildren().add(swapHeap1(list.get(parent), list.get(child), list, duration, parent, child));
			     parent = child;
			     child = 2 * child + 1;
		    }
        }

        for (int i = arr.length - 1; i > 0; i--)
        {
            int temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;
            sq.getChildren().add(swapHeap3(list.get(0), list.get(i), list, duration));
            sq.getChildren().add(swapHeap2(list.get(0), list.get(i), list, duration, 0, i));
            int parent=0;
            int tempFather = arr[parent];
		    int child = 2 * parent + 1;
		    while (child < i)
		    {
		    	if (child + 1 < i && arr[child] < arr[child + 1]) {
		    		child++;
			    }
		        if (tempFather >= arr[child])
		        	break;
		         tempFather=arr[child];
		         arr[child]=arr[parent];
			     arr[parent] =tempFather;
			     tempFather=arr[child];
			     sq.getChildren().add(swapHeap1(list.get(parent), list.get(child), list, duration, parent, child));
			     parent = child;
			     child = 2 * child + 1;
		    }
        }
    	return sq;
    }

    private Set<Animation> quickSortRec(int arr[],int start, int end, ArrayList<StackPane> list, Set<Animation> animationsSet, double duration) {
        if (start >= end)
            return animationsSet;
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
            System.out.println("SWAP " + list.get(left) + " AND " + list.get(right));
            animationsSet.add(swap(list.get(left), list.get(right), left-right, list, duration*2));
        }
        if (arr[left] >= arr[end]) {
            int temp = arr[left];
            arr[left] = arr[end];
            arr[end] = temp;
            System.out.println("SWAP " + list.get(left) + " AND " + list.get(end));
            animationsSet.add(swap(list.get(left), list.get(end), left-end, list, duration*2));
        }
        else
            left++;

        animationsSet.addAll(quickSortRec(arr, start, left-1, list, animationsSet, duration));
        animationsSet.addAll(quickSortRec(arr, left+1, end, list, animationsSet, duration));
        return animationsSet;
    }

    protected SequentialTransition quickSort(int arr[], ArrayList<StackPane> list, SequentialTransition sq, double duration) {
        Set<Animation> animationSet = new HashSet<>();
        animationSet = quickSortRec(arr, 0, arr.length-1, list, animationSet, duration);
        sq.getChildren().addAll(animationSet);
        return sq;
    }



    protected void MergeSort() {
        // merge sort algorithm
    }
}

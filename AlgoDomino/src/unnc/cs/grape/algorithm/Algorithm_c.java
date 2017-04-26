package unnc.cs.grape.algorithm;

import java.util.ArrayList;
import java.util.Collections;

import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import unnc.cs.grape.controller.PreferenceController;

public class Algorithm_c {
	
	private ParallelTransition swap(StackPane l1, StackPane l2, int xLength, ArrayList<StackPane> list, double speed, double dist) {
		if (xLength < 0) {
			xLength = 0 - xLength;
		}

		TranslateTransition t1 = new TranslateTransition(Duration.millis(speed), l1);
		TranslateTransition t2 = new TranslateTransition(Duration.millis(speed), l2);
		ParallelTransition pl = new ParallelTransition();
		t1.setByX(xLength * dist);
		t2.setByX((0 - xLength) * dist);
		pl.getChildren().addAll(t1, t2);
		Collections.swap(list, list.indexOf(l1), list.indexOf(l2));
		return pl;
	}
	
	// sort control part
	private ParallelTransition swapMe(StackPane l1, StackPane l2, ArrayList<StackPane> list, double speed, double dist) {
		TranslateTransition t1 = new TranslateTransition();
		TranslateTransition t2 = new TranslateTransition();
		t1.setDuration(Duration.millis(speed));
		t2.setDuration(Duration.millis(speed));
		ParallelTransition pl = new ParallelTransition();
		t1.setNode(l1);
		t2.setNode(l2);
		t1.setByX(dist);
		t2.setByX(-dist);
		pl.getChildren().addAll(t1, t2);
		Collections.swap(list, list.indexOf(l1), list.indexOf(l2));
		return pl;
	}

	private ParallelTransition swapSelect(StackPane l1, StackPane l2, ArrayList<StackPane> list, double speed, double dist) {
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
		num *= dist;
		t1.setByX(num);
		t2.setByX(-num);
		Collections.swap(list, list.indexOf(l1), list.indexOf(l2));
		pl.getChildren().addAll(t1, t2);
		return pl;
	}

	public SequentialTransition BubbleSort_c(int[] arr, ArrayList<StackPane> list, double duration, double dist) {
        SequentialTransition sq = new SequentialTransition();
        int temp;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 1; j < arr.length - i; j++) {
                if (arr[j] < arr[j - 1]) {
                    temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                    // change color and move
                    sq.getChildren().add(swapMe(list.get(j - 1), list.get(j), list, duration, dist));
                }
            }
        }
        return sq;
    }

	public SequentialTransition InsertionSort_c(int[] arr, ArrayList<StackPane> list, double duration, double dist) {
		SequentialTransition sq = new SequentialTransition();
		int temp;
		for (int i = 1; i < arr.length; i++) {
			for (int j = i; j > 0; j--) {
				if (arr[j] < arr[j - 1]) {
					temp = arr[j];
					arr[j] = arr[j - 1];
					arr[j - 1] = temp;
					sq.getChildren().add(swapMe(list.get(j - 1), list.get(j), list, duration, dist));
				} else {
					break;
				}
			}
		}
		return sq;
	}

	public SequentialTransition SelectionSort_c(int[] arr, ArrayList<StackPane> list, double duration, double dist) {
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
                sq.getChildren().add(swapSelect(list.get(i), list.get(minIndex), list, duration, dist));
            }
        }
        return sq;
    }
	
	 private ParallelTransition swapHeap1(StackPane l1, StackPane l2, ArrayList<StackPane> list, double speed, int parent, int child, double dist) {
	        TranslateTransition t1 = new TranslateTransition();
	        TranslateTransition t2 = new TranslateTransition();
	        t1.setDuration(Duration.millis(speed));
	        t2.setDuration(Duration.millis(speed));
	        ParallelTransition pl = new ParallelTransition();
	        t1.setNode(l1);
	        t2.setNode(l2);
	        int num=child-parent;
	        num *= dist;
	        t1.setByX(num);
	        t2.setByX(-num);
	        pl.getChildren().addAll(t1, t2);
	        Collections.swap(list, list.indexOf(l1), list.indexOf(l2));
	        return pl;
	    }

	    protected SequentialTransition HeapSort_c (int arr[], ArrayList<StackPane> list, double duration, double dist) {
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
				     sq.getChildren().add(swapHeap1(list.get(parent), list.get(child), list, duration, parent, child, dist));
				     parent = child;
				     child = 2 * child + 1;
			    }
	        }
	        for (int i = arr.length - 1; i > 0; i--)
	        {
	            int temp = arr[i];
	            arr[i] = arr[0];
	            arr[0] = temp;
	            sq.getChildren().add(swapHeap1(list.get(0), list.get(i), list, duration, 0, i, dist));
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
				     sq.getChildren().add(swapHeap1(list.get(parent), list.get(child), list, duration, parent, child, dist));
				     parent = child;
				     child = 2 * child + 1;
			    }
	        }
	    	return sq;
	    }
	    
	    
	    private ArrayList<Animation> quickSortRec(int arr[], int start, int end, ArrayList<StackPane> list, ArrayList<Animation> animationList,
				double duration, double dist) {
			if (start >= end)
				return animationList;
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
				
				animationList.add(swap(list.get(left), list.get(right), left - right, list, duration, dist));
			}
			if (arr[left] >= arr[end]) {
				int temp = arr[left];
				arr[left] = arr[end];
				arr[end] = temp;
				
				animationList.add(swap(list.get(left), list.get(end), left - end, list, duration, dist));
			} else
				left++;

			quickSortRec(arr, start, left - 1, list, animationList, duration, dist);
			quickSortRec(arr, left + 1, end, list, animationList, duration, dist);
			return animationList;
		}

		protected SequentialTransition QuickSort_c(int arr[], ArrayList<StackPane> list,  double duration, double dist) {
			SequentialTransition sq = new SequentialTransition();
			ArrayList<Animation> animationList = new ArrayList<>();
			sq.getChildren().addAll(quickSortRec(arr, 0, arr.length - 1, list,animationList, duration, dist));
			return sq;
		}
}

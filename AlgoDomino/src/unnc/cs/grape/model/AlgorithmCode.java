package unnc.cs.grape.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AlgorithmCode {
	//public static String javaBubble=readTxtFile("src/unnc/cs/grape/model/bubbleJava.txt");

	//public static String javaInsertion="test2";//readTxtFile("src/unnc/cs/grape/model/insertJava.txt").toString();

	//public static String javaSelection="test3";//readTxtFile("src/unnc/cs/grape/model/selectJava.txt").toString();

	//public static String javaQuick="test4";//readTxtFile("src/unnc/cs/grape/model/quickJava.txt").toString();

	public static String cQuick="test5";//readTxtFile("src/unnc/cs/grape/model/quickC.txt").toString();

	public static String cBubble="test6";//readTxtFile("src/unnc/cs/grape/model/bubbleC.txt").toString();

	public static String cInsertion="test7";//readTxtFile("src/unnc/cs/grape/model/insertC.txt").toString();

	public static String cSelection="test8";//readTxtFile("src/unnc/cs/grape/model/selectC.txt").toString();

	public static String cMerge="test9";//readTxtFile("src/unnc/cs/grape/model/mergeC.txt").toString();

	public static String cHeap="test10";//readTxtFile("src/unnc/cs/grape/model/heapC.txt").toString();

	public static String pythonHeap="test11";//readTxtFile("src/unnc/cs/grape/model/heapPython.txt").toString();

	public static String pythonBubble="test12";//readTxtFile("src/unnc/cs/grape/model/bubblePython.txt").toString();

	public static String pythonInsertion="test13";//readTxtFile("src/unnc/cs/grape/model/insertPython.txt").toString();

	public static String pythonSelection="test14";//readTxtFile("src/unnc/cs/grape/model/selectPython.txt").toString();

	public static String pythonQuick="test15";//readTxtFile("src/unnc/cs/grape/model/quickPython.txt").toString();

	public static String pythonMerge="test16";//readTxtFile("src/unnc/cs/grape/model/mergePython.txt").toString();

	public static String javaMerge="public void mergeSort(int[] array, int low, int mid, int high) {\n"
			+ "    int i = low;\n    int j = mid + 1;\n    int j = mid + 1;\n    int[] array2 = new int[high - low + 1];\n"
			+ "    while (i <= mid && j <= high)\n"
			+ "    {\n"
			+ "        if (array[i] <= array[j])\n"
    		+ "        {\n"
    		+ "            array2[k] = array[i];\n"
    		+ "            i++;\n"
    		+ "            k++;\n"
    		+ "        }\n"
    		+ "        else\n"
    		+ "        {\n"
    		+ "            array2[k] = array[j];\n"
    		+ "            j++;\n"
    		+ "            k++;\n"
    		+ "        }\n    }\n"
    		+ "    while (i <= mid)\n"
    		+ "    {\n"
    		+ "         array2[k] = array[i];\n"
    		+ "         i++;\n"
    		+ "         k++;\n"
    		+ "    }\n"
    		+ "    while (j <= high)\n"
    		+ "    {\n"
    		+ "         array2[k] = array[j];\n"
    		+ "         j++;\n"
    		+ "         k++;\n"
    		+ "    }\n"
    		+ "    for (k = 0, i = low; i <= high; i++, k++)\n"
    		+ "    {\n"
    		+ "         array[i] = array2[k];\n"
    		+ "    }\n}";

	public static String javaHeap="public void HeapAdjust(int[] array, int parent, int length) {\n"
			+ "    int temp = array[parent];\n"
			+ "    int child = 2 * parent + 1;\n"
			+ "    while (child < length)\n"
    		+ "    {\n"
    		+ "        if (child + 1 < length && array[child] < array[child + 1])\n"
    		+ "        {\n"
    		+ "             child++;\n"
    		+ "        }\n"
    		+ "        if (temp >= array[child])\n"
    		+ "            break;\n"
    		+ "        array[parent] = array[child];\n"
    		+ "        parent = child;\n"
    		+ "        child = 2 * child + 1;\n    }\n"
    		+ "    array[parent] = temp;\n}\n\n"
    		+ "public void heapSort(int[] list) {\n"
    		+ "    for (int i = list.length / 2; i >= 0; i--)\n"
    		+ "    {\n"
    		+ "        HeapAdjust(list, i, list.length);\n"
    		+ "    }\n"
    		+ "    for (int i = list.length - 1; i > 0; i--)\n"
    		+ "    {\n"
    		+ "        int temp = list[i];\n"
    		+ "        list[i] = list[0];\n"
    		+ "        list[0] = temp;\n"
    		+ "        HeapAdjust(list, 0, i);\n"
    		+ "    }\n}\n";

	public static String hintBubble = "do\n"
			+ "    swapped = false\n"
			+ "    for i = 1 to indexOfLastUnsortedElement\n"
			+ "        if leftElement > rightElement\n"
			+ "            swap(leftElement, rightElement)\n"
			+ "            swapped = true\n"
			+ "while swapped";

	public static String hintInsertion = "mark first element as sorted\n"
			+ "for each unsorted element\n"
			+ "    'extract' the element\n"
			+ "    for i = lastSortedIndex to 0\n"
			+ "        if currentSortedElement > extractedElement\n"
			+ "            move sorted element to the right by 1\n"
			+ "        else: insert extracted element";

	public static String hintSelection = "repeat (numOfElements - 1) times\n"
			+ "    set the first unsorted element as the minimum\n"
			+ "    for each of the unsorted elements\n"
			+ "        if element < currentMinimum\n"
			+ "            set element as new minimum\n"
			+ "    swap minimum with first unsorted position";

	public static String hintQuick = "for each (unsorted) partition\n"
			+ "    set first element as pivot\n"
			+ "    storeIndex = pivotIndex + 1\n"
			+ "    for i = pivotIndex + 1 to rightmostIndex\n"
			+ "        if element[i] < element[pivot]\n"
			+ "            swap(i, storeIndex); storeIndex++\n"
			+ "    swap(pivot, storeIndex - 1)";

	public static String hintMerge = "split each element into partitions of size 1\n"
			+ "recursively merge adjancent partitions\n"
			+ "    for i = leftPartStartIndex to rightPartLastIndex inclusive\n"
			+ "        if leftPartHeadValue <= rightPartHeadValue\n"
			+ "            copy leftPartHeadValue\n"
			+ "        else: copy rightPartHeadValue\n"
			+ "copy elements back to original array";

	public static String hintHeap = "repeat (numOfElements/2) times\n"
			+ "    build big root heap\n"
			+ "repeat (numOfElements - 1) times\n"
			+ "    swap root element with current index element\n"
			+ "    adjust current heap to big root heap";

	public static String javaScriptBubble="function bubbleSort(arr) {\n"
			+ "    var len = arr.length;\n"
			+ "    for (var i = 0; i < len; i++)\n"
    		+ "    {\n"
    		+ "        for(var j = 0; j < len - i -1; j++)\n"
    		+ "        {\n            if(arr[j]>arr[j+1])\n"
    		+ "            {\n"
    		+ "                var temp = arr[j+1];\n                arr[j+1] = arr[j];\n                arr[j] = temp;\n"
    		+ "            }\n        }\n    }\n    return arr;\n}";

	public static String javaScriptInsertion="function insertionSort(arr) {\n"
			+ "    for (var i = 1; i < arr.length; i++)\n"
    		+ "    {\n"
    		+ "        var temp = arr[i];\n        var j = i - 1;\n"
    		+ "        while (j >= 0 && arr[j] > temp)\n"
    		+ "        {\n            arr[j + 1] = arr[j];\n            j--;\n        }\n"
    		+ "        arr[j + 1] = temp;\n"
    		+ "    }\n    return arr;\n}";

	public static String javaScriptSelection="function selectionSort(arr) {\n"
			+ "    var len = arr.length;\n    var index,temp;\n"
			+ "    for(var i = 0; i < len-1 ;i++)\n"
    		+ "    {\n"
    		+ "        index = i;\n"
    		+ "        for(var j = i + 1 ; j<len; j++)\n"
    		+ "        {\n            if(arr[j] < arr[index])\n"
    		+ "            {\n"
    		+ "                index = j;\n"
    		+ "            }\n        }\n"
    		+ "        temp = arr[i];\n        arr[i] = arr[index];\n        arr[index] = temp;\n"
    		+ "    }\n    return arr;\n}";

	public static String javaScriptQuick="var quickSort = function(arr) {\n"
			+ "    if (arr.length <= 1)\n"
    		+ "    {\n"
    		+ "        return arr;\n"
    		+ "    }\n"
    		+ "    var pivotIndex = Math.floor(arr.length / 2);\n"
    		+ "    var pivot = arr.splice(pivotIndex, 1)[0];\n"
    		+ "    var left = [];\n"
    		+ "    var right = [];\n"
    		+ "    for (var i = 0; i < arr.length; i++)\n"
    		+ "    {\n"
    		+ "        if (arr[i] < pivot)\n"
    		+ "             left.push(arr[i]);\n"
    		+ "        else\n"
    		+ "             right.push(arr[i]);\n"
    		+ "    }\n"
    		+ "    return quickSort(left).concat([pivot], quickSort(right));\n}";


	public static String javaScriptMerge="function merge(left, right) {\n"
			+ "    var result = [];\n"
			+ "    while(left.length > 0 && right.length > 0)\n"
    		+ "    {\n"
    		+ "        if(left[0] < right[0])\n"
    		+ "            result.push(left.shift());\n"
    		+ "        else\n"
    		+ "            result.push(right.shift());\n"
    		+ "    }\n"
    		+ "    return result.concat(left).concat(right);\n}\n\n"
    		+ "function mergeSort(arr) {\n"
    		+ "    if(arr.length==1)\n"
    		+ "        return arr;\n"
    		+ "    var mid=Math.floor(arr.length/2);\n"
    		+ "    var left_arr=arr.slice(0,mid);\n"
    		+ "    var right_arr=arr.slice(mid);\n"
    		+ "    return merge(mergeSort(left_arr),mergeSort(right_arr));\n}";

	public static String javaScriptHeap="function heapSort(arr) {\n"
			+ "    function maxHeapify(arr, index, heapSize)\n"
			+ "    {\n"
			+ "        var iMax,iLeft,iRight;\n"
			+ "        while(true)\n"
			+ "        {\n"
			+ "            iMax = index;\n"
			+ "            iLeft = 2 * index + 1;\n"
			+ "            iRight = 2 * (index + 1);\n"
    		+ "            if (iLeft < heapSize && arr[index] < arr[iLeft])\n"
    		+ "                iMax = iLeft;\n"
    		+ "            if (iRight < heapSize && arr[iMax] < arr[iRight])\n"
    		+ "                iMax = iRight;\n"
    		+ "            if (iMax != index)\n"
    		+ "            {\n"
    		+ "                swap(arr, iMax, index);\n"
    		+ "                index = iMax;\n"
    		+ "            }\n"
    		+ "            else\n"
    		+ "            {\n"
    		+ "                break;\n"
    		+ "            }\n"
    		+ "        }\n"
    		+ "    }\n"
    		+ "    function buildMaxHeap(arr)\n"
    		+ "    {\n"
    		+ "        var i;\n"
    		+ "        var iParent = Math.floor(arr.length / 2) - 1;\n"
    		+ "        for (i = iParent; i >= 0; i--)\n"
    		+ "            maxHeapify(arr, i, arr.length);\n"
    		+ "    }\n"
    		+ "    function swap(arr, i, j)\n"
    		+ "    {\n"
    		+ "        var temp = arr[i];\n"
    		+ "        arr[i] = arr[j];\n"
    		+ "        arr[j] = temp;\n"
    		+ "    }\n"
    		+ "    function sort(arr)\n"
    		+ "    {\n"
    		+ "        buildMaxHeap(arr);\n"
    		+ "        for (var i = arr.length - 1; i > 0; i--)\n"
    		+ "        {\n"
    		+ "            swap(arr, 0, i);\n"
    		+ "            maxHeapify(arr, 0, i);\n"
    		+ "        }\n"
    		+ "        return arr;\n"
    		+ "    }\n\n"
    		+ "    return sort(arr);\n}";

	 public static String readTxtFile(String filePath) {
		 StringBuffer strBuffer=null;
	        try {
	            File file = new File(filePath);
	            if (file.isFile() && file.exists()) {
	            	InputStreamReader inputReader = null;
	                BufferedReader bufferReader = null;
	                try
	                {
	                    InputStream inputStream = new FileInputStream(file);
	                    inputReader = new InputStreamReader(inputStream);
	                    bufferReader = new BufferedReader(inputReader);
	                    String line = null;
	                    strBuffer = new StringBuffer();

	                    while ((line = bufferReader.readLine()) != null)
	                    {
	                        strBuffer.append(line+'\n');
	                    }
	                }
	                catch (IOException e)
	                {
	                	System.out.println("sss");
	                }
	            } else {
	                System.out.println("ddd");
	            }
	        } catch (Exception e) {
	            System.out.println("qqq!");
	        }
	        return strBuffer.toString();

	    }


}

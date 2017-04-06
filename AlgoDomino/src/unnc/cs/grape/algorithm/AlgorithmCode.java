package unnc.cs.grape.algorithm;

public class AlgorithmCode {
	public static String javaBubble="public void bubbleSort(int[] list) {\n"
			+ "    for (int i = 0; i < list.length - 1; i++)\n"
    		+ "    {\n"
    		+ "        for (int j = list.length - 1; j > i; j--)\n"
    		+ "        {\n            if (list[j - 1] > list[j])\n"
    		+ "            {\n"
    		+ "                int temp = list[j - 1];\n                list[j - 1] = list[j];\n                list[j] = temp;\n"
    		+ "            }\n        }\n    }\n}";

	public static String javaInsertion="public void insertSort(int[] list) {\n"
			+ "    for (int i = 1; i < list.length; i++)\n"
    		+ "    {\n"
    		+ "        int j = 0;\n        int temp = list[i];\n"
    		+ "        for (j = i - 1; j >= 0 && temp < list[j]; j--)\n"
    		+ "        {\n            list[j + 1] = list[j];\n        }\n"
    		+ "        list[j + 1] = temp;\n"
    		+ "    }\n}";

	public static String javaSelection="public void selectionSort(int[] list) {\n"
			+ "    for (int i = 0; i < list.length - 1; i++)\n"
    		+ "    {\n"
    		+ "        int temp = 0;\n        int index = i;\n"
    		+ "        for (int j = i + 1; j < list.length; j++)\n"
    		+ "        {\n            if (list[index] > list[j])\n"
    		+ "            {\n"
    		+ "                index = j;\n"
    		+ "            }\n        }\n"
    		+ "        temp = list[index];\n        list[index] = list[i];\n        list[i] = temp;\n"
    		+ "    }\n}";

	public static String javaQuick="private void quickSort(int[] list, int left, int right) {\n"
			+ "    if (left < right)\n"
    		+ "    {\n"
    		+ "        int base = division(list, left, right);\n"
    		+ "        quickSort(list, left, base - 1);\n"
    		+ "        quickSort(list, base + 1, right);\n"
    		+ "    }\n}\n\n"
    		+ "public int division(int[] list, int left, int right) {\n"
    		+ "    int base = list[left];\n"
    		+ "    while (left < right)\n"
    		+ "    {\n"
    		+ "        while (left < right && list[right] >= base)\n"
    		+ "             right--;\n"
    		+ "        list[left] = list[right];\n"
    		+ "        while (left < right && list[left] <= base)\n"
    		+ "             left++;\n"
    		+ "        list[right] = list[left];\n    }\n"
    		+ "    list[left] = base;\n    return left;\n}";

	public static String javaMerge="public void Merge(int[] array, int low, int mid, int high) {\n"
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

	public static String hintHeap = "heap sort";

	public static String javaScriptBubble="Js bubble sort";
	public static String javaScriptInsertion="Js insertion sort";
	public static String javaScriptSelection="Js selection sort";
	public static String javaScriptQuick="Js quick sort";
	public static String javaScriptMerge="Js merge sort";
	public static String javaScriptHeap="Js heap sort";

}

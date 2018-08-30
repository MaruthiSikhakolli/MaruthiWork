package java_Programs;

public class SortingTechniques {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int arr[] = { 64, 34, 25, 12, 22, 11, 90 };
		System.out.println("Array before sorting");
		System.out.println(arr);

		bubbleSort(arr);
		recursiveBubbleSort(arr, arr.length);
		insertionSort(arr);
		insertionSortRecursive(arr, arr.length);
		selectionSort(arr);

		System.out.println("Array after sorting");
		printArray(arr);
	}

	static void selectionSort(int arr[]) {
		int n = arr.length;

		// One by one move boundary of unsorted subarray
		for (int i = 0; i < n - 1; i++) {
			// Find the minimum element in unsorted array
			int min_idx = i;
			for (int j = i + 1; j < n; j++)
				if (arr[j] < arr[min_idx])
					min_idx = j;

			// Swap the found minimum element with the first
			// element
			int temp = arr[min_idx];
			arr[min_idx] = arr[i];
			arr[i] = temp;
		}
	}

	static void bubbleSort(int arr[]) {
		int n = arr.length;
		for (int i = 0; i < n - 1; i++)
			for (int j = 0; j < n - i - 1; j++)
				if (arr[j] > arr[j + 1]) {
					int temp = arr[j];
					arr[j] = arr[j + 1];
					arr[j + 1] = temp;
				}
	}

	static void recursiveBubbleSort(int arr[], int n) {
		// Base case
		if (n == 1)
			return;

		// One pass of bubble sort. After this pass, the largest element is moved (or
		// bubbled) to end.
		for (int i = 0; i < n - 1; i++)
			if (arr[i] > arr[i + 1]) {
				int temp = arr[i];
				arr[i] = arr[i + 1];
				arr[i + 1] = temp;
			}
		// Largest element is fixed, recur for remaining array
		recursiveBubbleSort(arr, n - 1);
	}

	static void insertionSort(int arr[]) {
		int n = arr.length;
		for (int i = 1; i < n; ++i) {
			int key = arr[i];
			int j = i - 1;

			/*
			 * Move elements of arr[0..i-1], that are greater than key, to one position
			 * ahead of their current position
			 */
			while (j >= 0 && arr[j] > key) {
				arr[j + 1] = arr[j];
				j = j - 1;
			}
			arr[j + 1] = key;
		}
	}

	static void insertionSortRecursive(int arr[], int n) {
		// Base case
		if (n <= 1)
			return;

		// Sort first n-1 elements
		insertionSortRecursive(arr, n - 1);

		// Insert last element at its correct position
		// in sorted array.
		int last = arr[n - 1];
		int j = n - 2;

		/*
		 * Move elements of arr[0..i-1], that are greater than key, to one position
		 * ahead of their current position
		 */
		while (j >= 0 && arr[j] > last) {
			arr[j + 1] = arr[j];
			j--;
		}
		arr[j + 1] = last;
	}

	static void printArray(int arr[]) {
		int n = arr.length;
		for (int i = 0; i < n; ++i)
			System.out.print(arr[i] + " ");
		System.out.println();
	}
}

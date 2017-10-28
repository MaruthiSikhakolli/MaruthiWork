package generalPrograms;

public class QuickSort {
	// Merges two subarrays of arr[].
    // First subarray is arr[l..m]
    // Second subarray is arr[m+1..r]
	public void _mergeSort(int arr[], int l, int m, int r) {
		// Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;
        
        /* Create temp arrays */
        int L[] = new int [n1];
        int R[] = new int [n2];
        
        /*Copy data to temp arrays*/
        for (int i=0; i<n1; ++i)
            L[i] = arr[l + i];
        for (int j=0; j<n2; ++j)
            R[j] = arr[m + 1+ j];
        
        /* Merge the temp arrays */
        
        // Initial indexes of first and second subarrays
        int i = 0, j = 0;
 
        // Initial index of merged subarry array
        int k = l;
        
        while (i < n1 && j < n2)
        {
            if (L[i] <= R[j])
            {
                arr[k] = L[i];
                i++;
            }
            else
            {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
        
        /* Copy remaining elements of L[] if any */
        while (i < n1)
        {
            arr[k] = L[i];
            i++;
            k++;
        }
 
        /* Copy remaining elements of R[] if any */
        while (j < n2)
        {
            arr[k] = R[j];
            j++;
            k++;
        }
		
	}
	public void mergeSort(int arry[], int left, int right) {
		if(left < right) {
			int middle = (left+right) / 2;
			mergeSort(arry, left, middle);
			mergeSort(arry, middle+1, right);
			_mergeSort(arry, left, middle, right);
		}
	}
	 static void printArray(int arr[])
	    {
	        int n = arr.length;
	        for (int i=0; i<n; ++i)
	            System.out.print(arr[i] + " ");
	        System.out.println();
	    }
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int numbersArray [] = {12, 11, 13, 5, 6, 7};
		QuickSort quickSort = new QuickSort();
		quickSort.mergeSort(numbersArray, 0, numbersArray.length-1);
		
		System.out.println("\nSorted array");
        printArray(numbersArray);
		
		
	}

}

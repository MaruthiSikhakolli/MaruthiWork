package java_Programs;

public class FindCombinationsOfNos_SumUpToANumber {
	public static void main(String args[]) {
		int n = 12;
		int array[] = new int[n];
		findCombinationsUtil(array, 0, n, n);

	}

	public static void findCombinationsUtil(int arr[], int index, int num, int reducedNum) {
		// Base condition
		if (reducedNum < 0)
			return;

		// If combination is found, print it
		if (reducedNum == 0) {
			for (int i = 0; i < index; i++)
				System.out.print(arr[i] + " ");
			System.out.println();
			return;
		}

		// Find the previous number stored in arr[]. It helps in maintaining increasing order
		int prev = (index == 0) ? 1 : arr[index - 1];

		// note loop starts from previous number i.e. at array location index - 1
		for (int k = prev; k <= num; k++) {
			arr[index] = k;
			// call recursively with reduced number
			findCombinationsUtil(arr, index + 1, num, reducedNum - k);
		}
	}
}

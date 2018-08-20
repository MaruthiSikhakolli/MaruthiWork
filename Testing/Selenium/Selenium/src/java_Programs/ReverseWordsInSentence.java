//In this program we reverse the words of a sentence without using inbuilt string functions

//Examples:
//Input : "Welcome to geeksforgeeks"
//Output : "geeksforgeeks to Welcome"

//Input : "I love Java Programming"
//Output :"Programming Java love I"

package java_Programs;

import java.util.regex.Pattern;

public class ReverseWordsInSentence {
	public static void main(String[] args) {

		String str = "My name is Maruthi Srinivas Sikhakolli";
		// Specifying the pattern to be searched
		Pattern pattern = Pattern.compile("\\s");

		// splitting String str with a pattern (i.e ) splitting the string whenever
		// there is whitespace and store in temp array.
		String[] temp = pattern.split(str);
		String result = "";

		// Iterate over the temp array and store the string in reverse order.
		for (int i = 0; i < temp.length; i++) {
			if (i == temp.length - 1)
				result = temp[i] + result;
			else
				result = " " + temp[i] + result;
		}

		System.out.println(result);
	}
}

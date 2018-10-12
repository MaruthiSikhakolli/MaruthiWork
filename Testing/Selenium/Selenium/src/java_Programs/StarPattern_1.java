package java_Programs;
//This is a program to print the stars in the below patterns

//				*
//				* *
//				* * *
//				* * * *
//				* * * * *
//				* * * *
//				* * * 
//				* *
//				*
public class StarPattern_1 {
	public static void main(String[] args) {
		printStars(10);
	}
	
	public static void printStars(int n) {
		int i;
		for(i=1; i<=(n/2)+1; i++) {
			for(int j=1; j<=i; j++) {
				System.out.print("*");
			}
			System.out.println();
		}
		
		for(int k=i-1; i>=1; i--) {
			for(int j=1; j<=i; j++) {
				System.out.print("*");
			}
			System.out.println();
		}
	}
}

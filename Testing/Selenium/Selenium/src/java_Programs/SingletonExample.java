package java_Programs;

public class SingletonExample {
	private static SingletonExample myObj;

	public static SingletonExample getInstance() {
		if (myObj == null) {
			myObj = new SingletonExample();
		}
		return myObj;
	}

	public void getSomeThing() {
		System.out.println(myObj);
	}

	public static void main(String[] args) {
		SingletonExample st = SingletonExample.getInstance();
		System.out.println("Calling for the first time. The object obtained is : ");
		st.getSomeThing();
		System.out.println("Calling for the second time. The object obtained is : ");
		st.getSomeThing();
	}

}

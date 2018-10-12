package abstractClass_And_Interface_Example;

public class InterfaceChild implements InterfaceParent, InterfaceParent2 {

	@Override
	public void method1() {
		// TODO Auto-generated method stub
		System.out.println("Implementing Method 1");
	}

	@Override
	public void method2() {
		// TODO Auto-generated method stub
		System.out.println("Implementing Method 2");
	}

	@Override
	public void method3() {
		// TODO Auto-generated method stub
		System.out.println("Implementing Method 3");
	}

	@Override
	public void method4() {
		// TODO Auto-generated method stub
		System.out.println("Implementing Method 4");
	}
	
	public static void main(String args[]) {
		InterfaceChild interfaceChild = new InterfaceChild();
		interfaceChild.method1();
		interfaceChild.method2();
		interfaceChild.method3();
		interfaceChild.method4();
		
		InterfaceParent interfaceParent = new InterfaceChild();
		interfaceParent.method1();
		interfaceParent.method2();
		
		InterfaceParent2 interfaceParent2 = new InterfaceChild();
		interfaceParent2.method3();
		interfaceParent2.method4();
	}
}

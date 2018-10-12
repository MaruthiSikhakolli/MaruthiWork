package abstractClass_And_Interface_Example;

public class AbstractClassChild extends AbstractClassParent{

	@Override
	public void abstractMethod1() {
		// TODO Auto-generated method stub
		System.out.println("Implementing abstract method 1");
	}
	
	public static void main(String args[]) {
		AbstractClassChild abstractClassChild = new AbstractClassChild();
		abstractClassChild.abstractMethod1();
		abstractClassChild.method1();
	}

}

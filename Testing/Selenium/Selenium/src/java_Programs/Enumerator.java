package java_Programs;

import java.util.Enumeration;
import java.util.Vector;

public class Enumerator {
	public static void main(String a[]) {
		Vector<String> lang = new Vector<String>();
		
		lang.add("JAVA");
		lang.add("JSP");
		lang.add("SERVLET");
		lang.add("EJB");
		lang.add("PHP");
		lang.add("PERL");
		
		Enumeration<String> en = lang.elements();
		while (en.hasMoreElements()) {
			System.out.println(en.nextElement());
		}
	}
}

import java.io.File;

import org.jasypt.util.text.BasicTextEncryptor;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

public class Sikuli {
	
	public static String param[];
	
	public static String decrypt (String s) {
		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
		textEncryptor.setPassword("1213");
		String str = "";
		try {
			str = textEncryptor.decrypt(s);
		} catch(Exception e) {
			str = s;
		}
		return str;
	}
	
	public void browserAuthenticate() {
		try {
			Screen sc = new Screen();
			String username = param[1].split("::")[0];
			String password = param[1].split("::")[1];
			
			File file = new File("../r");
			System.out.println(file.getCanonicalPath().substring(0,file.getCanonicalPath().length()-1)+"Screenshots/Consumer/");
			String path = file.getCanonicalPath().substring(0,file.getCanonicalPath().length()-1)+"Screenshots\\Consumer\\";
			
			Pattern cmd = new Pattern(path+"CMD.png");
			cmd.similar((float)0.85);
			Pattern cmd1 = new Pattern(path+"CMD2.png").targetOffset(0, -8);
			cmd1.similar((float)0.78);
			Pattern cmd2 = new Pattern(path+"CMD3.png").targetOffset(-8, -13);
			cmd2.similar((float)0.93);
			
			Pattern un = new Pattern(path+"Username.png");
			un.similar((float)0.60);
			Pattern pwd = new Pattern(path+"Password.png");
			pwd.similar((float)0.60);
			Pattern ok = new Pattern(path+"OK.png");
			ok.similar((float)0.75);
			
			Pattern unx = new Pattern(path+"Username_XP.PNG");
			unx.similar((float)0.70);
			Pattern pwdx = new Pattern(path+"Password_XP.png");
			pwdx.similar((float)0.70);
			Pattern okx = new Pattern(path+"OK_XP.png");
			okx.similar((float)0.75);
			
//			int i=0;
			/*if (!(sc.exists(un)!=null&&sc.exists(pwd)!=null&&sc.exists(ok)!=null||sc.exists(unx)!=null&&sc.exists(pwdx)!=null&&sc.exists(okx)!=null)) {
				while (i<2) {
					if (sc.exists(cmd1)!=null) {
						sc.click(cmd1);
						Thread.sleep(2000);
					} else if (sc.exists(cmd2)!=null) {
						sc.click(cmd2);
						Thread.sleep(2000);
					} else if (sc.exists(cmd)!=null) {
						sc.click(cmd);
						Thread.sleep(2000);
					}
					if (!(sc.exists(un)!=null&&sc.exists(pwd)!=null&&sc.exists(ok)!=null||sc.exists(unx)!=null&&sc.exists(pwdx)!=null&&sc.exists(okx)!=null)) {
						i=2;
					} else
						i++;
				}
			}*/
			
			System.out.println("Execution Started...");
			try {
				sc.wait(un, 120);
			} catch (Exception e) {
			}
			
			if (sc.exists(un)!=null&&sc.exists(ok)!=null&&sc.exists(pwd)!=null) {
				sc.wait(un, 120);
				sc.type(un, decrypt(username));
				sc.type(pwd, decrypt(password));
				sc.click(ok);
			} else {
				System.out.println("Entered into XP zone...");
				sc.wait(unx,120);
				System.out.println("Step 1 Done");
				sc.type(unx,decrypt(username));
				System.out.println("Step 2 Done");
				sc.type(pwdx,decrypt(password));
				System.out.println("Step 3 Done");
				sc.click(okx);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void blockpopup(){
		try{
		Screen sc = new Screen();
		
		File file = new File("../r");
		System.out.println(file.getCanonicalPath().substring(0,file.getCanonicalPath().length()-1)+"Screenshots/Professional/");
		String path = file.getCanonicalPath().substring(0,file.getCanonicalPath().length()-1)+"Screenshots\\Professional\\";
		
		Pattern rdb = new Pattern(path+"RDB.png").targetOffset(0, 0);
		
		Pattern ok = new Pattern(path+"OK.png").targetOffset(0, 0);
		
			sc.wait(rdb, 120);
			sc.click(rdb);
			sc.wait(ok, 120);
			sc.click(ok);
			
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) throws Exception {
		param = args;
		Sikuli sikuli = new Sikuli();
		if (param[0].equalsIgnoreCase("browserauthenticate")) {
			sikuli.browserAuthenticate();
		}
		if (param[0].equalsIgnoreCase("blockpopup")) {
			System.out.println("Enter into Sikuli script");
			sikuli.blockpopup();
		}
	}
	
}

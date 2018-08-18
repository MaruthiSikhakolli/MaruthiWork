package CME;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import com.java.WebDriverUtils;

public class CME1 {
	public static void main(String[] args) {
		WebDriver webdriver=new ChromeDriver();
		webdriver.get("http://www.qa00.medscape.org/");
		webdriver.manage().window().maximize();
		webdriver.findElement(By.linkText("Log In")).click();
		webdriver.findElement(By.id("userId")).sendKeys("sel2012");
		webdriver.findElement(By.id("password")).sendKeys("123456");
		webdriver.findElement(By.id("loginbtn")).click();
		webdriver.get("http://www.qa00.medscape.org/viewarticle/770937");
		
		try{
			if(webdriver.findElement(By.linkText("Continue to Activity")).isDisplayed())
			{
				 webdriver.findElement(By.linkText("Continue to Activity")).click();
				 WebDriverUtils.waitForPageToLoad(webdriver, "5000");
			}
		}catch(Exception e){}
		
		try{
			if(webdriver.findElement(By.linkText("Transcript")).isDisplayed())
			{
				System.out.println("Transcript displayed");
				webdriver.findElement(By.linkText("Transcript")).click();
				Thread.sleep(2000);
			}}catch(Exception e){}
		String p1 = webdriver.getPageSource();
		//System.out.println(p1);
		String word = "form_id";
		//System.out.println("first:"+p1.indexOf(word));
		//System.out.println("last:"+p1.lastIndexOf(word)); 
		
		int q=0;int k[]=new int[10];
		for (int i = p1.length(); (i = p1.lastIndexOf(word, i - 1)) != -1; )
		{
			while(true){
		    //System.out.println("From last:"+i);
		    q++;
			break;
			}
		} 
		System.out.println("q="+q);
		int a=0;String temp[]=null;
		for (int i=-1;(i=p1.indexOf(word,i+1))!=-1;)
		{	
			if(a<q)
			{
				k[a]=i;
				//System.out.println("k["+a+"]="+i);
				String Page = webdriver.getPageSource().substring(i-10,i-7);
				//System.out.println("form_id="+Page);
				String f="";
				temp=Page.trim().split(f);
				for(int i1 =0; i1 < temp.length ; i1++)
				{
					//System.out.println("temp["+i1+"]="+temp[i1]);
				}	
				a++;
			}
			System.out.println("temp[2]="+temp[2]);
		}
	
	/*	int	n=webdriver.getPageSource().indexOf("form_id");
		//System.out.println("n="+n);
		int s=0;
		for(int j=k[s];j<k.length;j++)
		{
			String Page = webdriver.getPageSource().substring(n-10,n-7);
			System.out.println("page="+Page);
		}
	
		
		/*String f="";
		String temp[]=Page.trim().split(f);
		for(int i =0; i < temp.length ; i++)
		{
			System.out.println("temp["+i+"]="+temp[i]);
		}*/
		/*	if(n!=0)
		{
		System.out.println("Multi page");
		}
		else{
			System.out.println("single page");
		}
		
	try{
		if(webdriver.findElement(By.xpath("//div[@id='page_nav']")).isDisplayed())
		{
		String s=webdriver.findElement(By.xpath("//div[@id='page_nav']/div[2]")).getText();
		String s2="Page";
		//System.out.println("s="+s);
		if(s.contains(s2))
		{
			System.out.println("This is a Multi page");
		}else{
			System.out.println("This is a Single page");
		}
		}
	}catch(Exception e)
	{
		e.printStackTrace();
		System.out.println("This is a Single page in catch block");
	}	*/
		
	}
	}



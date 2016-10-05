package entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Scanner;



public class WebScraper {
    static String username = "";
    static String name = "";
    static String job = "";
    static String email = "";
    static String phone = "";


    public static void getData(String nameid)
    {
        String url = "http://www.ecs.soton.ac.uk/people/";
        try {
            Document doc = Jsoup.connect(url+nameid).get();

            username = nameid;

            name = doc.select("[property$=name]").text();
            job = doc.select("[property$=jobtitle").text();
            phone = doc.select("[property$=telephone").text().split(" ")[0];
            email = doc.select("[property$=email]").text().split(" ")[0];

            //If we can't find the name, we're screwed.
            if(name.equals(""))
            {
                System.out.println("Sorry! Couldn't find anything");
            }
            else {
            	//If we are here, everything is good!
                System.out.println("Got the data!");
            }

        } catch(Exception e) {
        	//Lots of things can go wrong. I can't even begin to think about the individual exception types.
            System.out.println("Something went wrong!");
            System.out.println(e);
        }
    }

    public static void printData()
    {
        System.out.printf("Name:\t\t%s\n", name);
        System.out.printf("Job Title:\t%s\n", job);
        if(!phone.equals(""))
            System.out.printf("Phone No:\t%s\n", phone);
        System.out.printf("Email:\t\t%s\n\n", email);
    }
    
    
    public static void anagram()
    {
    	if(username.equals(""))
    	{
    		System.out.println("Please gather a user's data first!");
    	}
    	else
    	{
    		String url = "http://wordsmith.org/anagram/anagram.cgi?anagram=";
    		//Sanitise the name for URL use.
    		String output = name.replace(' ', '+');
    		
    		try{
    			Document doc = Jsoup.connect(url+output).get();
    			String fullSite = doc.html();
    			
    			int index = fullSite.indexOf("document.body.style.cursor='wait';");
    			String shortSite = fullSite.substring(index);
    			String names[] = shortSite.split("<br>");
    			
    			// for some reason formatting is out on the first name in the list
    			names[1] = " " + names[1];
    			
    			for(int i = 1; i<51; i++)
    			{
    				System.out.printf("%d. \t%s\n", i, names[i]);
    			}
    			
    		}catch(Exception e)
    		{
    			System.out.println("Something went wrong!");
    			System.out.println(e);
    		}
    	}
    }
        
    public static void userMenu()
    {
    	boolean finished = false;
	    	while(!finished)
	    	{
	    	System.out.println("[1] Gather data for a given username");
	    	System.out.println("[2] Print Name, Job Description, Phone number (if found) and email address");
	    	System.out.println("[3] (IN PROGRESS) 50 Name anagrams!");
	    	System.out.println("[0] Quit the program");
	    	
	    	Scanner in = new Scanner(System.in);
	    	int choice = in.nextInt();
	    	System.out.println();
	    	
	    	switch(choice)
	    	{
	    	case 1:
	    		System.out.printf("Enter an ECS username:\n");
	    		//This just means we don't pick up any junk;
	    		in.nextLine();
	    		String ecsName = in.nextLine();
	    		getData(ecsName);
	    		break;
	    	case 2:
	    		printData();
	    		break;
	    	case 3:
	    		anagram();
	    		break;
	    	case 0:
	    		finished = true;
	    		System.out.println("Goodbye!");
	    		in.close();
	    		break;
	    	default:
	    		System.out.println("What? Didn't understand that");
	    	}
    	}
    }



    public static void main(String[] args) {
      
        //TODO: Add user input loop for easy use.
        System.out.println("Welcome to the ECS Detail finder!");
        System.out.println("Please enter one of the following commands:");
        userMenu();
        

       // System.out.print("hello");
    }
}

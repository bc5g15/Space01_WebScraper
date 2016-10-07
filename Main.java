package entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Scanner;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;

public class Start {
	static String username = "";
    static String name = "";
    static String job = "";
    static String email = "";
    static String phone = "";

    
    public static void getSecondaryData(String nameid)
    {
    	//TODO: Think about adding additional data from the secure.ecs.soton/people pages.
    }

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
    			
    			//Adding a bit extra here. This tends to timeout if the name is too long. Extending the time allowed.
    			org.jsoup.Connection con = Jsoup.connect(url+output);
    			//Allow 20 seconds
    			con.timeout(20000);
    			
    			//I want to find out how long this takes.
    			
    			long startTime = System.nanoTime();
    			
    			Document doc = con.get();
    			String fullSite = doc.html();
    			
    			int index = fullSite.indexOf("document.body.style.cursor='wait';");
    			String shortSite = fullSite.substring(index);
    			String names[] = shortSite.split("<br>");
    			
    			// for some reason formatting is out on the first name in the list
    			names[1] = " " + names[1];
    			
    			
    			//The last 3 names are always gibberish
    			int max_len = (names.length-3 >= 51) ? 51 : names.length-3;
    			
    			for(int i = 1; i<max_len; i++)
    			{
    				System.out.printf("%d. \t%s\n", i, names[i]);
    			}
    			
    			System.out.printf("Took %d ms\n\n", (System.nanoTime() - startTime)/1000000);
    			
    		}catch(Exception e)
    		{
    			//Again, Hideous general catch statement
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
	    	System.out.println("[3] Up To 50 Name Anagrams!");
	    	System.out.println("[0] Quit the program");
	    	
	    	Scanner in = new Scanner(System.in);
	    	
	    	int choice = Integer.MAX_VALUE;
	    	while(choice==Integer.MAX_VALUE)
	    	{
	    		if(in.hasNextInt())
	    		{
	    			choice=in.nextInt();
	    		}
	    		else
	    		{
	    			System.out.println("Please enter a number!");
	    			in.nextLine();
	    		}
	    	}
	    	System.out.println();
	    	
	    	switch(choice)
	    	{
	    	case 1:
	    		System.out.printf("Enter an ECS username:\n");
	    		//This just means we don't pick up any junk from the scanner
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
	// write your code here
      
        //TODO: Add user input loop for easy use.
        System.out.println("Welcome to the ECS Detail finder!");
        System.out.println("Please enter one of the following commands:");
        userMenu();
        

       // System.out.print("hello");
    }
}

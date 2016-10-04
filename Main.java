package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Scanner;



public class Main {

    static String name = "";
    static String job = "";
    static String email = "";
    static String phone = "";


    public static void getData(String nameid)
    {
        String url = "http://www.ecs.soton.ac.uk/people/";
        try {
            Document doc = Jsoup.connect(url+nameid).get();


            //store all the data in an array, so I can check for blanks.
            String data[] = new String[4];

            name = doc.select("[property$=name]").text();
            job = doc.select("[property$=jobtitle").text();
            phone = doc.select("[property$=telephone").text().split(" ")[0];
            email = doc.select("[property$=email]").text().split(" ")[0];

            if(name.equals(""))
            {
                System.out.println("Sorry! Couldn't find anything");
            }
            else {
                System.out.println("Got the data!");
            }

        } catch(Exception e) {
            System.out.println("Something went wrong!");
            System.out.print(e);
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



    public static void main(String[] args) {
	// write your code here
        Scanner in = new Scanner(System.in);
        getData("sjt4g11");
        getData("rfp");
        printData();

        //TODO: Add user input loop for easy use.

       // System.out.print("hello");
    }
}

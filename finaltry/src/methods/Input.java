/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methods;

import java.util.Scanner;

/**
 *
 * @author johnkenny
 */
public class Input 
{
    
    /*sets up a scanner item that can be used by all methods
    * in this class
    */
    static Scanner in = new Scanner(System.in);
    
    /*This method returns a line of text Input by the user
    the return type is a string
    */
    public static String inputOption()
    {
        String input = in.nextLine();
        return input;
    }
    
    //gets an input line
    
    public static String inputOption(String prompt)
    {
        System.out.println(prompt);
        String input = in.nextLine();
        return input;
    }
    
    /*This method returns a word of text Input by the user 
    the return type is a string
    */
    
    public static String inputOptionSingle()
    {
        String input;
        input = in.next();
        return input;
    }
    
    /* This converts keyboard Input from a String into an int(whole number) using the parseIntmethod
    a parament string is passed into this mehod and this is what is 
    */
    public static int convertToNumberInt()
    {
        boolean in = false;
        int no = 0;
        /* 
           This while loop is set and it will only exit once a user has entered a number
        */
        while(!in)
        {
            /*This try catch block is used for error handling
             int calls the inputOptionSingle method and uses the parseInt method to convert user
            input to a number
            */
            String convert = inputOption();
            try
            {
             no =Integer.parseInt(convert);
              in=true; 
            }
            catch(NumberFormatException nFE)
            {
                System.err.println("You have not entered a valid number \n Please enter a valid number \n");
            }
        }
        return no;
        
    }//close convertToNumberInt
    
    //converts a strint to an int
    
    public static int convertToNumberInt(String prompt)
    {
        
        boolean in = false;
        int no = 0;
        System.out.println(prompt);
        /* 
           This while loop is set and it will only exit once a user has entered a number
        */
        while(!in)
        {
            /*This try catch block is used for error handling
             int calls the inputOptionSingle method and uses the parseInt method to convert user
            input to a number
            */
            String convert = inputOption();
            try
            {
             no =Integer.parseInt(convert);
              in=true; 
            }
            catch(NumberFormatException nFE)
            {
                System.err.println("You have not entered a valid number \n Please enter a valid number \n");
            }
        }
        return no;
        
    }//close convertToNumberInt
    
    //converts string to double
    
    public static double convertToNumberDoub(String prompt)
    {
        
        boolean in = false;
        double no = 0;
        System.out.println(prompt);
        /* 
           This while loop is set and it will only exit once a user has entered a number
        */
        while(!in)
        {
            /*This try catch block is used for error handling
             int calls the inputOptionSingle method and uses the parseInt method to convert user
            input to a number
            */
            String convert = inputOption();
            try
            {
             no = Double.parseDouble(convert);
              in=true; 
            }
            catch(NumberFormatException nFE)
            {
                System.err.println("You have not entered a valid number \n Please enter a valid number \n");
            }
        }
        return no;
        
    }//close convertToNumberDoub
    
    //converts a string to long
    
    public static long convertToNumberLong(String prompt)
    { 
        boolean in = false;
        long no = 0;
        System.out.println(prompt);
        /* 
           This while loop is set and it will only exit once a user has entered a number
        */
        while(!in)
        {
            /*This try catch block is used for error handling
             int calls the inputOptionSingle method and uses the parseInt method to convert user
            input to a number
            */
            String convert = inputOption();
            try
            {
             no =Long.parseLong(convert);
              in=true; 
            }
            catch(NumberFormatException nFE)
            {
                System.err.println("You have not entered a valid number \n Please enter a valid number \n");
            }
        }
        return no;
        
    }//close convertToNumberLong
        
    //This loop is used to to confirm by the user
    
    public static boolean yesNo()
    {
        boolean locked = true;
        //This loop is lock until the user confirms yes or no
        while(locked)
        {
            String input = inputOption("Please enter yes or y to confrim \nEnter no or n to cancel.");
            if(input.equalsIgnoreCase("yes") ||input.equalsIgnoreCase("y"))
            {
                locked = false;
                //sets return type ends method
                return true;
            }
            else if(input.equalsIgnoreCase("no") ||input.equalsIgnoreCase("n"))
            {
                locked = false;
            }
            else
            {
                System.err.println("Invalid input.");
            }
            
        }
        return false;
        
    }//close yesNo()

    //check for a valid number
    
    public static boolean isNumber(String check)
    {
        //check if the parameter is a number
        try  
        {  
          int x = Integer.parseInt(check);  
        }  
        catch(NumberFormatException nfe)  
        {  
          return false;  
        }  
        //if its a number return true
        return true; 
        
    }//close isNumber

    //coverts a string to an int
    
    public static int convertToNum(String convert)
    {
        return Integer.parseInt(convert);
    }
    
    
    
}//end of class

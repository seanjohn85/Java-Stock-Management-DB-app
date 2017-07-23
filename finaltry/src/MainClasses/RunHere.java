/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainClasses;
import com.sun.rowset.internal.Row;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import jxl.read.biff.BiffException;
import jxl.write.*; 
import methods.MenuOptions;


/**
 *
 * @author johnkenny
 */
public class RunHere 
{
    //main method
    
    public static void main(String[] args) throws ParseException, SQLException, FileNotFoundException, IOException, WriteException, BiffException 
    {

        /*The MenuOptions class is ran with the
          menuHandler() method look in the MenuOptions class
          for details of what is hapening here
        */
               
       MenuOptions.menuHandler(); 
               
        
    }
}//close runHere
  



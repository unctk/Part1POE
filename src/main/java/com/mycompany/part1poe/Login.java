/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.part1poe;
import java.util.regex.Pattern;
//For the formatting of the cell phone number
/**
 *
 * @author Student
 */
public class Login {
    
    //declaration
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellNumber;
    private boolean registered = false; 
    
    
     // method checks whether username contains "_" and is <=5      
    public static boolean checkUserName(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("_") && username.length() <= 5) {
            return true;
        }
        else {
            return false;
        }
    }        
     
    
    //checks if method has correct international code and if numbers are not too many
    public static boolean checkCellPhoneNumber(String cellNumber) {
        String pattern = "\\+27[0-9]{1,10}";
        
        if (cellNumber == null || cellNumber.isEmpty()) {
            return false;
        }
        if (Pattern.matches(pattern, cellNumber)) {
            return true;
        }
        else {
            return false;
        }
    }        
            
            
}

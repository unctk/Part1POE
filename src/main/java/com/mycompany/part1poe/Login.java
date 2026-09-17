/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.part1poe;
import java.util.regex.Pattern;
/**
 *handles user reg and log in validation
 * 
 * @author Student
 */
public class Login {
    
    //declarations are made not static meaning every new login gets its own copy separetly
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
     
    
    //checks if method has correct international code(+27) and digits of 1 to 10
    public static boolean checkCellPhoneNumber(String cellNumber) {
        String pattern = "\\+27[0-9]{1,10}";   // \\+27 a literal and [0-9] is any single digit and {1,10} between 1 & 10 of those digits
        
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
      // checks if password is at least 8 char long and has capital letter, number ans special char
    public static boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() <8 ) {
            return false;
        }
               boolean hasCapital = false;
               boolean hasSpecial = false;      
               boolean hasNumber = false;
               
               for(int i = 0; i < password.length(); i++){
                   char ch = password.charAt(i);
                   
               if(Character.isDigit(ch)){
                  hasNumber = true; 
                  }
               else if (Character.isUpperCase(ch)) {
                   hasCapital = true;
                  }
               // anything that is not a letter or not a degit counts a special character !, @
               else if (!Character.isLetterOrDigit(ch)) {
                   hasSpecial = true;
               }
               
             }
            //password passes only if all 3 conditions are met within string   
        return hasCapital && hasNumber && hasSpecial;
        
    }
            
}

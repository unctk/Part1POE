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
        return username.contains("_") && username.length() <= 5;
    }        
     
    
    //checks if method has correct international code(+27) and digits of 1 to 10
    public static boolean checkCellPhoneNumber(String cellNumber) {
        String pattern = "\\+27[0-9]{1,10}";   // \\+27 a literal and [0-9] is any single digit and {1,10} between 1 & 10 of those digits
        
        if (cellNumber == null || cellNumber.isEmpty()) {
            return false;
        }
        return Pattern.matches(pattern, cellNumber);
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
    
    //validates all 3 fields and if all is valid
          public String registerUser(String firstName, String lastName, String username, String password, String cellNumber) {
        
        if(!checkUserName(username)){
            return "Username is not correctly formatted; please "+ "ensure that your username contains an underscore and "+ "is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please " + "ensure that the password contains at least eight characters, " + " a capital letter, a number, and a special character.";
        }  
        if(!checkCellPhoneNumber(cellNumber)) {
            return "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
        }
        //3 checks passed, saves the details against object so loginUser can verify later
       this.firstName = firstName; 
       this.lastName = lastName;
       this.username = username;
       this.password = password;
       this.cellNumber = cellNumber;
       this.registered = true;
       
       return "Username successfully captured.\n" + "Password successfully captured.\n" + "Cell number successfully captured.\n" + "Registration successful!";
          }
          
          //verifies if entered username and password match from when registerUser succceede
          public boolean loginUser (String enteredUsername, String enteredPassword) {
       if (!registered) {
           return false;
       }
       return this.username.equals(enteredUsername) && this.password.equals(enteredPassword);
   } 
    public String returnLoginStatus(boolean loginSuccess){
        if (loginSuccess) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        }
        else {
            return "Username or password incorrect, please try again.";
        }
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
        
        
    }
}


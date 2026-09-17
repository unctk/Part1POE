/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.part1poe;

import java.util.Scanner;
/**
 *
 * @author Student
 */
public class Part1POE {

    public static void main(String[] args) {
        //Declarations
       String firstName;
       String lastName;
       String username;
       String password;
       String cellNumber;

             
      //Scanner input = new Scanner (); 
       Scanner input = new Scanner(System.in);
       Login login = new Login();
       
       
       // Ask for users first name and last name
       System.out.print("Please enter your first name: ");
       firstName = input.nextLine();
       
       System.out.print("Please enter your last name: ");
       lastName = input.nextLine();
       
       
       //username validation
       while (true) {
           System.out.print("Please enter your username: ");
           username = input.nextLine();
           
             if (login.checkUserName(username)) {
            System.out.println("Username successfully captured.");
              break;
       }
             else {
            System.out.println("Username is not correctly formatted; please "+ "ensure that your username contains an underscore and "+ "is no more than five characters in length.");
                               
            System.out.println("");
       }
             
    }
       //password validation
       while (true) {
           System.out.print("Please enter your password: ");
           password = input.nextLine();
           
           if (login.checkPasswordComplexity(password)) {
              System.out.println("Password successfully captured.");
               break;
           }
           else {
               System.out.println("Password is not correctly formatted; please " + "ensure that the password contains at least eight characters, " + " a capital letter, a number, and a special character.");
               System.out.println();
              
           }
       }
       //Cell phone number validation
        while (true) {
           System.out.print("Please enter your South African cell phone number (include +27)" + ", example +27714224567: ");
           cellNumber = input.nextLine();
           
             if (login.checkCellPhoneNumber(cellNumber)) {
            System.out.println("Cell phone number successfully captured.");
              break;
            }
             
             else {
            System.out.println("Cell phone number is incorrectly formatted or does not contain international code." + "Try again");
            
            }   
        }
        //Storing of details against Login class
}
}

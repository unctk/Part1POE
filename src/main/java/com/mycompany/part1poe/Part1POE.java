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
       
    }
}

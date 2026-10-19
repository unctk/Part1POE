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
        String regResult = login.registerUser(firstName, lastName, username, password, cellNumber);
        
        System.out.println();
        System.out.print(regResult);
        
        
        //user must use same username and password to login
         System.out.println();
        System.out.println("-Login-");
        
        
         System.out.print("Please enter your username: ");
           String loginUsername = input.nextLine();
           
        System.out.print("Please enter your password: ");
           String loginPassword = input.nextLine();
           
           boolean loginSuccess =login.loginUser(loginUsername, loginPassword); 
         System.out.println(login.returnLoginStatus(loginSuccess));
        
        //Part 2: users should be able to send messages if they have logged in successfully
        if (loginSuccess) {
            runQuickChat(input);
        }
         
         input.close();
}
    //Part 2 - Sending Messages.
    //Runs the QuickChat menu loop: lets the user send, view, or quit, capped at the number of messages they choose to enter at the start.
    private static void runQuickChat (Scanner input) {
        System.out.println();
        System.out.println("Welcome to QuickChat.");
        
        System.out.print("How many messages would you like to enter? ");
        int numMessages = Integer.parseInt(input.nextLine().trim());
        int messagesEntered = 0;
    
        while (true) {
            System.out.println();
            System.out.println("1) Send Messages");
            System.out.println("2) Coming Soon");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");
            String choice = input.nextLine().trim();
            
        if (choice.equals("1")) {
            if (messagesEntered >= numMessages) {
              System.out.println("You have already entered your "  + "chosen number of messages.");
              continue;
            }

            // Recipient
            // Checked and reported, but does not block the user from continuing - the message can still be created, sent, stored or disregarded either way.
            System.out.print("Enter recipient cell number: ");
             String recipient = input.nextLine();
             System.out.println(Message.checkRecipientCell(recipient));
             
             //Message text
             System.out.print("Enter your message: ");
             String messageText = input.nextLine();
             System.out.println(Message.checkMessageLength(messageText));
             
             Message message = new Message (recipient, messageText);
             System.out.println(message.checkMessageIDCreated());
             messagesEntered++;
             
             // Send/ Store/ Disregard
            System.out.println("Send Message");
            System.out.println("Disregard Message");
            System.out.println("Store Message to send later");
            System.out.print("Choose an option (send/store/disregard): ");
             String sendChoice = input.nextLine().trim();
             
             String result = message.sentMessage(sendChoice);
             System.out.println(result);
             
             if (sendChoice.equalsIgnoreCase("send")) {
                    System.out.println();
                    System.out.println("Message ID: " + message.getMessageID());
                    System.out.println("Message Hash: " + message.getMessageHash());
                    System.out.println("Recipient: " + message.getRecipient());
                    System.out.println("Message: " + message.getMessageText());
                }
    
             } 
        else if (choice.equals("2")){
            System.out.println("Coming Soon.");
            
        }
        else if (choice.equals("3")) {
            System.out.println();
            System.out.println("Total messages sent: " + Message.returnTotalMessages());
            break;      
        }
        
        else {
            System.out.println("Please choose option 1, 2 or 3.");
        }
      } 
    }
  }
        

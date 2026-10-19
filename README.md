# Part1POE - Registration and Login and Messaging

**Student Name:** Thakgalo Maleka
**Student Number:** ST10511101
**Module:** PROG5121
**School:** Rosebank International

## Overview
 This POE is a console java program that demonstrates user registration and login using
oop, data validation and decision making.
Part 1: Registration and Login: a user registers with a username, password and South African cell phone number (each validated), then logs in with the same details.
Part 2: Sending Messages: once logged in, the user can create, send, store or disregard messages. Each message gets a unique ID and an auto-generated hash, and stored messages are saved to a JSON file.

The business logic is kept separate from the console interface (Login and Message classes vs. the main class), so it can be unit tested directly and run automatically through GitHub Actions.

## Features

**Part 1: Registration and Login**
-User registration with three validated fields: username, password, and South African cell phone number
-Login verification against the registered details
-Clear, specific feedback messages for every valid/invalid case
**Part 2: Sending Messages** 
-Messaging is only available after a successful login
-Welcome message ("Welcome to QuickChat.") and a numeric menu
-The user chooses at the start how many messages they want to enter, and can only enter that many
-Each message gets an auto-generated 10-digit Message ID and Message Hash
-Recipient number and message length are validated, with specific feedback messages
-For each message the user can send, store (saved to JSON), or disregard it
-Full details of each sent message are displayed (Message ID, Message Hash, Recipient, Message)
-The total number of messages sent is displayed when the user quits
   
   ## Testing and Automation
-JUnit 5 test suites covering every rule in both parts, using the exact test data from the  assessment rubric
-GitHub Actions workflow that runs all tests automatically on every push
   
   ## Validation Rules
   ## Registration (Part 1)
   **Username**:  Must contain an underscore (_) and be no more than 5 characters long
   **Password**:  Must be at least 8 characters long, and contain at least one capital letter, one number, and one special character 
   **Cell phone number**: Must contain the South African international country code (`+27`) followed by up to 9 digits 

## Messaging (Part 2)
   **Message ID**: Randomly generated 10-digit number; must be no more than 10 characters
   **Recipient**: Must contain the international country code (+27); this reuses the same check as the Part 1 cell number
   **Message**: Must not exceed 250 characters; if it does, the user is told by how many characters
   **Message Hash**: First two digits of the Message ID, a colon, the message number, a colon, then the first and last words of the message in capitals, e.g. 00:0:HITONIGHT

  ## Using the Application

After logging in successfully, the user sees:

```
Welcome to QuickChat.

1) Send Messages
2) Show recently sent messages
3) Quit
```

| Option | What it does |
|---|---|
| **1) Send Messages** | Prompts for a recipient and message, validates them, generates the Message ID and Hash, then asks whether to **send**, **store**, or **disregard** the message |
| **2) Show recently sent messages** | Still in development; displays *"Coming Soon."* |
| **3) Quit** | Displays the total number of messages sent and exits |

The menu keeps running until the user chooses Quit.

| Send / Store / Disregard choice | Message shown |
|---|---|
| Send | "Message successfully sent." |
| Store | "Message successfully stored." (and the message is written to `messages.json`) |
| Disregard | "Press 0 to delete the message." |

### Storing messages as JSON

Messages chosen to be stored are appended to a `messages.json` file in the folder the program is run from, one JSON object per message:

```json
{"messageID":"4832643602","messageNumber":0,"recipient":"+27821234567","message":"Test message","messageHash":"48:0:TESTMESSAGE"}
```
   ## Classes

   ### Login
    Handles all registration and login logic, independent of the console interface, so it can be unit tested directly.
    
    -checkUserName - 'Boolean' - Validates the username format 
    -checkPasswordComplexity' - 'boolean' - Validates password complexity 
    -checkCellPhoneNumber' - 'boolean' - Validates the cell number format using a regular expression 
    -registerUser - 'String' - Validates all fields and registers the user if valid; returns the relevant status message 
    -loginUser - 'boolean' - Verifies login credentials against the registered details 
    -returnLoginStatus - 'String' - Returns the correct message for a login attempt's outcome 

   ### POEPart1
  The console application. Prompts the user through registration (looping on each field until it's valid), then prompts for login and displays the result.
   
   # How to Run

**Requirements:**  NetBeans

**In NetBeans:**
1. Open the project ('File + Open Project')
2. Right-click the project - **Run**

## Running the Unit Tests
 In NetBeans: right-click the project - **Test**.

## References

[GeeksforGeeks – How to Validate a Phone Number Using Regular Expression](https://www.geeksforgeeks.org/how-to-validate-a-phone-number-using-regular-expression/)

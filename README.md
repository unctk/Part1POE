# Part1POE - Registration and Login

**Student Name:** Thakgalo Maleka
**Student Number:** ST10511101
**Module:** PROG5121
**School:** Rosebank International

## Overview
This is part 1 of the POE for PROG5121. This POE is a console java program that demonstrates user registration and login using
oop, data validation and decision making.

A user is first asked to register to the system by providing a South African cellphone number, a username and password. 
Each value is then validated using a set of formatting constraints. The user is then asked to log in using the same values used during registration.


## Features

**User registration**
  - Username
  - Password
  - South African cell phone number
**Login verification** 
   -Login is verified using registration data
   -Provides explicit information on success or faliure
   -POEPart1 main class and Login are separated allowing for unit testing.
   

   ## Validation Rules
   **Username**:  Must contain an underscore (_) and be no more than 5 characters long
   **Password**:  Must be at least 8 characters long, and contain at least one capital letter, one number, and one special character 
   **Cell phone number**: Must contain the South African international country code (`+27`) followed by up to 9 digits 
  
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

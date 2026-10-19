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
## Project Structure

> The project was renamed to `Part1POE`, so the package is `com.mycompany.part1poe`. Make sure the `package` line in every `.java` file matches the folder it sits in.

```
Part1POE/
├── pom.xml
├── .github/
│   └── workflows/
│       └── maven.yml          # GitHub Actions: runs the tests automatically
└── src/
    ├── main/
    │   └── java/
    │       └── com/mycompany/part1poe/
    │           ├── Login.java       # Part 1: registration & login logic
    │           ├── Message.java     # Part 2: message creation, hashing, sending, storing
    │           └── Part1POE.java    # Console application entry point
    └── test/
        └── java/
            └── com/mycompany/part1poe/
                ├── LoginTest.java   # Part 1 JUnit 5 tests
                └── MessageTest.java # Part 2 JUnit 5 tests
```
   ## Class Design

### `Login` (Part 1)

| Method | Returns | Description |
|---|---|---|
| `checkUserName(String)` | `boolean` | Validates the username format |
| `checkPasswordComplexity(String)` | `boolean` | Validates password complexity |
| `checkCellPhoneNumber(String)` | `boolean` | Validates the cell number format using a regular expression |
| `registerUser(...)` | `String` | Validates all fields and registers the user if valid; returns the relevant status message |
| `loginUser(String, String)` | `boolean` | Verifies login credentials against the registered details |
| `returnLoginStatus(boolean)` | `String` | Returns the correct message for a login attempt's outcome |

### `Message` (Part 2)

| Method | Returns | Description |
|---|---|---|
| `checkMessageID(String)` | `boolean` | Ensures the Message ID is no more than ten characters |
| `checkRecipientCell(String)` | `String` | Validates the recipient number (reuses `Login.checkCellPhoneNumber()`) and returns the matching message |
| `checkMessageLength(String)` | `String` | Checks the message is 250 characters or fewer, otherwise reports how many characters over it is |
| `createMessageHash(String, int, String)` | `String` | Builds the Message Hash from the ID, message number, and first/last words |
| `checkMessageIDCreated()` | `String` | Returns the "Message ID generated" confirmation |
| `sentMessage(String)` | `String` | Handles the send / store / disregard choice and returns the matching message |
| `storeMessage()` | `void` | Writes the message to `messages.json` |
| `printMessages()` | `String` | Returns the details of every message sent while the program is running |
| `returnTotalMessages()` | `int` | Returns the total number of messages sent |

### `Part1POE`

The console application. Handles registration (looping on each field until it is valid), login, and, if login succeeds, the QuickChat menu loop.

## How to Run

**Requirements:** Java JDK 17+, Maven (or NetBeans, which bundles both)

**In NetBeans:**
1. Open the project (`File → Open Project`)
2. Right-click the project → **Run**

**From the command line:**
```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.mycompany.part1poe.Part1POE"
```

## Running the Unit Tests

The test suites use **JUnit 5**. Run them with:

```bash
mvn test
```

Or in NetBeans: right-click the project → **Test**.

**`LoginTest.java`** verifies:
- Each validation method (`checkUserName`, `checkPasswordComplexity`, `checkCellPhoneNumber`) against valid and invalid test data
- `registerUser()`'s exact returned message for each failure case and the success case
- `loginUser()` and `returnLoginStatus()` for successful and failed logins

**`MessageTest.java`** verifies:
- Message length: success (`"Message ready to send."`) and failure (reports the number of characters over 250)
- Recipient number: correctly and incorrectly formatted
- Message hash: matches the expected format (e.g. `00:0:HITONIGHT`), tested for more than one message
- Message ID creation message
- Send, disregard and store outcomes

All test data matches the values specified in the module's assessment rubric.

## Continuous Integration (GitHub Actions)

The workflow in `.github/workflows/maven.yml` runs `mvn test` automatically every time code is pushed to `main` or a pull request is opened against it, so every change is tested before it is merged. The result of each run can be seen under the **Actions** tab of this repository.

New work is done on a separate feature branch and merged into `main` once the tests pass, so `main` always stays in working order.

## References

- The regular expression logic used in `checkCellPhoneNumber()` was adapted from: [GeeksforGeeks – How to Validate a Phone Number Using Regular Expression](https://www.geeksforgeeks.org/how-to-validate-a-phone-number-using-regular-expression/)
- The approach used in `storeMessage()` to write messages as JSON was based on: [Baeldung – Working with JSON in Java](https://www.baeldung.com/java-json)

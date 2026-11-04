# POE - QuickChat: Registration and Login and Messaging

**Student Name:** Thakgalo Maleka
**Student Number:** ST10511101
**Module:** PROG5121 | Programming 1A 
**School:** Rosebank International

## Overview
 This repository contains my Portfolio of Evidence (POE) for PROG5121. It is a console-based Java application called **QuickChat**, built in three parts:

- **Part 1 – Registration and Login:** a user registers with a username, password and South African cell phone number (each validated), then logs in with the same details.
- **Part 2 – Sending Messages:** once logged in, the user can create, send, store or disregard messages. Each message gets a unique ID and an auto-generated hash, and stored messages are saved to a JSON file.
- **Part 3 – Store Data and Display Task Report:** every message is recorded in arrays, stored messages are read back from the JSON file when the program starts, and a new **Stored Messages** menu lets the user search, delete and report on them.

The business logic is kept separate from the console interface (`Login`, `Message` and `MessageStore` classes vs. the main class), so it can be unit tested directly and run automatically through GitHub Actions.

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
 **Part 3: Store Data and Display Task Report**
- Arrays for **sent**, **disregarded** and **stored** messages, plus arrays holding every **message hash** and **message ID**. All of them are filled from what the user does, with nothing hard-coded
- Stored messages are **read from the JSON file into the stored messages array** when QuickChat starts
- A fourth main menu option, **Stored Messages**, where the user can:
  - display the sender and recipient of all stored messages
  - display the longest stored message
  - search for a message ID and see the recipient and message
  - search for all sent or stored messages for a particular recipient
  - delete a message using its message hash (the JSON file is updated too)
  - display a report of the full details of all stored messages
   
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
4) Stored Messages
```

| Option | What it does |
|---|---|
| **1) Send Messages** | Prompts for a recipient and message, validates them, generates the Message ID and Hash, then asks whether to **send**, **store**, or **disregard** the message |
| **2) Show recently sent messages** | Still in development; displays *"Coming Soon."* |
| **3) Quit** | Displays the total number of messages sent and exits |
| **4) Stored Messages** | Opens the Stored Messages menu (see below) |

The menu keeps running until the user chooses Quit.

| Send / Store / Disregard choice | Message shown |
|---|---|
| Send | "Message successfully sent." |
| Store | "Message successfully stored." (and the message is written to `messages.json`) |
| Disregard | "Press 0 to delete the message." |

### Stored Messages menu (Part 3)

| Option | What it does |
|---|---|
| **1** | Shows the sender and recipient of every stored message |
| **2** | Shows the longest stored message |
| **3** | Asks for a message ID and shows the recipient and message |
| **4** | Asks for a recipient's cell number and shows every sent or stored message for them |
| **5** | Asks for a message hash and deletes that message |
| **6** | Shows a report with the ID, hash, recipient, message and sender of every stored message |
| **7** | Goes back to the main menu |

### Storing messages as JSON

Stored messages are written to a `messages.json` file in the folder the program is run from, as a JSON array with one object per message. The file is read back into the arrays each time the program starts, and is rewritten whenever a stored message is added or deleted:

```json
[
  {"messageID":"9860538335","messageNumber":1,"sender":"Kyle Smith","recipient":"+27838884567","message":"Where are you? You are late! I have asked you to be on time.","messageHash":"98:1:WHERETIME"}
]
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
    │           ├── MessageStore.java # Part 3: arrays, search, delete, report, JSON read/write
    │           └── Part1POE.java    # Console application entry point
    └── test/
        └── java/
            └── com/mycompany/part1poe/
                ├── LoginTest.java   # Part 1 JUnit 5 tests
                ├── MessageTest.java      # Part 2 JUnit 5 tests
                └── MessageStoreTest.java # Part 3 JUnit 5 tests
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

### `MessageStore` (Part 3)

Holds the arrays and everything that works on them. Each array is a plain Java array (for example `String[] sentMessages = new String[100]`) with a counter for how many positions are in use. The message ID, hash, sender, recipient, text and flag arrays are **parallel arrays**: position 0 in each describes the first message, position 1 the second, and so on, so a search in one array (for example the hashes) finds the matching value in another.

| Method | Returns | Description |
|---|---|---|
| `addMessage(...)` | `void` | Adds a sent, stored or disregarded message to the arrays (stored messages are also written to the JSON file) |
| `getSentMessages()`, `getDisregardedMessages()`, `getStoredMessages()` | `String[]` | The message arrays |
| `getMessageHashes()`, `getMessageIDs()` | `String[]` | The hash and ID arrays |
| `displaySenderAndRecipient()` | `String` | Sender and recipient of every stored message |
| `getLongestStoredMessage()` | `String` | The longest stored message |
| `searchByMessageID(String)` | `String[]` | The recipient and message for an ID, or `null` if not found |
| `searchByRecipient(String)` | `String[]` | Every sent or stored message for a recipient |
| `deleteByHash(String)` | `String` | Deletes the message with that hash and confirms it |
| `generateReport(String)` | `String` | Full details of every message with a given flag |
| `saveStoredMessages()` | `void` | Writes all stored messages to the JSON file |
| `loadStoredMessages()` | `int` | Reads the JSON file into the arrays and returns how many were loaded |

### `Part1POE`

The console application. Handles registration (looping on each field until it is valid), login, and, if login succeeds, the QuickChat menu loop and the Stored Messages menu.

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

**`MessageStoreTest.java`** populates the arrays with the five test messages from the rubric (using the same code the application uses) and verifies:
- The sent, disregarded and stored arrays, and the hash and ID arrays, are populated correctly
- The longest stored message is returned
- Searching by message ID returns the right recipient and message (and nothing for an unknown ID)
- Searching by recipient returns the sent and stored messages for that recipient
- Deleting a message by hash removes it and confirms it (and reports an unknown hash)
- The sent and stored reports list the full details
- Stored messages are read back from the JSON file, and a deleted message is gone from the file

All test data matches the values specified in the module's assessment rubric.

## Continuous Integration (GitHub Actions)

The workflow in `.github/workflows/maven.yml` runs `mvn test` automatically every time code is pushed to `main` or a pull request is opened against it, so every change is tested before it is merged. The result of each run can be seen under the **Actions** tab of this repository.

New work is done on a separate feature branch and merged into `main` once the tests pass, so `main` always stays in working order.

## References

- The regular expression logic used in `checkCellPhoneNumber()` was adapted from: [GeeksforGeeks – How to Validate a Phone Number Using Regular Expression](https://www.geeksforgeeks.org/how-to-validate-a-phone-number-using-regular-expression/)
- The approach used in `MessageStore` to write the stored messages as JSON and read them back was based on: [Baeldung – Working with JSON in Java](https://www.baeldung.com/java-json) and the [Oracle `java.nio.file.Files` documentation](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/nio/file/Files.html)
  

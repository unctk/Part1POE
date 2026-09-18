/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package TestClasses;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author Student
 */
public class LoginTest {
    
    private Login login ;
    
    @BeforeEach
   public void setUp() {
       //fresh Login object before each test, so that tests dont interfere with one another
       login = new Login();
}
   
   //username tests, est (assertTrue/False) - checking the three boolean methods and loginUser
      @Test
    public void testUsernameCorrectlyFormatted() {
        // Test Data: "kyl_1"
        boolean actual = Login.checkUserName("kyl_1");
        assertTrue(actual, "Expect a correctly formatted username to return true");
    }
    
    @Test
    public void testUsernameIncorrectlyFormatted() {
        // Test Data: "kyle!!!!!!!"
        boolean actual = Login.checkUserName("kyle!!!!!!!");
        assertFalse(actual, "Expect an incorrectly formatted username to return false");
    }
 
    @Test
    public void testPasswordMeetsComplexityRequirements() {
        // Test Data: "Ch&&sec@ke99!"
        boolean actual = Login.checkPasswordComplexity("Ch&&sec@ke99!");
        assertTrue(actual, "Expect a password meeting all complexity "
                + "requirements to return true");
    }
 
    @Test
    public void testPasswordDoesNotMeetComplexityRequirements() {
        // Test Data: "password"
        boolean actual = Login.checkPasswordComplexity("password");
        assertFalse(actual, "Expect a password failing the complexity "
                + "requirements to return false");
        
        
    }
    
    @Test
    public void testCellPhoneNumberCorrectlyFormatted() {
        // Test Data: +27838968976
        boolean actual = Login.checkCellPhoneNumber("+27838968976");
        assertTrue(actual, "Expect a correctly formatted cell phone number "
                + "to return true");
    }
 
    @Test
    public void testCellPhoneNumberIncorrectlyFormatted() {
        // Test Data: 08966553
        boolean actual = Login.checkCellPhoneNumber("08966553");
        assertFalse(actual, "Expect an incorrectly formatted cell phone "
                + "number to return false");
    }
 
    @Test
    public void testLoginSuccessful() {
        login.registerUser("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        boolean actual = login.loginUser("kyl_1", "Ch&&sec@ke99!");
        assertTrue(actual, "Expect login to succeed with the correct "
                + "username and password");
    }
 
    @Test
    public void testLoginFailed() {
        login.registerUser("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        boolean actual = login.loginUser("kyl_1", "wrongPassword1!");
        assertFalse(actual, "Expect login to fail with an incorrect password");
    }
    //Test (assertEquals) - checking the String-returning methods: registerUser and returnLoginStatus.
  
    @Test
    public void testRegisterUser_PasswordDoesNotMeetComplexity() {
        String expected = "Password is not correctly formatted; please "
                + "ensure that the password contains at least eight "
                + "characters,  a capital letter, a number, and a special "
                + "character.";
        String actual = login.registerUser("Kyle", "Smith", "kyl_1",
                "password", "+27838968976");
        assertEquals(expected, actual, "Expect registerUser() to return the "
                + "password error message when the password is invalid");
    }
 
    @Test
    public void testRegisterUser_CellNumberIncorrectlyFormatted() {
        String expected = "Cell number is incorrectly formatted or does "
                + "not contain an international code; please correct the "
                + "number and try again.";
        String actual = login.registerUser("Kyle", "Smith", "kyl_1",
                "Ch&&sec@ke99!", "08966553");
        assertEquals(expected, actual, "Expect registerUser() to return the "
                + "cell number error message when the number is invalid");
    }
 
    @Test
    public void testRegisterUser_AllFieldsValid() {
        String expected = "Username successfully captured.\n"
                + "Password successfully captured.\n"
                + "Cell number successfully captured.\n"
                + "Registration successful!";
        String actual = login.registerUser("Kyle", "Smith", "kyl_1",
                "Ch&&sec@ke99!", "+27838968976");
        assertEquals(expected, actual, "Expect registerUser() to return the "
                + "success message when all fields are valid");
    }
 
    @Test
    public void testReturnLoginStatus_Successful() {
        login.registerUser("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        boolean loginResult = login.loginUser("kyl_1", "Ch&&sec@ke99!");
 
        String expected = "Welcome Kyle, Smith it is great to see you again.";
        String actual = login.returnLoginStatus(loginResult);
        assertEquals(expected, actual, "Expect the welcome message after a "
                + "successful login");
    }
 
    @Test
    public void testReturnLoginStatus_Failed() {
        login.registerUser("Kyle", "Smith", "kyl_1", "Ch&&sec@ke99!", "+27838968976");
        boolean loginResult = login.loginUser("kyl_1", "wrongPassword1!");
 
        String expected = "Username or password incorrect, please try again.";
        String actual = login.returnLoginStatus(loginResult);
        assertEquals(expected, actual, "Expect the error message after a "
                + "failed login");
    }
}
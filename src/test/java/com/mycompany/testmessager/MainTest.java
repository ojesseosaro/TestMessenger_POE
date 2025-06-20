/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.testmessager;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MainTest {

    @Test
    void testCheckUserName_Valid() {
        assertTrue(Main.checkUserName("kyl_l"));
        assertTrue(Main.checkUserName("_kyl"));
    }

    @Test
    void testCheckUserName_Invalid() {
        assertFalse(Main.checkUserName("kyl!!!!!"));  // too long
        assertFalse(Main.checkUserName("kyl!"));   // no underscore
        assertFalse(Main.checkUserName("kyl"));     // no underscore and short
    }

    @Test
    void testCheckPasswordComplexity_Valid() {
        assertTrue(Main.checkPasswordComplexity("Abcdef1!"));
        assertTrue(Main.checkPasswordComplexity("Password9#"));
    }

    @Test
    void testCheckPasswordComplexity_Invalid() {
        assertFalse(Main.checkPasswordComplexity("password"));     // no uppercase, number, special char
        assertFalse(Main.checkPasswordComplexity("PASSWORD1"));    // no special char
        assertFalse(Main.checkPasswordComplexity("Pass!word"));    // no number
        assertFalse(Main.checkPasswordComplexity("Pass1!"));       // too short
    }

    @Test
    void testCheckCellPhoneNumber_Valid() {
        assertTrue(Main.checkCellPhoneNumber("+12345678901"));
        assertTrue(Main.checkCellPhoneNumber("+441234567890"));
    }

    @Test
    void testCheckCellPhoneNumber_Invalid() {
        assertFalse(Main.checkCellPhoneNumber("1234567890"));     // no +
        assertFalse(Main.checkCellPhoneNumber("+123"));           // too short
        assertFalse(Main.checkCellPhoneNumber("+12345678901234567")); // too long
        assertFalse(Main.checkCellPhoneNumber("+12-34567890"));   // invalid chars
    }

    @Test
    void testRegisterUser_Success() {
        assertEquals("Registration successful.", Main.registerUser("abc_d", "Abcdef1!"));
    }

    @Test
    void testRegisterUser_Failure() {
        assertEquals("Registration failed.", Main.registerUser("abcde", "abc"));
        assertEquals("Registration failed.", Main.registerUser("abc", "password"));
    }

    @Test
    void testLoginUser_Success() {
        assertTrue(Main.loginUser("user", "pass1!", "user", "pass1!"));
    }

    @Test
    void testLoginUser_Failure() {
        assertFalse(Main.loginUser("user", "pass1!", "user1", "pass1!"));
        assertFalse(Main.loginUser("user", "pass1!", "user", "wrongpass"));
    }
}

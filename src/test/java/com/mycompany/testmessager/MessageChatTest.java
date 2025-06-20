/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.testmessager;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.mycompany.testmessager.MessageChat.Message;

public class MessageChatTest {

    @Test
    void testMessageGetters() {
        Message msg = new Message("12345", "Alice", "Bob", "Hello there");
        assertEquals("12345", msg.getMessageID());
        assertEquals("Alice", msg.getSender());
        assertEquals("Bob", msg.getRecipient());
        assertEquals("Hello there", msg.getMessageText());
    }

    @Test
    void testGetHash_Normal() {
        Message msg = new Message("12345", "Alice", "Bob", "Hello there world");
        // messageID first two chars: "12"
        // space count in "Hello there world" = 2 spaces
        // uppercase without spaces: "HELLOTHEREWORLD"
        String expectedHash = "12:2:HELLOTHEREWORLD";
        assertEquals(expectedHash, msg.getHash());
    }

    @Test
    void testGetHash_ShortMessageID() {
        Message msg = new Message("1", "Alice", "Bob", "Hi");
        // messageID length < 2 => firstTwoDigits = "00"
        // space count in "Hi" = 0
        // uppercase without spaces: "HI"
        String expectedHash = "00:0:HI";
        assertEquals(expectedHash, msg.getHash());
    }

    @Test
    void testCheckInternationalRecipient_Valid() {
        assertTrue(MessageChat.checkInternationalRecipient("+27123456789"));
    }

    @Test
    void testCheckInternationalRecipient_Invalid() {
        assertFalse(MessageChat.checkInternationalRecipient("27123456789"));  // missing +
        assertFalse(MessageChat.checkInternationalRecipient("+2712345678"));  // too short
        assertFalse(MessageChat.checkInternationalRecipient("+271234567890")); // too long
        assertFalse(MessageChat.checkInternationalRecipient("+27123A56789")); // contains letter
    }

    @Test
    void testGenerateMessageID_Format() {
        String id = MessageChat.generateMessageID();
        assertNotNull(id);
        assertEquals(10, id.length());
        assertTrue(id.startsWith("0"));
        assertTrue(id.substring(1).chars().allMatch(Character::isDigit));
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.testmessager;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class MessageArrayManagerTest {

    private MessageChat.Message testMessage;

    @Before
    public void setup() {
        testMessage = new MessageChat.Message("0123456789", "+27123456789", "+27987654321", "Hello World");
    }

    @Test
    public void testAddToSentMessages() {
        int initialSize = MessageArrayManager.getSentMessagesReport().length();
        MessageArrayManager.addToSentMessages(testMessage);
        String report = MessageArrayManager.getSentMessagesReport();
        assertTrue(report.contains(testMessage.getMessageID()));
    }

    @Test
    public void testSearchMessageByID_Found() {
        MessageArrayManager.addToSentMessages(testMessage);
        String result = MessageArrayManager.searchMessageByID(testMessage.getMessageID());
        assertTrue(result.contains("Sender: " + testMessage.getSender()));
    }

    @Test
    public void testSearchMessageByID_NotFound() {
        String result = MessageArrayManager.searchMessageByID("nonexistentID");
        assertTrue(result.contains("not found"));
    }

    @Test
    public void testDeleteMessageByHash() {
        MessageArrayManager.addToSentMessages(testMessage);
        boolean deleted = MessageArrayManager.deleteMessageByHash(testMessage.getHash());
        assertTrue(deleted);
    }

    @Test
    public void testDeleteMessageByHash_NotFound() {
        boolean deleted = MessageArrayManager.deleteMessageByHash("invalidhash");
        assertFalse(deleted);
    }
}

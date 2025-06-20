/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testmessager;

public class MessageChat {

    public static class Message {
        private final String messageID;
        private final String sender;
        private final String recipient;
        private final String body;

        public Message(String messageID, String sender, String recipient, String body) {
            this.messageID = messageID;
            this.sender = sender;
            this.recipient = recipient;
            this.body = body;
        }

        public String getMessageID() { return messageID; }
        public String getSender() { return sender; }
        public String getRecipient() { return recipient; }
        public String getMessageText() { return body; }

        public String getHash() {
            String firstTwoDigits = messageID.length() >= 2 ? messageID.substring(0, 2) : "00";
            int spaceCount = (int) body.chars().filter(c -> c == ' ').count();
            String upperNoSpaces = body.replaceAll(" ", "").toUpperCase();
            return firstTwoDigits + ":" + spaceCount + ":" + upperNoSpaces;
        }

        @Override
        public String toString() {
            return "Message{" +
                   "ID='" + messageID + '\'' +
                   ", From='" + sender + '\'' +
                   ", To='" + recipient + '\'' +
                   ", Body='" + body + '\'' +
                   ", Hash='" + getHash() + '\'' +
                   '}';
        }
    }

    public static boolean checkInternationalRecipient(String number) {
        return number.matches("\\+27\\d{9}");
    }

    public static String generateMessageID() {
        StringBuilder sb = new StringBuilder("0");
        for (int i = 0; i < 9; i++) {
            sb.append((int)(Math.random() * 10));
        }
        return sb.toString();
    }
}

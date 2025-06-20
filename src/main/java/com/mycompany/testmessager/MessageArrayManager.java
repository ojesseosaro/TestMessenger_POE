/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testmessager;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class MessageArrayManager {
    private static final String SENT_FILE = "sentMessages.json";
    private static final String STORED_FILE = "storedMessages.json";
    private static final String DISREGARDED_FILE = "disregardedMessages.json";

    private static final List<MessageChat.Message> sentMessages = new ArrayList<>();
    private static final List<MessageChat.Message> storedMessages = new ArrayList<>();
    private static final List<MessageChat.Message> disregardedMessages = new ArrayList<>();

    static {
        loadMessages(SENT_FILE, sentMessages);
        loadMessages(STORED_FILE, storedMessages);
        loadMessages(DISREGARDED_FILE, disregardedMessages);
    }

    private static void saveMessages(String filePath, List<MessageChat.Message> messages) {
        JSONArray jsonArray = new JSONArray();
        for (MessageChat.Message msg : messages) {
            JSONObject json = new JSONObject();
            json.put("messageID", msg.getMessageID());
            json.put("sender", msg.getSender());
            json.put("recipient", msg.getRecipient());
            json.put("body", msg.getMessageText());
            json.put("messageHash", msg.getHash());
            jsonArray.put(json);
        }
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(jsonArray.toString(4));
        } catch (IOException e) {
        }
    }

    private static void loadMessages(String filePath, List<MessageChat.Message> targetList) {
        File file = new File(filePath);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder jsonText = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonText.append(line);
            }

            JSONArray jsonArray = new JSONArray(jsonText.toString());
            targetList.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                MessageChat.Message msg = new MessageChat.Message(
                        obj.getString("messageID"),
                        obj.getString("sender"),
                        obj.getString("recipient"),
                        obj.getString("body")
                );
                targetList.add(msg);
            }
        } catch (Exception e) {
        }
    }

    public static void addToSentMessages(MessageChat.Message msg) {
        sentMessages.add(msg);
        saveMessages(SENT_FILE, sentMessages);
    }

    public static void addToStoredMessages(MessageChat.Message msg) {
        storedMessages.add(msg);
        saveMessages(STORED_FILE, storedMessages);
    }

    public static void addToDisregardedMessages(MessageChat.Message msg) {
        disregardedMessages.add(msg);
        saveMessages(DISREGARDED_FILE, disregardedMessages);
    }

    public static List<MessageChat.Message> getStoredMessages() {
        return new ArrayList<>(storedMessages);
    }

    public static String getSendersAndRecipientsList() {
        StringBuilder sb = new StringBuilder("Senders and Recipients:\n");
        for (MessageChat.Message msg : sentMessages) {
            sb.append("Sender: ").append(msg.getSender())
              .append(" → Recipient: ").append(msg.getRecipient()).append("\n");
        }
        return sb.toString();
    }

    public static String getLongestSentMessage() {
        if (sentMessages.isEmpty()) return "No messages found.";
        MessageChat.Message longest = sentMessages.get(0);
        for (MessageChat.Message msg : sentMessages) {
            if (msg.getMessageText().length() > longest.getMessageText().length()) {
                longest = msg;
            }
        }
        return "Longest message:\nID: " + longest.getMessageID() + "\nText: " + longest.getMessageText();
    }

    public static String searchMessageByID(String id) {
        for (MessageChat.Message msg : sentMessages) {
            if (msg.getMessageID().equals(id)) {
                return "Message Found:\nSender: " + msg.getSender() +
                        "\nRecipient: " + msg.getRecipient() +
                        "\nMessage: " + msg.getMessageText();
            }
        }
        return "Message with ID '" + id + "' not found.";
    }

    public static String searchRecipientMessages(String recipient) {
        StringBuilder sb = new StringBuilder();
        for (MessageChat.Message msg : sentMessages) {
            if (msg.getRecipient().equalsIgnoreCase(recipient)) {
                sb.append("ID: ").append(msg.getMessageID())
                  .append("\nFrom: ").append(msg.getSender())
                  .append("\nText: ").append(msg.getMessageText())
                  .append("\nHash: ").append(msg.getHash())
                  .append("\n\n");
            }
        }
        return sb.length() > 0 ? sb.toString() : "No messages found for recipient: " + recipient;
    }

    public static boolean deleteMessageByHash(String hash) {
        Iterator<MessageChat.Message> iterator = sentMessages.iterator();
        while (iterator.hasNext()) {
            MessageChat.Message msg = iterator.next();
            if (msg.getHash().equals(hash)) {
                iterator.remove();
                saveMessages(SENT_FILE, sentMessages);
                return true;
            }
        }
        return false;
    }

    public static String getSentMessagesReport() {
        StringBuilder sb = new StringBuilder("Sent Messages Report:\n\n");
        for (MessageChat.Message msg : sentMessages) {
            sb.append("ID: ").append(msg.getMessageID())
              .append("\nTo: ").append(msg.getRecipient())
              .append("\nText: ").append(msg.getMessageText())
              .append("\nHash: ").append(msg.getHash())
              .append("\n\n");
        }
        return sb.length() == 0 ? "No sent messages yet." : sb.toString();
    }

    public static String generateShortHash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 4; i++) {
                sb.append(String.format("%02x", digest[i]));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return Integer.toHexString(input.hashCode());
        }
    }
}

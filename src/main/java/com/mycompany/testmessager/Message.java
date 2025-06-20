/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.testmessager;

import javax.swing.*;
import java.util.List;

public class Message {

    public static void showTopMessage(String message) {
        JFrame frame = new JFrame();
        frame.setAlwaysOnTop(true);
        JOptionPane.showMessageDialog(frame, message);
    }

    public static String showTopInput(String prompt) {
        JFrame frame = new JFrame();
        frame.setAlwaysOnTop(true);
        return JOptionPane.showInputDialog(frame, prompt);
    }

    public static int showMessageActionDialog() {
        JFrame frame = new JFrame();
        frame.setAlwaysOnTop(true);
        String[] options = {"Send Message", "Store Message for later", "Discard Message"};
        return JOptionPane.showOptionDialog(
                frame,
                "What do you want to do with this message?",
                "Choose Action",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
    }

    public static void launchGuiMenu(String firstName, String lastName, String username, String password) {
        showTopMessage("*****Login Successful*****\nWelcome back, Quick Message " + firstName + " " + lastName + "!");

        OUTER:
        while (true) {
            String input = showTopInput(
                    "***** MAIN MENU *****\n" +
                            "1 - Send Message\n" +
                            "2 - Display STORED Messages\n" +
                            "3 - Quit\n" +
                            "4 - Messenger Menu\n\n" +
                            "Enter your choice (1-4):"
            );

            if (input == null) return;

            int choice;
            try {
                choice = Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                showTopMessage("Invalid input. Please enter a number between 1 and 4.");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    int numberOfMessages = 0;
                    while (true) {
                        String msgCountInput = showTopInput("How many messages do you want to send?");
                        if (msgCountInput == null) return;
                        try {
                            numberOfMessages = Integer.parseInt(msgCountInput);
                            if (numberOfMessages > 0) break;
                        } catch (NumberFormatException ignored) {
                        }
                        showTopMessage("Please enter a valid number.");
                    }

                    for (int i = 1; i <= numberOfMessages; i++) {
                        showTopMessage("Message " + i + " of " + numberOfMessages);

                        String sender, recipient, messageText;

                        while (true) {
                            sender = showTopInput("Enter your (sender's) number (e.g., +27123456789):");
                            if (sender == null) return;
                            if (MessageChat.checkInternationalRecipient(sender)) break;
                            showTopMessage("Invalid phone number format.");
                        }

                        while (true) {
                            recipient = showTopInput("Enter recipient number (e.g., +27123456789):");
                            if (recipient == null) return;
                            if (MessageChat.checkInternationalRecipient(recipient)) break;
                            showTopMessage("Invalid phone number format.");
                        }

                        while (true) {
                            messageText = showTopInput("Enter message (max 250 characters):");
                            if (messageText != null && !messageText.trim().isEmpty() && messageText.length() <= 250) break;
                            showTopMessage("Invalid message.");
                        }

                        String messageID = MessageChat.generateMessageID();
                        MessageChat.Message msg = new MessageChat.Message(messageID, sender, recipient, messageText);

                        int msgAction = showMessageActionDialog();
                        String actionMessage;

                        switch (msgAction) {
                            case 2 -> {
                                MessageArrayManager.addToDisregardedMessages(msg);
                                actionMessage = "Message discarded.";
                            }
                            case 1 -> {
                                MessageArrayManager.addToStoredMessages(msg);
                                actionMessage = "Message stored for later.";
                            }
                            case 0 -> {
                                MessageArrayManager.addToSentMessages(msg);
                                actionMessage = "Message sent successfully.";
                            }
                            case JOptionPane.CLOSED_OPTION -> {
                                actionMessage = "Dialog closed. No action taken.";
                            }
                            default -> {
                                actionMessage = "Unknown selection. No action taken.";
                            }
                        }

                        showTopMessage(actionMessage + "\n\n" +
                                "ID: " + msg.getMessageID() +
                                "\nRecipient: " + msg.getRecipient() +
                                "\nText: " + msg.getMessageText() +
                                "\nHash: " + msg.getHash());
                    }
                }

                case 2 -> {
                    List<MessageChat.Message> stored = MessageArrayManager.getStoredMessages();
                    StringBuilder sb = new StringBuilder("Stored Messages:\n\n");
                    if (stored.isEmpty()) {
                        sb.append("No messages stored yet.");
                    } else {
                        for (MessageChat.Message m : stored) {
                            sb.append("ID: ").append(m.getMessageID())
                              .append("\nTo: ").append(m.getRecipient())
                              .append("\nMsg: ").append(m.getMessageText())
                              .append("\nHash: ").append(m.getHash())
                              .append("\n\n");
                        }
                    }
                    showTopMessage(sb.toString());
                }

                case 3 -> {
                    showTopMessage("Exiting. Goodbye!");
                    break OUTER;
                }

                case 4 -> {
                    String[] messengerOptions = {
                            "Display Sender and Recipient",
                            "Display Longest Sent Message",
                            "Search by Message ID",
                            "Search Recipient",
                            "Delete by Message Hash",
                            "Display Full Report",
                            "Exit"
                    };

                    while (true) {
                        JComboBox<String> comboBox = new JComboBox<>(messengerOptions);
                        JPanel panel = new JPanel();
                        panel.add(new JLabel("Select Messenger Action:"));
                        panel.add(comboBox);

                        int result = JOptionPane.showConfirmDialog(null, panel, "Messenger Menu",
                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

                        if (result != JOptionPane.OK_OPTION) {
                            showTopMessage("Cancelled. Returning to main menu.");
                            break;
                        }

                        int selection = comboBox.getSelectedIndex();
                        switch (selection) {
                            case 0 -> showTopMessage(MessageArrayManager.getSendersAndRecipientsList());
                            case 1 -> showTopMessage(MessageArrayManager.getLongestSentMessage());
                            case 2 -> {
                                String id = showTopInput("Enter Message ID:");
                                if (id != null) {
                                    showTopMessage(MessageArrayManager.searchMessageByID(id));
                                }
                            }
                            case 3 -> {
                                String recipient = showTopInput("Enter recipient number:");
                                if (recipient != null) {
                                    showTopMessage(MessageArrayManager.searchRecipientMessages(recipient));
                                }
                            }
                            case 4 -> {
                                String hash = showTopInput("Enter Message Hash to delete:");
                                if (hash != null) {
                                    boolean deleted = MessageArrayManager.deleteMessageByHash(hash);
                                    showTopMessage(deleted ? "Message deleted." : "Message not found.");
                                }
                            }
                            case 5 -> showTopMessage(MessageArrayManager.getSentMessagesReport());
                            case 6 -> {
                                showTopMessage("Exiting Messenger Menu.");
                                break;
                            }
                        }
                    }
                }

                default -> showTopMessage("Invalid option. Please choose 1 to 4.");
            }
        }
    }


}





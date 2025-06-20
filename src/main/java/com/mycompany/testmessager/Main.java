/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.testmessager;

import java.util.Scanner;
import javax.swing.SwingUtilities;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String firstName = getFirstName();
        String lastName = getLastName();
        String username = getUserName();
        String password = getPassword();
        String phone = getPhoneNumber();

        if (username == null || password == null || phone == null) {
            System.out.println("Registration failed. Please try again.");
            return;
        }

        System.out.println(registerUser(username, password));

        while (true) {
            System.out.println("Enter your username to login:");
            String enteredUsername = scanner.nextLine();

            System.out.println("Enter your password to login:");
            String enteredPassword = scanner.nextLine();

            if (loginUser(username, password, enteredUsername, enteredPassword)) {
                System.out.println("*****Login Successful*****");
                System.out.println("Welcome back, " + firstName + " " + lastName + ", it is great to see you again!");

                // Show JOptionPane immediately after login success
                Message.showTopMessage("*****Login Successful*****\nWelcome back, " + firstName + " " + lastName + "!");

                // Load the GUI menu
                SwingUtilities.invokeLater(() -> {
                    Message.launchGuiMenu(firstName, lastName, username, password);
                });

                break;
            } else {
                System.out.println("Login failed. Try again.");
            }
        }
    }

    public static String getFirstName() {
        System.out.println("Enter your First Name:");
        return scanner.nextLine();
    }

    public static String getLastName() {
        System.out.println("Enter your Last Name:");
        return scanner.nextLine();
    }

    public static String getUserName() {
        while (true) {
            System.out.println("Enter your username:");
            String username = scanner.nextLine();

            if (username == null || username.isEmpty()) return null;

            if (checkUserName(username)) {
                System.out.println("Username successfully captured.");
                return username;
            } else {
                System.out.println("Username must include '_' and be ≤ 5 characters.");
            }
        }
    }

    public static String getPassword() {
        while (true) {
            System.out.println("Enter your password:");
            String password = scanner.nextLine();

            if (password == null || password.isEmpty()) return null;

            if (checkPasswordComplexity(password)) {
                System.out.println("Password successfully captured.");
                return password;
            } else {
                System.out.println("Password must be at least 8 characters long, include an uppercase letter, a number, and a special character.");
            }
        }
    }

    public static String getPhoneNumber() {
        while (true) {
            System.out.println("Enter your phone number with international code:");
            String phone = scanner.nextLine();

            if (phone == null || phone.isEmpty()) return null;

            if (checkCellPhoneNumber(phone)) {
                System.out.println("Phone number successfully added.");
                return phone;
            } else {
                System.out.println("Invalid phone number format. It must include '+' and 10–15 digits.");
            }
        }
    }

    public static boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public static boolean checkPasswordComplexity(String password) {
        return password.matches("(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}");
    }

    public static boolean checkCellPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^\\+\\d{10,15}$");
    }

    public static String registerUser(String username, String password) {
        return checkUserName(username) && checkPasswordComplexity(password)
                ? "Registration successful."
                : "Registration failed.";
    }

    public static boolean loginUser(String regUser, String regPass, String inUser, String inPass) {
        return regUser.equals(inUser) && regPass.equals(inPass);
    }
}

package edu.miracosta.cs112.capstone.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Model {
    public static final String CSV_FILE = "Users.csv";
    public static final String BINARY_FILE = "Users.dat";
    public static final String BANNED_BINARY_FILE = "BannedUsers.dat";
    public static final String POSTS_BINARY_FILE = "Posts.dat";
    public static final String COMMENTS_BINARY_FILE = "Comments.dat";

    public static boolean binaryFileHasData(String fileName) {
        File binaryFile = new File(fileName);
        return (binaryFile.exists() && binaryFile.length() > 5L);
    }

    public static ObservableList<User> populateListFromBinaryFile() {
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        File binaryFile = new File(BINARY_FILE);
        if (binaryFileHasData(BINARY_FILE)) {
            try {
                ObjectInputStream fileReader = new ObjectInputStream(new FileInputStream(binaryFile));
                User[] tempArray = (User[]) fileReader.readObject();
                allUsers.addAll(tempArray);
                fileReader.close();
            } catch (Exception e) {
                System.err.println("Error opening file: " + BINARY_FILE + " for reading.\nCaused by: " + e.getMessage());
            }
        } else {
            System.out.println("Binary File has no data.");
        }
        return allUsers;
    }

    public static boolean writeDataToBinaryFile(ObservableList<User> allUsers) {
        File binaryFile = new File(BINARY_FILE);
        try {
            ObjectOutputStream fileWriter = new ObjectOutputStream(new FileOutputStream(binaryFile));
            User[] tempArray = new User[allUsers.size()];
            allUsers.toArray(tempArray);
            fileWriter.writeObject(tempArray);
            fileWriter.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error writing binary file: " + BINARY_FILE + "\n" + e.getMessage());
            return false;
        }
    }

    public static ObservableList<User> populateListFromCSVFile() {
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        String[] data;
        String firstName, lastName, username, password, line;
        try {
            Scanner fileScanner = new Scanner(new File(CSV_FILE));

            if (fileScanner.hasNextLine()) {
                fileScanner.nextLine();
            }

            while (fileScanner.hasNextLine()) {
                line = fileScanner.nextLine().trim();

                if (line.isEmpty()) {
                    continue;
                }

                data = line.split(",", -1);

                firstName = (data.length > 0 && !data[0].isEmpty()) ? data[0] : "empty";
                lastName = (data.length > 1 && !data[1].isEmpty()) ? data[1] : "empty";
                username = (data.length > 2 && !data[2].isEmpty()) ? data[2] : "empty";
                password = (data.length > 3 && !data[3].isEmpty()) ? data[3] : "empty";

                allUsers.add(new User(firstName, lastName, username, password));
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error opening " + CSV_FILE + ": " + e.getMessage());
        }
        return allUsers;
    }

    public static ObservableList<String> populateBannedUsernamesFromBinaryFile() {
        ObservableList<String> bannedUsernames = FXCollections.observableArrayList();
        File binaryFile = new File(BANNED_BINARY_FILE);
        if (binaryFileHasData(BANNED_BINARY_FILE)) {
            try {
                ObjectInputStream fileReader = new ObjectInputStream(new FileInputStream(binaryFile));
                String[] tempArray = (String[]) fileReader.readObject();
                bannedUsernames.addAll(tempArray);
                fileReader.close();
            } catch (Exception e) {
                System.err.println("Error opening file: " + BANNED_BINARY_FILE + " for reading.\nCaused by: " + e.getMessage());
            }
        } else {
            System.out.println("Banned Users Binary File has no data.");
        }
        return bannedUsernames;
    }

    public static boolean writeBannedUsernamesToBinaryFile(ObservableList<String> bannedUsernames) {
        File binaryFile = new File(BANNED_BINARY_FILE);
        try {
            ObjectOutputStream fileWriter = new ObjectOutputStream(new FileOutputStream(binaryFile));
            String[] tempArray = new String[bannedUsernames.size()];
            bannedUsernames.toArray(tempArray);
            fileWriter.writeObject(tempArray);
            fileWriter.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error writing binary file: " + BANNED_BINARY_FILE + "\n" + e.getMessage());
            return false;
        }
    }

    public static ObservableList<Post> populatePostsFromBinaryFile() {
        ObservableList<Post> allPosts = FXCollections.observableArrayList();
        File binaryFile = new File(POSTS_BINARY_FILE);
        if (binaryFileHasData(POSTS_BINARY_FILE)) {
            try {
                ObjectInputStream fileReader = new ObjectInputStream(new FileInputStream(binaryFile));
                List<Post> tempList = (List<Post>) fileReader.readObject();
                allPosts.addAll(tempList);
                fileReader.close();
            } catch (Exception e) {
                System.err.println("Error opening file: " + POSTS_BINARY_FILE + " for reading.\nCaused by: " + e.getMessage());
            }
        } else {
            System.out.println("Posts Binary File has no data.");
        }
        return allPosts;
    }

    public static boolean writePostsToBinaryFile(ObservableList<Post> allPosts) {
        File binaryFile = new File(POSTS_BINARY_FILE);
        try {
            ObjectOutputStream fileWriter = new ObjectOutputStream(new FileOutputStream(binaryFile));
            List<Post> tempList = new ArrayList<>(allPosts);
            fileWriter.writeObject(tempList);
            fileWriter.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error writing binary file: " + POSTS_BINARY_FILE + "\n" + e.getMessage());
            return false;
        }
    }

    public static ObservableList<Comment> populateCommentsFromBinaryFile() {
        ObservableList<Comment> allComments = FXCollections.observableArrayList();
        File binaryFile = new File(COMMENTS_BINARY_FILE);
        if (binaryFileHasData(COMMENTS_BINARY_FILE)) {
            try {
                ObjectInputStream fileReader = new ObjectInputStream(new FileInputStream(binaryFile));
                List<Comment> tempList = (List<Comment>) fileReader.readObject();
                allComments.addAll(tempList);
                System.out.println("Comments successfully read from binary file: " + tempList.size() + " comments.");
                fileReader.close();
            } catch (Exception e) {
                System.err.println("Error opening file: " + COMMENTS_BINARY_FILE + " for reading.\nCaused by: " + e.getMessage());
            }
        } else {
            System.out.println("Comments Binary File has no data.");
        }
        return allComments;
    }

    public static boolean writeCommentsToBinaryFile(ObservableList<Comment> allComments) {
        File binaryFile = new File(COMMENTS_BINARY_FILE);
        try {
            ObjectOutputStream fileWriter = new ObjectOutputStream(new FileOutputStream(binaryFile));
            List<Comment> tempList = new ArrayList<>(allComments);
            fileWriter.writeObject(tempList);
            System.out.println("Comments successfully written to binary file: " + tempList.size() + " comments.");
            fileWriter.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error writing binary file: " + COMMENTS_BINARY_FILE + "\n" + e.getMessage());
            return false;
        }
    }
}




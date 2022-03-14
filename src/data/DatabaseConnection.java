package data;

import model.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class DatabaseConnection {

    public ArrayList<User> initDatabase() throws IOException {
        final ArrayList<User> users = new ArrayList<>();

        String s = null;
        File file = new File("users.txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine())
        {
            s = sc.nextLine();
            String[] sSplit = s.split(" ");
            User x = new User(sSplit[0],sSplit[1],sSplit[2]);
            users.add(x);
        }
        return users;
    }

    public boolean addUser(String username, String password, String role) throws FileNotFoundException {
        User x = new User(username,password,role);

        if(userExists(username))
            return false;

        String userString = username + " " + password + " " + role + "\n";
        try {
            Files.write(Paths.get("users.txt"), userString.getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    private static boolean userExists(String username) throws FileNotFoundException {
        final ArrayList<User> users = new ArrayList<>();
        String s = null;
        File file = new File("users.txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine())
        {
            s = sc.nextLine();
            String[] sSplit = s.split(" ");
            User x = new User(sSplit[0],sSplit[1],sSplit[2]);
            users.add(x);
        }
        for (User user : users) {
            if (Objects.equals(username, user.getUsername()))
                return true;
        }
        return false;
    }
}

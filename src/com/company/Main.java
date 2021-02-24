package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class Main {

    private static String email = "george.dumbrava@isa.utm.md";
    private static String password = "beginbegin1A+";
    private static String token;
    public static void main(String[] args) throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        ServiceAPI _service = new ServiceAPI();

        System.out.println("Welcome to Musicid!\nEnter your credentials to login");
        System.out.println("Your email:");
        //email = reader.readLine();
        boolean b1 = Pattern.matches("^[\\w-\\.]+@([\\w]+\\.)+[\\w]{2,4}$", email);
        if (b1 == true) {
            System.out.println("\nThis is a valid email");
        } else {
            System.out.println("\nThis email is unfortunately invalid :( .");
            return;
        }
        System.out.println("Your password:");
        //password = reader.readLine();
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=\\S+$).{8,}$";
        boolean b2 = Pattern.matches(regex, password);
        if (b2 == true) {
            System.out.println("\nThis is a valid password");
        } else {
            System.out.println("\nThis password is unfortunately invalid :( .");
            return;
        }
        if (b1 && b2){
            token = _service.userAuth(email, password);
        }
        System.out.println("\n\n\n\n============WELCOME TO MUSICID==============\n\n\n\n");
        boolean isWorking = true;
        while(isWorking == true){
            System.out.println("1. Get user by id\n2. Get permitted communication options\n3. Make a HEAD request\n4. Exit");
            String option = reader.readLine();
            switch (option){
                case "1" : {
                    System.out.println("Introduce the Id");
                    String id = reader.readLine();
                    _service.getUserById(id, token);
                }
                break;

                case "2":{
                    System.out.println("Permitted communication options are:");
                    _service.getOptions();
                }
                break;

                case "3":{
                    System.out.println("On THIS head request you should not get a body on response:");
                    _service.getUserHead(token);
                }
                case "4":{
                    isWorking = false;
                }
            }
        }

    }
}

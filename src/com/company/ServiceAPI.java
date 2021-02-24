package com.company;

import com.company.DTOs.LoginResponse;
import com.company.DTOs.User;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class ServiceAPI {
    private String rootURL = "https://musicidapi20210219150854.azurewebsites.net/";
    private String proxyHost = "193.8.56.119";
    private Integer proxyPort = 9183;
    private HttpClient client = HttpClient.newBuilder()
            .proxy(ProxySelector.of(new InetSocketAddress(proxyHost, proxyPort)))
            .build();

    public void getUserById(String id, String token) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rootURL + "api/Users/User/" + id))
                .header("token", token)
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        String result = response.body();
        Gson gson = new Gson();

        User user = gson.fromJson(result, User.class);
        switch (user.Specialization) {
            case "1":
                user.Specialization = "Musician";
                break;
            case "2":
                user.Specialization = "Writer";
                break;
            case "3":
                user.Specialization = "Producer";
                break;
        }
        System.out.println("\tYou got " + user.FullName + "\nHe\\She is " + user.Age + " years old and he is a " + user.Specialization + ".\n");
    }

    public String userAuth(String email, String password) throws IOException, InterruptedException {

        String jsonBody = "{ 'Email':'" + email + "', 'Password':'" + password + "' }";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rootURL + "api/Login/UserAuth"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        String result = response.body();
        Gson gson = new Gson();

        LoginResponse lr = gson.fromJson(result, LoginResponse.class);

        return lr.Message;
    }

    public void getUserHead(String token) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rootURL + "api/Users/User/"))
                .header("token", token)
                .method("HEAD", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response);
    }

    public void getOptions() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(rootURL + "api/Users/Options"))
                .method("OPTIONS", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        Map<String, List<String>> map = response.headers().map();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            if (entry.getKey().contains("access")) {
                System.out.println("Key : " + entry.getKey() +
                        " ,Value : " + entry.getValue());
            }
        }
    }
}

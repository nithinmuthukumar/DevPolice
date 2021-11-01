package com.github.nithinmuthukumar;

import com.google.gson.JsonObject;
import com.google.gson.Gson;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;

public class RequestHandler {
    static HttpClient client=HttpClient.create();


    static String graphUrl = "http://159.89.116.36/graphql";
    static Gson gson = new Gson();


    public static Project[] getHackathonsSubmissions(String hackathonUrl){
        String json = String.format("{\"hackathonUrl\":\"%s\"}",hackathonUrl);
        String response = sendRequest(json);

        JsonObject jsonObject = gson.fromJson(response,JsonObject.class);
        Project[] projects = gson.fromJson(jsonObject.get("projects"),Project[].class);
        return projects;
    }

    public static Project getProject(String projectUrl) {
        String json = String.format("{\"query\": \"{ project(projectUrl:\\\"https://devpost.com/software/shopadvisr\\\"){name,tagLine,projectUrl,imageUrl,externalLinks,hackathons{name,hackathonUrl},members{name,username,profileUrl}}}\"}",projectUrl);
        String response = sendRequest(json);
        JsonObject jsonObject = gson.fromJson(response,JsonObject.class);

        Project project = gson.fromJson(jsonObject.getAsJsonObject("data").get("project"),Project.class);
        return project;

    }
    public static Member getMember(String username){
        String json = String.format("{\"query\": \"{ profile(username:\\\"%s\\\"){name,username,profileUrl,imageUrl,externalLinks}}\"}",username);
        String response = sendRequest(json);
        JsonObject jsonObject = gson.fromJson(response,JsonObject.class);
        Member member = gson.fromJson(jsonObject.getAsJsonObject("data").get("profile"),Member.class);
        return member;

    }
    public static String sendRequest(String json){
        return client.headers(h ->h.add("Content-Type","application/json")).post().uri(graphUrl)
                .send(ByteBufFlux.fromString(Mono.just(json)))
                .responseSingle((status,res)->res.asString())
                .block();
    }
}

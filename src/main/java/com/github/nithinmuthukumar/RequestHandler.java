package com.github.nithinmuthukumar;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.Gson;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;

import java.util.ArrayList;
import java.util.List;

public class RequestHandler {
    static HttpClient client=HttpClient.create();


    static String devpostAPIUrl = "http://159.89.116.36/graphql";
    static String githubAPIUrl = "https://api.github.com/graphql";
    static String gitToken = "";
    static Gson gson = new Gson();


    public static Project[] getHackathonsSubmissions(String hackathonUrl){
        String json = String.format("{\"hackathonUrl\":\"%s\"}",hackathonUrl);
        String response = sendRequest(json,devpostAPIUrl);

        JsonObject jsonObject = gson.fromJson(response,JsonObject.class);
        Project[] projects = gson.fromJson(jsonObject.get("projects"),Project[].class);
        return projects;
    }

    public static Project getProject(String projectUrl) {
        String json = String.format("{\"query\": \"{ project(projectUrl:\\\"%s\\\"){name,tagLine,projectUrl,imageUrl,externalLinks,hackathons{name,hackathonUrl},members{name,username,profileUrl}}}\"}",projectUrl);
        String response = sendRequest(json,devpostAPIUrl);
        JsonObject jsonObject = gson.fromJson(response,JsonObject.class);

        Project project = gson.fromJson(jsonObject.getAsJsonObject("data").get("project"),Project.class);
        return project;

    }
    public static Hackathon getHackathon(String hackathonUrl){
        String json = String.format("{\"query\": \"{ hackathon(hackathonUrl:\\\"%s\\\"){name,registrationsCount,submissionPeriod,hackathonUrl,imageUrl,winnersAnnounced}}\"}"
                ,hackathonUrl);
        String response = sendRequest(json,devpostAPIUrl);
        JsonObject jsonObject = gson.fromJson(response,JsonObject.class);
        Hackathon hackathon = gson.fromJson(jsonObject.getAsJsonObject("data").get("hackathon"),Hackathon.class);
        return hackathon;
    }
    public static Member getMember(String username){
        String json = String.format("{\"query\": \"{ profile(username:\\\"%s\\\"){name,username,profileUrl,imageUrl,externalLinks}}\"}"
                ,username);
        String response = sendRequest(json,devpostAPIUrl);
        JsonObject jsonObject = gson.fromJson(response,JsonObject.class);
        Member member = gson.fromJson(jsonObject.getAsJsonObject("data").get("profile"),Member.class);
        return member;

    }
    public static Repository getRepository(String projectGit){
        List<String> ownerName = DevPolice.getProjectGitInfo(projectGit);
        System.out.println(ownerName);

        String json = String.format("{\"query\":\"{repository(owner:\\\"%s\\\",name:\\\"%s\\\"){collaborators{nodes{login}},name,createdAt,pushedAt,projectsUrl}}\"}"
                ,ownerName.get(1),ownerName.get(0));
//        System.out.println(json);

        String response = sendRequest(json,githubAPIUrl);
        JsonObject jsonObject = gson.fromJson(response,JsonObject.class);
        Repository repository= gson.fromJson(jsonObject.getAsJsonObject("data").get("repository"),Repository.class);
        jsonObject.getAsJsonObject("data").getAsJsonObject("repository");

        for(JsonElement j: jsonObject.getAsJsonObject("data").getAsJsonObject("repository").getAsJsonObject("collaborators").getAsJsonArray("nodes")){
            repository.addContributor(j.getAsJsonObject().get("login").toString());
        }
        return repository;
        //System.out.println(response);

    }
    public static String sendRequest(String json,String url){
        return client.headers(h ->h.add("Content-Type","application/json").add("Authorization","Bearer "+gitToken)).post().uri(url)
                .send(ByteBufFlux.fromString(Mono.just(json)))
                .responseSingle((status,res)->res.asString())
                .block();
    }
}

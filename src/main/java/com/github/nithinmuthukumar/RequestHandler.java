package com.github.nithinmuthukumar;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;

public class RequestHandler {
    static HttpClient client=HttpClient.create();


    static String baseUrl = "http://159.89.116.36";
    static Gson gson = new Gson();


    public static Hackathon[] getHackathons(int amount, String categories){

        String response = client.post().uri(baseUrl+"/hackathons/?amount="+amount)
//                .send()
                .responseSingle((status,res)->res.asString())
                .block();




        JsonObject jsonObject = gson.fromJson(response,JsonObject.class);


        Hackathon[] hackathons = gson.fromJson(jsonObject.get("hackathons"),Hackathon[].class);
        return hackathons;


    }
    public static Project[] getHackathonsProjects(String hackathonUrl){
        String json = String.format("{\"hackathonUrl\":\"%s\"}",hackathonUrl);

        String response = client.headers(h ->h.add("Content-Type","application/json")).post().uri(baseUrl+"/hackathons/projects/")
                .send(ByteBufFlux.fromString(Mono.just(json)))
                .responseSingle((status,res)->res.asString())
                .block();

        JsonObject jsonObject = gson.fromJson(response,JsonObject.class);
        Project[] projects = gson.fromJson(jsonObject.get("projects"),Project[].class);
        return projects;
    }
}

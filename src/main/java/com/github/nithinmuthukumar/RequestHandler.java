package com.github.nithinmuthukumar;

import com.google.gson.JsonObject;
import com.google.gson.Gson;
import reactor.netty.http.client.HttpClient;

public class RequestHandler {
    static HttpClient client=HttpClient.create();

    public static Hackathon[] getHackathons(int amount, String categories){
        Gson gson = new Gson();
        System.out.println(gson);

        String response = client.post().uri("http://159.89.116.36/hackathons/?amount="+amount)
//                .send()
                .responseSingle((status,res)->res.asString())
                .block();


        JsonObject jsonObject = gson.fromJson(response,JsonObject.class);


        Hackathon[] hackathons = gson.fromJson(jsonObject.get("hackathons"),Hackathon[].class);
        return hackathons;


    }

}

package com.github.nithinmuthukumar;

import java.util.ArrayList;

public class Member {
    private String name;
    private String username;
    private String profileUrl;
    private String imageUrl;
    private ArrayList<String> externalLinks;

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ArrayList<String> getExternalLinks() {
        if(externalLinks==null){
            Member fullMember = RequestHandler.getMember(username);
            this.externalLinks = fullMember.externalLinks;

        }
        return this.externalLinks;
    }

    @Override
    public String toString() {
        return "Member{" +
                "name='" + name + '\'' +
                ", userName='" + username + '\'' +
                ", profileUrl='" + profileUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", externalLinks='" + externalLinks + '\'' +
                '}';
    }
}

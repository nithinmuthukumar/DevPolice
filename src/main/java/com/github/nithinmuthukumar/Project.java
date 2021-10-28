package com.github.nithinmuthukumar;

import java.util.ArrayList;

public class Project {
    String name;
    String tagLine;
    int likes;
    int commentCount;
    String projectUrl;
    String imageUrl;
    boolean isWinner;
    ArrayList<Member> members;
    ArrayList<String> externalLinks;

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", tagLine='" + tagLine + '\'' +
                ", likes=" + likes +
                ", commentCount=" + commentCount +
                ", projectUrl='" + projectUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", isWinner=" + isWinner +
                ", members=" + members +
                ", externalLinks=" + externalLinks +
                '}';
    }
}

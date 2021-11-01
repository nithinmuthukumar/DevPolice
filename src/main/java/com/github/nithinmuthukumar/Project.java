package com.github.nithinmuthukumar;

import java.util.ArrayList;

public class Project {
    private String name;
    private String tagLine;
    private String projectUrl;
    private String imageUrl;
    private ArrayList<Member> members;
    private ArrayList<String> externalLinks;
    private ArrayList<String> submittedTo;

    public String getName() {
        return name;
    }

    public String getTagLine() {
        return tagLine;
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    public ArrayList<String> getExternalLinks() {
        return externalLinks;
    }

    public ArrayList<String> getSubmittedTo() {
        return submittedTo;
    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", tagLine='" + tagLine + '\'' +
                ", projectUrl='" + projectUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", members=" + members +
                ", externalLinks=" + externalLinks +
                ", submittedTo=" + submittedTo +
                '}';
    }
}
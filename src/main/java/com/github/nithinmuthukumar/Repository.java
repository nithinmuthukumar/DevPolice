package com.github.nithinmuthukumar;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class Repository {

    private List<String> contributors = new ArrayList<>();
    private String createdAt;
    private String pushedAt;
    private String name;
    private String projectsUrl;


    public void addContributor(String contributor){
        contributors.add(contributor);

    }

    public List<String> getContributors() {
        return contributors;
    }

    public LocalDateTime getCreated() {
        return OffsetDateTime.parse(createdAt).toLocalDateTime();
    }
    public LocalDateTime getLastUpdated() {
       return OffsetDateTime.parse(pushedAt).toLocalDateTime();
    }

    @Override
    public String toString() {
        return "Repository{" +
                "contributors=" + contributors +
                ", createdAt='" + createdAt + '\'' +
                ", pushedAt='" + pushedAt + '\'' +
                ", name='" + name + '\'' +
                ", projectsUrl='" + projectsUrl + '\'' +
                '}';
    }
}

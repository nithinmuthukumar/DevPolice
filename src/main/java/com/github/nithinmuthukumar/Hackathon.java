package com.github.nithinmuthukumar;




import java.util.ArrayList;
import java.util.Currency;

public class Hackathon {

    String name;    //name of hackathon

    int registrationsCount;
    String submissionPeriod;
    String hackathonUrl;
    String imageUrl;
    boolean winnersAnnounced;

    public Hackathon() {


    }

    @Override
    public String toString() {
        return "Hackathon{" +
                "name='" + name + '\'' +
                ", registrationsCount=" + registrationsCount +
                ", submissionPeriod='" + submissionPeriod + '\'' +
                ", hackathonUrl='" + hackathonUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", winnersAnnounced=" + winnersAnnounced +
                '}';
    }
}
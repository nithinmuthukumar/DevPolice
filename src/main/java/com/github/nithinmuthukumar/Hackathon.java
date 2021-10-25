package com.github.nithinmuthukumar;




import java.util.ArrayList;
import java.util.Currency;

public class Hackathon {

    String name;    //name of hackathon

    String location;    // Whether hackathon is in person or online
    String openTo;  // If the hackathon is public or private
    String organizationName;
    String prizeAmount;
    int registrationsCount;
    String submissionPeriod;
    String hackathonUrl;
    String imageUrl;
    boolean winnersAnnounced;
    String startSubmissionUrl;
    String submissionGalleryUrl;
    ArrayList<String> themes;
    public Hackathon(){



    }


    @Override
    public String toString() {
        return "Hackathon{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", openTo='" + openTo + '\'' +
                ", organizationName='" + organizationName + '\'' +
                ", prizeAmount='" + prizeAmount + '\'' +
                ", registrationsCount=" + registrationsCount +
                ", submissionPeriod='" + submissionPeriod + '\'' +
                ", hackathonUrl='" + hackathonUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", winnersAnnounced=" + winnersAnnounced +
                ", themes=" + themes +
                '}';
    }
}

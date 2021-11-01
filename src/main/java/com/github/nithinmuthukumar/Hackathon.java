package com.github.nithinmuthukumar;




import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Hackathon {

    private String name;    //name of hackathon
    private int registrationsCount;
    private String submissionPeriod;
    private String hackathonUrl;
    private String imageUrl;
    private boolean winnersAnnounced;

    public Hackathon() {


    }

    public String getName() {
        return name;
    }

    public int getRegistrationsCount() {
        return registrationsCount;
    }

    public String getSubmissionPeriod() {
        return submissionPeriod;
    }

    public String getHackathonUrl() {
        return hackathonUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isWinnersAnnounced() {
        return winnersAnnounced;
    }

    public List<LocalDateTime> submissionPeriodAsDates(){
        String datePattern = "MMM dd yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        ArrayList<LocalDateTime> dates = new ArrayList<>();
        String[] splitByComma = submissionPeriod.split(",");
        String year = splitByComma[1];
        String[] splitByDash = splitByComma[0].split("-");
        String from = splitByDash[0].trim();
        String to = splitByDash[1].trim();
        if(to.split(" ").length==1){
            to = from.split(" ")[0]+" "+to;
        }
        dates.add(LocalDate.parse(from+year,formatter).atStartOfDay());
        dates.add(LocalDate.parse(to+year,formatter).atTime(23,59));

        return dates;

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
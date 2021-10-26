package com.github.nithinmuthukumar;



import org.junit.jupiter.api.Test;


class RequestHandlerTest {

    @Test
    void getHackathons() {
        Hackathon[] hackathons= RequestHandler.getHackathons(1,"jj");
        System.out.println(hackathons[0]);





    }



    @Test
    void getHackathonsProjects() {
        Project[] projects = RequestHandler.getHackathonsProjects("https://hack-the-valley-v.devpost.com/");
        System.out.println(projects[0]);
    }
}
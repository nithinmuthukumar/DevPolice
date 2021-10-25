package com.github.nithinmuthukumar;



import org.junit.jupiter.api.Test;


class RequestHandlerTest {

    @Test
    void getHackathons() {
        Hackathon[] hackathons= RequestHandler.getHackathons(1,"jj");
        System.out.println(hackathons[0]);





    }
}
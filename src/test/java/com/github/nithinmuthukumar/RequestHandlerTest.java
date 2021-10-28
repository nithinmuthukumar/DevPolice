package com.github.nithinmuthukumar;



import org.junit.jupiter.api.Test;


class RequestHandlerTest {

    @Test
    void testGetHackathons() {
        Hackathon[] hackathons= RequestHandler.getHackathons(1,"jj");
        System.out.println(hackathons[0]);





    }



    @Test
    void testGetHackathonsProjects() {
        Project[] projects = RequestHandler.getHackathonsSubmissions("https://hack-the-valley-v.devpost.com/");
        System.out.println(projects[0]);
    }



    @Test
    void testGetHackathonsSubmissions() {
    }

    @Test
    void testGetProject() {
        Project project= RequestHandler.getProject("https://devpost.com/software/shopadvisr");
        System.out.println(project);
    }

    @Test
    void testSendRequest() {
    }

    @Test
    void testGetHackathons1() {
    }




    @Test
    void testGetMember() {
        Member m = RequestHandler.getMember("fairnightzz");
        System.out.println(m);
    }


}
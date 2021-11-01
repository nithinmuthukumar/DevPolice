package com.github.nithinmuthukumar;



import org.junit.jupiter.api.Test;


class RequestHandlerTest {

    @Test
    void testGetHackathons() {
//        Hackathon[] hackathons= RequestHandler.getHackathons(1,"jj");
//        System.out.println(hackathons[0]);






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
    void testGetHackathon() {
        Hackathon hackathon = RequestHandler.getHackathon("https://frosthack.devpost.com");
        System.out.println(hackathon.submissionPeriodAsDates());
    }




    @Test
    void testGetMember() {
        Member m = RequestHandler.getMember("fairnightzz");
        System.out.println(m);
    }


    @Test
    void getHackathonsSubmissions() {
    }

    @Test
    void getProject() {
    }

    @Test
    void getHackathon() {
    }

    @Test
    void getMember() {
    }

    @Test
    void getGithubProjectData() {
        Repository r = RequestHandler.getRepository("https://github.com/landofmesa/mesa");
        System.out.println(r);
        System.out.println(r.getCreated());
        System.out.println(r.getLastUpdated());
    }

    @Test
    void sendRequest() {
    }
}
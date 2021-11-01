package com.github.nithinmuthukumar;


import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import discord4j.core.object.component.Button;
import discord4j.core.object.component.SelectMenu;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.core.spec.InteractionApplicationCommandCallbackReplyMono;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class DevPolice {

    public static void main(String[] args) {

        final GatewayDiscordClient client = DiscordClientBuilder.create("").build()
                .login()
                .block();
        try {
            new GlobalCommandRegistrar(client.getRestClient()).registerCommands();
        } catch (Exception e) {
            //Handle exception
            System.out.println(e);
        }
        client.on(ReadyEvent.class,readyEvent -> Mono.fromRunnable(()->{
            final User self = readyEvent.getSelf();
            System.out.printf("Logged in as %s#%s%n", self.getUsername(), self.getDiscriminator());
        })).subscribe();
        client.on(ChatInputInteractionEvent.class, event -> {


            System.out.println(event.getCommandName());

            switch (event.getCommandName()) {
                case "project":
                    System.out.println("projectcheck");
                    event.deferReply().block();
                    projectCheck(event);
                    return projectCheck(event);


                    //return event.reply("hi");

                    //event.editReply("ahi");
                case "hackathon":
                    break;
                    //return hackathonSubmissionsCheck(event);

                case "user":
                    break;
                default:
                    break;
                    //return event.reply("The command doesn't seem to exist").withEphemeral(true);
            }
            return event.deferReply().then(event.createFollowup());



        }).subscribe();
        client.onDisconnect().block();

    }

    private static InteractionApplicationCommandCallbackReplyMono hackathonSubmissionsCheck(ChatInputInteractionEvent event) {
        String hackathonUrl = event.getOption("Hackathon Url")
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asString).get();
        Project[] projects = RequestHandler.getHackathonsSubmissions(hackathonUrl);
        System.out.println(Arrays.asList(projects));
        return event.reply("HI");


    }

    private static Mono<Message> projectCheck(ChatInputInteractionEvent event){

        String projectUrl = event.getOption("url")
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asString).get();
        System.out.println("before request "+projectUrl);

        Project project = RequestHandler.getProject(projectUrl);

        List<Repository> repositories = project.getExternalLinks().stream()
                .filter(s->s.contains("github.com"))
                .map(RequestHandler::getRepository)
                .collect(Collectors.toList());

        List<List<String>> mu = new ArrayList<>();
        for(Member m:project.getMembers()){
            mu.add(m.getExternalLinks().stream().filter(s->s.contains("github.com")).map(s->{
                String[] urlParts = s.split("/");
                return urlParts[urlParts.length-1];
            }).collect(Collectors.toList()));
        }
        List<String> memberUsernames = mu.stream().flatMap(List::stream).collect(Collectors.toList());


        List<Hackathon> hackathons = project.getHackathons().stream().map(h->RequestHandler.getHackathon(h.getHackathonUrl())).collect(Collectors.toList());
        //TODO check if commits happened within submission period
        for(Hackathon hackathon:hackathons){
            List<LocalDate> submissionPeriod = hackathon.submissionPeriodAsDates();
            for(Repository r:repositories){
                r.getCreated();
                r.getLastUpdated();
            }

        }
        for(Repository repository:repositories){
            //TODO do something with checks
            if(repository.getContributors().size()==memberUsernames.size()){

            }
            for(String username:memberUsernames){
                if(repository.getContributors().contains(username));
            }



        }



        System.out.println("This took awhile");
        return event.editReply(Arrays.toString(project.getExternalLinks().get(0).split("/")));
    }
    public static List<String> getProjectGitInfo(String projectGit){
        String[] urlParts= projectGit.split("/");
        List<String> ownerName= new ArrayList<String>();
        ownerName.add(urlParts[urlParts.length-1]);
        ownerName.add(urlParts[urlParts.length-2]);
        return ownerName;


    }




}

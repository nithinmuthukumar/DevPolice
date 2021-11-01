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
import java.time.LocalDateTime;
import java.util.*;
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
                //TODO add way more options to project such as start time and end time, should check if pushed after
                case "project":
                    System.out.println("projectcheck");
                    event.deferReply().block();

                    return executeProject(event);






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

    private static Mono<Message> executeProject(ChatInputInteractionEvent event){

        String projectUrl = event.getOption("url")
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asString).get();

        Project project = RequestHandler.getProject(projectUrl);
        List<Repository> repositories = new ArrayList<>();

        repositories = project.getExternalLinks().stream()
                .filter(s->s.contains("github.com"))
                .map(RequestHandler::getRepository)
                .collect(Collectors.toList());








        Set<String> memberViolators = contributorsCheck(project,repositories);
        List<Repository> submissionViolators = timePeriodCheck(project,repositories);
        System.out.println(memberViolators);
        System.out.println(submissionViolators);
        if(memberViolators.size()>0||submissionViolators.size()>0){
            return event.editReply("There is a violation");

        }
        return event.editReply("No violations");
    }
    public static Set<String> contributorsCheck(Project project,List<Repository> repositories){
        List<List<String>> mu = new ArrayList<>();
        for(Member m:project.getMembers()){
            mu.add(m.getExternalLinks().stream().filter(s->s.contains("github.com")).map(s->{
                String[] urlParts = s.split("/");
                return urlParts[urlParts.length-1];
            }).collect(Collectors.toList()));
        }

        Set<String> memberUsernames = mu.stream().flatMap(List::stream).collect(Collectors.toSet());
        Set<String> contributors = repositories.stream().map(Repository::getContributors).flatMap(List::stream).collect(Collectors.toSet());
        Set<String> notMember = new HashSet<>();
        for(String c:contributors){
            if(!memberUsernames.contains(c)){
                notMember.add(c);
            }
        }

        return notMember;

    }
    public static List<Repository> timePeriodCheck(Project project, List<Repository> repositories){
        List<Hackathon> hackathons = project.getHackathons().stream()
                .map(h->RequestHandler.getHackathon(h.getHackathonUrl())).collect(Collectors.toList());
        List<Repository> dateViolators = new ArrayList<>();
        for(Hackathon hackathon:hackathons){
            try{
            List<LocalDateTime> submissionPeriod = hackathon.submissionPeriodAsDates();
                for (Repository r : repositories) {
                    if ((r.getCreated().isBefore(submissionPeriod.get(0)))
                            || (r.getLastUpdated().isAfter(submissionPeriod.get(1)))) {

                        dateViolators.add(r);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return dateViolators;
    }
    public static List<String> getProjectGitInfo(String projectGit){
        String[] urlParts= projectGit.split("/");
        List<String> ownerName= new ArrayList<String>();
        ownerName.add(urlParts[urlParts.length-1]);
        ownerName.add(urlParts[urlParts.length-2]);
        return ownerName;


    }




}

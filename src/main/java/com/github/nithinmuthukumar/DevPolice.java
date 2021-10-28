package com.github.nithinmuthukumar;


import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;
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
import discord4j.core.spec.InteractionFollowupCreateMono;
import reactor.core.publisher.Mono;


import java.util.ArrayList;
import java.util.Arrays;


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

                    event.deferReply().then(projectCheck(event));
                    break;



                case "hackathon":
                    return hackathonSubmissionsCheck(event);
                case "user":
                    break;
                default:
                    return event.reply("The command doesn't seem to exist").withEphemeral(true);
            }


            return Mono.empty();

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
        System.out.println(project);
        for(Member m:project.members){
            System.out.println(m.getExternalLinks().toString());

        }

        ArrayList<SelectMenu.Option> options = new ArrayList<>();

        EmbedCreateSpec.Builder embed = EmbedCreateSpec.builder().title("hackathon");
        ArrayList<Button> buttons = new ArrayList<>();
//        for (int h=0;h<hackathons.length;h++) {
//            Hackathon hackathon = hackathons[h];
//
//            options.add(SelectMenu.Option.of(hackathon.name,hackathon.prizeAmount));
//            embed.addField((h+1)+". "+hackathon.name,String.format("Prize: %s\nOrganization: %s\n%d participants",
//                    hackathon.prizeAmount,hackathon.organizationName,hackathon.registrationsCount),false);
//            buttons.add(Button.primary("hackathon"+h,Integer.toString(h+1)));
//        }
//        SelectMenu selectMenu = SelectMenu.of("custom-id",options);
//
//        return event.reply().withEmbeds(embed.build()).withComponents(ActionRow.of(selectMenu),ActionRow.of(buttons));
        System.out.println("This took awhile");
        return event.createFollowup("This took awhile");

    }


}

package com.github.nithinmuthukumar;


import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import discord4j.core.object.component.ActionRow;
import discord4j.core.object.component.Button;
import discord4j.core.object.component.SelectMenu;
import discord4j.core.object.entity.User;
import reactor.core.publisher.Mono;


import java.util.ArrayList;
import java.util.Arrays;


public class DevpostBot {

    public static void main(String[] args) {

        final GatewayDiscordClient client = DiscordClientBuilder.create(System.getenv("token")).build()
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


            if(event.getCommandName().equals("hackathons")){


                int amount = event.getOption("amount")
                        .flatMap(ApplicationCommandInteractionOption::getValue)
                        .map(ApplicationCommandInteractionOptionValue::asLong).get().intValue();
                Hackathon[] hackathons = RequestHandler.getHackathons(amount,"j");
                StringBuilder hackathonNames = new StringBuilder();
                for(int i = 0;i<amount;i++){
                    hackathonNames.append(hackathons[i].name);
                }
                System.out.println(hackathonNames.toString());
                Button button = Button.link(hackathons[0].hackathonUrl,"GO");
                ArrayList<SelectMenu.Option> options = new ArrayList<>();
                for (int i = 0; i < amount; i++) {
                    options.add(SelectMenu.Option.of(hackathons[i].name,hackathons[i].prizeAmount));

                }
                SelectMenu selectMenu = SelectMenu.of("custom-id",options);



                return event.reply().withComponents(ActionRow.of(selectMenu),ActionRow.of(button));
            }
            return Mono.empty();

        }).subscribe();
        client.onDisconnect().block();

    }
}

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
import discord4j.core.spec.EmbedCreateSpec;
import org.checkerframework.checker.units.qual.A;
import reactor.core.publisher.Mono;


import java.util.ArrayList;
import java.util.Arrays;


public class DevpostBot {

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


            if(event.getCommandName().equals("hackathons")){


                int amount = event.getOption("amount")
                        .flatMap(ApplicationCommandInteractionOption::getValue)
                        .map(ApplicationCommandInteractionOptionValue::asLong).get().intValue();
                Hackathon[] hackathons = RequestHandler.getHackathons(amount,"j");

                ArrayList<SelectMenu.Option> options = new ArrayList<>();

                EmbedCreateSpec.Builder embed = EmbedCreateSpec.builder().title("hackathon");
                ArrayList<Button> buttons = new ArrayList<>();
                for (int h=0;h<hackathons.length;h++) {
                    Hackathon hackathon = hackathons[h];

                    options.add(SelectMenu.Option.of(hackathon.name,hackathon.prizeAmount));
                    embed.addField((h+1)+". "+hackathon.name,String.format("Prize: %s\nOrganization: %s\n%d participants",
                            hackathon.prizeAmount,hackathon.organizationName,hackathon.registrationsCount),false);
                    buttons.add(Button.primary("hackathon"+h,Integer.toString(h+1)));
                }
                SelectMenu selectMenu = SelectMenu.of("custom-id",options);

                return event.reply().withEmbeds(embed.build()).withComponents(ActionRow.of(selectMenu),ActionRow.of(buttons));
            }
            return Mono.empty();

        }).subscribe();
        client.onDisconnect().block();

    }
}

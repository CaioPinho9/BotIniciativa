import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;

import java.lang.reflect.Array;
import java.util.*;

import static com.mojang.brigadier.arguments.StringArgumentType.*;
import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;
import static com.mojang.brigadier.builder.RequiredArgumentBuilder.argument;

public class Octavius {

    public static void main(String[] args) {
        final String token = System.getenv("DISCORD_TOKEN");
        final DiscordClient client = DiscordClient.create(token);
        final GatewayDiscordClient gateway = client.login().block();

        gateway.on(MessageCreateEvent.class).subscribe(event->{
            if (event.getMember().get().getId().asLong() != gateway.getSelfId().asLong() &&  event.getMessage().getContent().startsWith("d.")) {
                final Message message = event.getMessage();
                CommandDispatcher<Object> dispatcher = new CommandDispatcher<>();

                dispatcher.register(
                        literal("d.hey").executes(context -> {
                            message.getChannel().subscribe(messageChannel -> {
                                messageChannel.createMessage("sua mae").subscribe();
                            });
                            return 1;
                        })
                );

                dispatcher.register(
                        literal("").executes(context -> {
                            message.getChannel().subscribe(messageChannel -> {
                                messageChannel.createMessage("").subscribe();
                            });
                            return 1;
                        })
                );

                dispatcher.register(
                        literal("d.iniciativa").then(argument("iniciativa", greedyString()).executes(context -> {
                                    String iniciativa = getString(context, "iniciativa");
                                    String[] pessoas = iniciativa.split(", ");
                                    String[] iniciativaList = new String[40];
                                    int valorIniciativa = 0;
                                    int quantidadePessoas = pessoas.length;

                                     int i = 0;
                                     int u = 0;
                                     Integer[] checarIniciativa = new Integer[8];
                                     Integer[] checarDestreza = new Integer[8];
                                     String[] checarPersonagem = new String[8];

                                     while (i<quantidadePessoas) {
                                         Random random = new Random();
                                         int dado = 0;
                                         valorIniciativa = 0;
                                         int bonus = 0;

                                         bonus = Integer.parseInt(pessoas[i+1]);

                                         String personagem = pessoas[i];

                                         dado = random.nextInt(19)+1;

                                         valorIniciativa = dado+bonus;

                                         checarIniciativa[u] = valorIniciativa;
                                         checarDestreza[u] = bonus;
                                         checarPersonagem[u] = personagem;
                                         String reserva;

                                         int finalDado = dado;
                                         int finalValorIniciativa = valorIniciativa;
                                         int finalBonus = bonus;

                                         if (bonus>=0) {
                                             if (dado != 1 && dado != 20) {

                                                 message.getChannel().subscribe(messageChannel -> {
                                                     messageChannel.createMessage(personagem + ": ` " + finalValorIniciativa + " `  <--  [" + finalDado + "] d20+" + finalBonus).subscribe();
                                                 });
                                             } else {
                                                 message.getChannel().subscribe(messageChannel -> {
                                                     messageChannel.createMessage(personagem + ": ` " + finalValorIniciativa + " `  <--  [**" + finalDado + "**] d20+" + finalBonus).subscribe();
                                                 });
                                             }
                                         }else{
                                             if (dado != 1 && dado != 20) {
                                                 message.getChannel().subscribe(messageChannel -> {
                                                     messageChannel.createMessage(personagem + ": ` " + finalValorIniciativa + " `  <--  [" + finalDado + "] d20" + finalBonus).subscribe();
                                                 });
                                             } else {
                                                 message.getChannel().subscribe(messageChannel -> {
                                                     messageChannel.createMessage(personagem + ": ` " + finalValorIniciativa + " `  <--  [**" + finalDado + "**] d20" + finalBonus).subscribe();
                                                 });
                                             }
                                         }

                                         try {
                                             if (iniciativaList[valorIniciativa] == null) {
                                                 iniciativaList[valorIniciativa] = personagem;
                                             } else {
                                                 for (int y = 0; y < checarPersonagem.length; y++) {
                                                     if (checarPersonagem[y].equals(iniciativaList[valorIniciativa])) {
                                                         if (checarIniciativa[y] > checarIniciativa[u]) {
                                                             if (iniciativaList[valorIniciativa - 1] == null) {
                                                                 iniciativaList[valorIniciativa - 1] = personagem;
                                                             } else {
                                                                 for (int t = 0; t < checarPersonagem.length; t++) {
                                                                     if (checarPersonagem[t].equals(checarPersonagem[valorIniciativa - 1])) {
                                                                         if (checarIniciativa[t] >= checarIniciativa[u]) {
                                                                             iniciativaList[valorIniciativa - 2] = personagem;
                                                                         } else {
                                                                             reserva = iniciativaList[valorIniciativa - 1];
                                                                             iniciativaList[valorIniciativa - 1] = personagem;
                                                                             iniciativaList[valorIniciativa - 2] = reserva;
                                                                         }
                                                                         break;
                                                                     }
                                                                 }
                                                             }
                                                         } else if (checarIniciativa[y] == checarIniciativa[u]) {
                                                             if (checarDestreza[y] < checarDestreza[u]) {
                                                                 reserva = iniciativaList[valorIniciativa];
                                                                 iniciativaList[valorIniciativa] = personagem;
                                                                 if (iniciativaList[valorIniciativa - 1] == null) {
                                                                     iniciativaList[valorIniciativa - 1] = reserva;
                                                                 } else {
                                                                     for (int t = 0; t < checarPersonagem.length; t++) {
                                                                         if (checarPersonagem[t].equals(checarPersonagem[valorIniciativa - 1])) {
                                                                             if (checarIniciativa[t] >= checarIniciativa[u]) {
                                                                                 iniciativaList[valorIniciativa - 2] = reserva;
                                                                             } else {
                                                                                 String reserva1 = iniciativaList[valorIniciativa - 1];
                                                                                 iniciativaList[valorIniciativa - 1] = reserva;
                                                                                 iniciativaList[valorIniciativa - 2] = reserva1;
                                                                             }
                                                                             break;
                                                                         }
                                                                     }
                                                                 }
                                                             } else if (iniciativaList[valorIniciativa - 1] == null) {
                                                                 iniciativaList[valorIniciativa - 1] = personagem;
                                                             } else {
                                                                 for (int t = 0; t < checarPersonagem.length; t++) {
                                                                     if (checarPersonagem[t].equals(checarPersonagem[valorIniciativa - 1])) {
                                                                         if (checarIniciativa[t] >= checarIniciativa[u]) {
                                                                             iniciativaList[valorIniciativa - 2] = personagem;
                                                                         } else {
                                                                             reserva = iniciativaList[valorIniciativa - 1];
                                                                             iniciativaList[valorIniciativa - 1] = personagem;
                                                                             iniciativaList[valorIniciativa - 2] = reserva;
                                                                         }
                                                                         break;
                                                                     }
                                                                 }
                                                             }
                                                         } else {
                                                             reserva = iniciativaList[valorIniciativa];
                                                             iniciativaList[valorIniciativa] = personagem;
                                                             if (iniciativaList[valorIniciativa - 1] == null) {
                                                                 iniciativaList[valorIniciativa - 1] = reserva;
                                                             } else {
                                                                 for (int t = 0; t < checarPersonagem.length; t++) {
                                                                     if (checarPersonagem[t].equals(checarPersonagem[u])) {
                                                                         if (checarIniciativa[t] >= checarIniciativa[u]) {
                                                                             iniciativaList[valorIniciativa - 2] = reserva;
                                                                         } else {
                                                                             String reserva1 = iniciativaList[valorIniciativa - 1];
                                                                             iniciativaList[valorIniciativa - 1] = reserva;
                                                                             iniciativaList[valorIniciativa - 2] = reserva1;
                                                                         }
                                                                         break;
                                                                     }
                                                                 }
                                                             }
                                                         }
                                                     }
                                                 }
                                             }
                                         }catch (NullPointerException ignored){

                                         }
                                         u++;
                                         i+=2;
                                     }
                                     String[] mensagemIniciativa = new String[10];

                                     int o = 0;
                                     i = 30;

                                     while (i!=0){
                                         try {
                                             if (iniciativaList[i] != null) {
                                                 mensagemIniciativa[o] = iniciativaList[i];
                                                 o++;
                                             }
                                         }catch (NullPointerException ignored){

                                         }
                                         i-=1;
                                     }

                                     int numeroDePessoas = quantidadePessoas/2;
                                     String mensagemFinal;
                                     switch (numeroDePessoas) {
                                         case 2:
                                             mensagemFinal = mensagemIniciativa[0] + ", " + mensagemIniciativa[1];
                                             break;

                                         case 3:
                                             mensagemFinal = mensagemIniciativa[0] + ", " + mensagemIniciativa[1] + ", " +
                                                     mensagemIniciativa[2];
                                             break;

                                         case 4:
                                             mensagemFinal = mensagemIniciativa[0] + ", " + mensagemIniciativa[1] + ", " +
                                                     mensagemIniciativa[2] + ", " + mensagemIniciativa[3];
                                             break;

                                         case 5:
                                             mensagemFinal = mensagemIniciativa[0] + ", " + mensagemIniciativa[1] + ", " +
                                                     mensagemIniciativa[2] + ", " + mensagemIniciativa[3] + ", " + mensagemIniciativa[4];
                                             break;

                                         case 6:
                                             mensagemFinal = mensagemIniciativa[0] + ", " + mensagemIniciativa[1] + ", " +
                                                     mensagemIniciativa[2] + ", " + mensagemIniciativa[3] + ", " + mensagemIniciativa[4] +
                                                     ", " + mensagemIniciativa[5];
                                             break;

                                         case 7:
                                             mensagemFinal = mensagemIniciativa[0] + ", " + mensagemIniciativa[1] + ", " +
                                                     mensagemIniciativa[2] + ", " + mensagemIniciativa[3] + ", " + mensagemIniciativa[4] +
                                                     ", " + mensagemIniciativa[5] + ", " + mensagemIniciativa[6];
                                             break;

                                         case 8:
                                             mensagemFinal = mensagemIniciativa[0] + ", " + mensagemIniciativa[1] + ", " +
                                                     mensagemIniciativa[2] + ", " + mensagemIniciativa[3] + ", " + mensagemIniciativa[4] +
                                                     ", " + mensagemIniciativa[5] + ", " + mensagemIniciativa[6] + ", " +
                                                     mensagemIniciativa[7];
                                             break;

                                         default:
                                             throw new IllegalStateException("Unexpected value: " + numeroDePessoas);
                                     }

                                     String finalMensagemFinal = mensagemFinal;
                                     message.getChannel().subscribe(messageChannel -> {
                                         messageChannel.createMessage(String.valueOf(finalMensagemFinal)).subscribe();
                                     });
                                     return 1;
                            })
                        ));

/*
                    dispatcher.register(
                            literal("say").then(argument("thing", greedyString()).executes(context -> {
                                        String thing = getString(context, "thing");
                                        message.getChannel().subscribe(messageChannel -> {
                                            messageChannel.createMessage(thing).subscribe();
                                        });
                                        return 1;
                                    })
                            ));
*/

                dispatcher.register(
                        literal("d.20").executes(context -> {
                            message.getChannel().subscribe(messageChannel -> {
                                Random random = new Random();
                                int d20 = random.nextInt((20 - 2 + 1) + 1);
                                messageChannel.createMessage("``` " + String.valueOf(d20+1) + " <-- [d20] ```").subscribe();
                            });
                            return 1;
                        })
                );

                dispatcher.register(
                        literal("d.10").executes(context -> {
                            message.getChannel().subscribe(messageChannel -> {
                                Random random = new Random();
                                int d10 = random.nextInt((10 - 2 + 1) + 1);
                                messageChannel.createMessage("``` " + String.valueOf(d10+1) + " <-- [d10] ```").subscribe();
                            });
                            return 1;
                        })
                );

                try {
                    dispatcher.execute(message.getContent(), null);
                } catch (CommandSyntaxException e) {
                    e.printStackTrace();
                }
            }
            /*
            Optional<Member> memberOptional = event.getMember();
            memberOptional.ifPresent((member)->{
                member.ban(banQuerySpec -> {
                   banQuerySpec.setReason("BANIDO");
                }).subscribe(ignore->{
                    System.out.println("MEMBRO BANIDO");
                });

            });
            */
            });
        gateway.onDisconnect().block();
    }
}

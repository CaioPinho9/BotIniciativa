import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;

import java.util.*;

import static com.mojang.brigadier.arguments.StringArgumentType.*;
import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;
import static com.mojang.brigadier.builder.RequiredArgumentBuilder.argument;

public class Octavius {

    public static <Iniciativa, pessoas> void main(String[] args) {
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
                                    ArrayList<String> iniciativaList = new ArrayList<>(40);
                                    int valorIniciativa = 0;
                                    int quantidadePessoas = pessoas.length;

                                    /*
                                    System.out.println(pessoas[0]);
                                    System.out.println(pessoas[1]);
                                    System.out.println(pessoas[2]);
                                    System.out.println(pessoas[3]);
                                    System.out.println(quantidadePessoas);
                                    */

                                     int i = 0;
                                     while (i<quantidadePessoas) {
                                         Random random = new Random();
                                         int dado = 0;

                                         valorIniciativa = 0;
                                         int bonus = 0;

                                         bonus = Integer.parseInt(pessoas[i+1]);

                                         /*
                                         System.out.println("");
                                         System.out.println(bonus);
                                         */

                                         String personagem = pessoas[i];

                                         dado = random.nextInt(19)+1;

                                         valorIniciativa = dado+bonus;
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

/*
                                         System.out.println(valorIniciativa);
                                         System.out.println(personagem);
*/
                                         System.out.println("a");

                                         if (iniciativaList.get(valorIniciativa).isBlank()) {
                                             System.out.println("a");
                                             iniciativaList.set(valorIniciativa, personagem);
                                             System.out.println(valorIniciativa);
                                             System.out.println(iniciativaList.get(valorIniciativa));
                                         } else {
                                             iniciativaList.set(valorIniciativa - 1, personagem);
                                             System.out.println(valorIniciativa);
                                             System.out.println(iniciativaList.get(valorIniciativa));
                                         }

                                         i+=2;
                                     }
                                     ArrayList<String> mensagemIniciativa = new ArrayList<>(10);

                                     int o = 1;
                                     i = 25;

                                     while (i!=0){
                                         System.out.println(i);
                                         String nulo;
                                         try {
                                             nulo = iniciativaList.get(i);
                                         }catch (IndexOutOfBoundsException ignored){
                                             nulo = "";
                                         }
                                         System.out.println(nulo);
                                         if (!nulo.equals("")) {
                                             try {
                                                 System.out.println(i);
                                                 mensagemIniciativa.set(o, iniciativaList.get(i));
                                                 System.out.println(mensagemIniciativa.get(o));
                                                 o++;

                                                 System.out.println("a");
                                             }catch (IndexOutOfBoundsException ignored){

                                             }
                                         }

                                         i-=1;
                                     }
                                     System.out.println(mensagemIniciativa.get(0));

                                     int numeroDePessoas = quantidadePessoas/2;
                                     String mensagemFinal;
                                     switch (numeroDePessoas) {
                                         case 2:
                                             mensagemFinal = mensagemIniciativa.get(0) + ", " + mensagemIniciativa.get(1);
                                             break;

                                         case 3:
                                             mensagemFinal = mensagemIniciativa.get(0) + ", " + mensagemIniciativa.get(1) + ", " + mensagemIniciativa.get(2);
                                             break;

                                         case 4:
                                             mensagemFinal = mensagemIniciativa.get(0) + ", " + mensagemIniciativa.get(1) + ", " +
                                                     mensagemIniciativa.get(2) + ", " + mensagemIniciativa.get(3);
                                             break;

                                         case 5:
                                             mensagemFinal = mensagemIniciativa.get(0) + ", " + mensagemIniciativa.get(1) + ", " +
                                                     mensagemIniciativa.get(2) + ", " + mensagemIniciativa.get(3) + ", " + mensagemIniciativa.get(4);


                                         case 6:
                                             mensagemFinal = mensagemIniciativa.get(0) + ", " + mensagemIniciativa.get(1) + ", " +
                                                     mensagemIniciativa.get(2) + ", " + mensagemIniciativa.get(3) + ", " + mensagemIniciativa.get(4) +
                                                     ", " + mensagemIniciativa.get(5);
                                             break;

                                         case 7:
                                             mensagemFinal = mensagemIniciativa.get(0) + ", " + mensagemIniciativa.get(1) + ", " +
                                                     mensagemIniciativa.get(2) + ", " + mensagemIniciativa.get(3) + ", " + mensagemIniciativa.get(4) +
                                                     ", " + mensagemIniciativa.get(5) + ", " + mensagemIniciativa.get(6);

                                         case 8:
                                             mensagemFinal = mensagemIniciativa.get(0) + ", " + mensagemIniciativa.get(1) + ", " +
                                                     mensagemIniciativa.get(2) + ", " + mensagemIniciativa.get(3) + ", " + mensagemIniciativa.get(4) +
                                                     ", " + mensagemIniciativa.get(5) + ", " + mensagemIniciativa.get(6) + ", " +
                                                     mensagemIniciativa.get(7);
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

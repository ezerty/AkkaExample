package com.mycompany.akkaexample;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.mycompany.akkaexample.actors.ClientManager;
import com.mycompany.akkaexample.messages.TransmissionBeginMessage;
import com.typesafe.config.ConfigFactory;

public class ClientApplication {

    private static ActorSystem system = ActorSystem.create("MyClientSystem",
            ConfigFactory.load().getConfig("MyClientSystem"));

    public static void main(String[] args) {

        ActorRef clientManager = system.actorOf(Props.create(ClientManager.class));
        clientManager.tell(new TransmissionBeginMessage(), null);
    }

    public static void shutdown() {
        system.shutdown();
    }
}

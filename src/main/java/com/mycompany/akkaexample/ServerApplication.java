package com.mycompany.akkaexample;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.FromConfig;
import com.mycompany.akkaexample.actors.DumbGenerator;
import com.typesafe.config.ConfigFactory;

public class ServerApplication {

    private static ActorSystem system = ActorSystem.create("MyServerSystem",
            ConfigFactory.load().getConfig("MyServerSystem"));

    public static void main(String[] args) {
        ActorRef router = system.actorOf(FromConfig.getInstance()
                .props(Props.create(DumbGenerator.class)), "dumbRouter");

        System.out.println("Server system has been started");
    }

    public static void shutdown() {
        system.shutdown();
    }
}

package com.mycompany.akkaexample.actors;

import akka.actor.UntypedActor;
import com.mycompany.akkaexample.ServerApplication;
import com.mycompany.akkaexample.messages.CompletionMessage;
import com.mycompany.akkaexample.messages.DumbInvocationMessage;
import com.mycompany.akkaexample.messages.JobDoneMessage;

public class DumbGenerator extends UntypedActor {

    private final int id = hashCode();

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof DumbInvocationMessage) {
            DumbInvocationMessage invocationMessage = (DumbInvocationMessage) message;
            Thread.sleep(invocationMessage.getDuration());
            String result = id + ": " + invocationMessage.getContent();
            System.out.println(result);
            getSender().tell(new JobDoneMessage(), getSelf());

        } else if (message instanceof CompletionMessage) {
            getSender().tell(message, getSelf());
            ServerApplication.shutdown();

        } else {
            throw new IllegalArgumentException("Unknown message");
        }
    }

}

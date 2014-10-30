package com.mycompany.akkaexample.actors;

import akka.actor.ActorSelection;
import akka.actor.UntypedActor;
import com.mycompany.akkaexample.ClientApplication;
import com.mycompany.akkaexample.messages.CompletionMessage;
import com.mycompany.akkaexample.messages.DumbInvocationMessage;
import com.mycompany.akkaexample.messages.JobDoneMessage;
import com.mycompany.akkaexample.messages.TransmissionBeginMessage;

public class ClientManager extends UntypedActor {

    private final static int JOBS_COUNT = 100;
    private static final int JOB_DURATION = 50; // ms

    private ActorSelection remoteRouter;
    private int completedJobsCount;

    @Override
    public void preStart() throws Exception {
        super.preStart();
        remoteRouter = getContext().actorSelection("akka.tcp://MyServerSystem@127.0.0.1:2552/user/dumbRouter");
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof TransmissionBeginMessage) {
            completedJobsCount = 0;
            for (int i = 0; i < JOBS_COUNT; i++) {
                DumbInvocationMessage job = new DumbInvocationMessage(String.valueOf(i),
                        (int) (Math.random() * JOB_DURATION));
                remoteRouter.tell(job, getSelf());
            }
        } else if (message instanceof JobDoneMessage) {
            if (++completedJobsCount == JOBS_COUNT) {
                remoteRouter.tell(new CompletionMessage(), getSelf());
            }

        } else if (message instanceof CompletionMessage) {
            ClientApplication.shutdown();

        } else {
            throw new IllegalArgumentException("Unknown message");
        }
    }

}

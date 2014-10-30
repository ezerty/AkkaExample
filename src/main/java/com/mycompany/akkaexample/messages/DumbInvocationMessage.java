package com.mycompany.akkaexample.messages;

import java.io.Serializable;

public class DumbInvocationMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String content;
    private final int duration;

    public DumbInvocationMessage(String content, int duration) {
        this.content = content;
        this.duration = duration;
    }

    public String getContent() {
        return content;
    }

    public int getDuration() {
        return duration;
    }

}

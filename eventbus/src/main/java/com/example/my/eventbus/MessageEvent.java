package com.example.my.eventbus;

public class MessageEvent {

    private double random;

    public MessageEvent(double random) {
        this.random = random;
    }

    public double getRandom() {
        return random;
    }

    public void setRandom(double random) {
        this.random = random;
    }
}

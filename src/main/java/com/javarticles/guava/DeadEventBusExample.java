package com.javarticles.guava;

import com.google.common.eventbus.EventBus;

public class DeadEventBusExample {
    public static void main(String[] args) {
        EventBus eventBus = new EventBus(){
            public String toString() {
                return "DeadEventBus";
            }
        };
        eventBus.register(new DeadEventListener());
        System.out.println("Post 'Some Event'");
        eventBus.post("Some Event");
    }
}

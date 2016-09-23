package com.javarticles.guava;

import com.google.common.eventbus.EventBus;

public class UnregisterEventBusExample {
    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        SimpleListener simpleListener = new SimpleListener();
        eventBus.register(simpleListener);
        System.out.println("Post Simple EventBus Example");
        eventBus.post("Simple EventBus Example");
        System.out.println("Unregister the subscriber");
        eventBus.unregister(simpleListener);
        System.out.println("Post Simple EventBus Example once again");
        eventBus.post("Simple EventBus Example");
    }
}

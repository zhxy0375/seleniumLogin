package com.javarticles.guava;

import com.google.common.eventbus.EventBus;

public class MultipleEventTypeEventBusExample {
    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        eventBus.register(new MultipleListeners());
        System.out.println("Post 'Multiple Listeners Example'");
        eventBus.post("Multiple Listeners Example");      
        eventBus.post(1);
    }
}

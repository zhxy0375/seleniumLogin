package com.javarticles.guava;

import com.google.common.eventbus.EventBus;

public class ListenerHierarchyEventBusExample {
    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        eventBus.register(new ListenerHierarchy());
        System.out.println("Post 'Listener Hierarchy Example'");
        eventBus.post("Listener Hierarchy Example");      
    }
}

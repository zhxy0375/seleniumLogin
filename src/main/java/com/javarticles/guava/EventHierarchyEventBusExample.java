package com.javarticles.guava;

import com.google.common.eventbus.EventBus;

public class EventHierarchyEventBusExample {
    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        eventBus.register(new FruitEaterListener());
        System.out.println("Post 'Apple'");
        eventBus.post(new Apple());  
        System.out.println("Post 'Orange as Fruit'");
        eventBus.post(new Fruit("Orange"));        
    }
}

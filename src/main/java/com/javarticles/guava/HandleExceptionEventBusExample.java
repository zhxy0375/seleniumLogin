package com.javarticles.guava;

import com.google.common.eventbus.EventBus;

public class HandleExceptionEventBusExample {
    public static void main(String[] args) {
        EventBus eventBus = new EventBus(new HandleNotAfruitException());
        eventBus.register(new FruitEaterListener());
        System.out.println("Post 'Raw Mango'");
        eventBus.post(new Fruit("Raw Mango", false));        
    }
}

package com.javarticles.guava;

import com.google.common.eventbus.Subscribe;

//  http://javarticles.com/2015/04/guava-eventbus-examples.html
public abstract class AbstractListener {
    @Subscribe
    public void commonTask(String s) {
        System.out.println("do commonTask(" + s + ")");
    }
}

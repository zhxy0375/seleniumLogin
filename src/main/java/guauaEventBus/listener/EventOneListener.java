package guauaEventBus.listener;

import com.google.common.eventbus.Subscribe;

public class EventOneListener {

	@Subscribe
    public void task(String s) {
        System.out.println("do task(" + s + ")");
    }
}

package guauaEventBus;

import com.google.common.eventbus.EventBus;

import guauaEventBus.listener.EventOneListener;

public class EventBusTest {

	 public static void main(String[] args) {
	        EventBus eventBus = new EventBus();
	        eventBus.register(new EventOneListener());
	        System.out.println("Post Simple EventBus Example");
	        eventBus.post("Simple EventBus Example");
	    }
}

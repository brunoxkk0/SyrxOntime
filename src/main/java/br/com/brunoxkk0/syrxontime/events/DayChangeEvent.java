package br.com.brunoxkk0.syrxontime.events;

import br.com.brunoxkk0.syrxontime.utils.Clock;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DayChangeEvent extends Event{

    private int currentDay;
    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public int getCurrentDay() {
        return currentDay;
    }

    public DayChangeEvent(){
        currentDay = Clock.getCurrentDay();
    }

}

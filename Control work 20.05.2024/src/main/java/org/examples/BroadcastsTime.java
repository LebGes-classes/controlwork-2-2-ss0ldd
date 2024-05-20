package org.examples;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class BroadcastsTime implements Comparable<BroadcastsTime>{

    private final int hour;
    private final int minutes;
    private static final String TIME = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm"));
    public BroadcastsTime(int hour, int minutes) {
        this.hour = hour;
        this.minutes = minutes;
    }
    public static BroadcastsTime now() {
        return new BroadcastsTime(LocalTime.now().getHour(), LocalTime.now().getMinute());
    }

    public static BroadcastsTime parse(String line) {
        return null;
    }

    public int getHour() {
        return hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public boolean after(BroadcastsTime time) {
        return hour > time.getHour() || (hour == time.getHour() && minutes > time.getMinutes());
    }

    public boolean before(BroadcastsTime time){
        return hour < time.getHour() || (hour == time.getHour() && minutes < time.getMinutes());
    }

    public boolean between(BroadcastsTime time1, BroadcastsTime time2){
        return after(time1)&&before(time2);
    }


    @Override
    public int compareTo(BroadcastsTime f) {
        if (f != null) {
            BroadcastsTime otherTime = f;
            if (before(otherTime)) {
                return -1;
            }
            if (after(otherTime)) {
                return 1;
            }
            return 0;
        }
        throw new RuntimeException("Incorrect object type");
    }

    @Override
    public String toString() {
        return hour + ":" + minutes;
    }
}

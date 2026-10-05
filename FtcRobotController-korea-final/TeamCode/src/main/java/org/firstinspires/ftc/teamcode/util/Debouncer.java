package org.firstinspires.ftc.teamcode.util;

public class Debouncer {
    private long lastPressTime = 0;
    private final long debounceTimeMs;

    public Debouncer(long debounceTimeMs) {
        this.debounceTimeMs = debounceTimeMs;
    }

    public boolean isReady() {
        long now = System.currentTimeMillis();
        if (now - lastPressTime > debounceTimeMs) {
            lastPressTime = now;
            return true;
        }
        return false;
    }
}

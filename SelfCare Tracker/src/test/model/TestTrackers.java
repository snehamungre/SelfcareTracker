package model;

import model.trackers.Trackers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public abstract class TestTrackers {
    Trackers tracker;

    @Test
    public void testUpdateProgressOnce() {
        tracker.setProgress(50);
        assertEquals(50, tracker.getProgress());
    }

    @Test
    public void testUpdateProgressMultiple() {
        tracker.setProgress(50);
        assertEquals(50, tracker.getProgress());
        tracker.setProgress(50);
        assertEquals(100, tracker.getProgress());
    }

    @Test
    public void testTargetMetFalse() {
        tracker.setTarget(8);
        tracker.setProgress(5);

        assertFalse(tracker.targetMet());
    }

    @Test
    public void testTargetMetTrue() {
        tracker.setTarget(7);
        tracker.setProgress(8);
        assertTrue(tracker.targetMet());
    }

    @Test
    public abstract void testGetUnits();

    @Test
    public abstract void testGetUnitsWithValue();
}

package model;

import model.trackers.SleepTracker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestSleepTracker extends TestTrackers{

    @BeforeEach
    public void setUp(){
        tracker = new SleepTracker();
    }

    @Test
    @Override
    public void testUpdateProgressMultiple(){
        tracker.setProgress(4);
        assertEquals(4, tracker.getProgress());
        tracker.setProgress(8);
        assertEquals(8, tracker.getProgress());
    }

    @Test
    @Override
    public void testGetUnits(){
        assertEquals("hours", tracker.getUnits());
    }


    @Test
    public void testGetUnitsWithValue() {
        assertEquals("hours", tracker.getUnits(3));
    }
}

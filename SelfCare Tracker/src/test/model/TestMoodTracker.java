package model;

import model.trackers.MoodTracker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestMoodTracker extends TestTrackers{
    
    @BeforeEach
    public void setUp(){
        tracker = new MoodTracker();
    }

    @Test
    @Override
    public void testTargetMetTrue(){
        tracker.setTarget(1);
        tracker.setProgress(1);
        assertTrue(tracker.targetMet());
    }


    @Test
    @Override
    public void testTargetMetFalse(){
        tracker.setTarget(1);
        tracker.setProgress(2);
        assertFalse(tracker.targetMet());
    }

    @Test
    @Override
    public void testUpdateProgressMultiple(){
        tracker.setProgress(1);
        assertEquals(1, tracker.getProgress());
        tracker.setProgress(3);
        assertEquals(3, tracker.getProgress());
    }

    @Override
    @Test
    public void testGetUnits() {
        assertEquals("\n :)   (1) \n :/   (2)  \n :(   (3)", tracker.getUnits());
    }

    @Test
    @Override
    public void testGetUnitsWithValue(){
        assertEquals(":)", tracker.getUnits(1));
        assertEquals(":/", tracker.getUnits(2));
        assertEquals(":(", tracker.getUnits(3));
    }
}

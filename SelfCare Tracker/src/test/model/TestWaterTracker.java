package model;

import model.trackers.WaterTracker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestWaterTracker extends TestTrackers {

    @BeforeEach
    public void setUp() {
        tracker = new WaterTracker();
        assertEquals(0, tracker.getProgress());
    }

    @Test
    @Override
    public void testGetUnits() {
        assertEquals("ml", tracker.getUnits());
    }

    @Test
    public void testGetUnitsWithValue(){
        assertEquals("ml", tracker.getUnits(2));
    }
}
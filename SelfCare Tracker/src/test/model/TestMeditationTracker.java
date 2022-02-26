package model;

import model.trackers.MeditationTracker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestMeditationTracker extends TestTrackers {

    @BeforeEach
    public void setUp() {
        tracker = new MeditationTracker();
        assertEquals(0, tracker.getProgress());
    }


    @Test
    public void testGetUnits() {
        assertEquals("minutes", tracker.getUnits());
    }

    @Test
    public void testGetUnitsWithValue(){
        assertEquals("minutes", tracker.getUnits(2));
    }
}
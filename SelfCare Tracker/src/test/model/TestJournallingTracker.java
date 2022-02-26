package model;

import model.trackers.JournalingTracker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestJournallingTracker extends TestTrackers {

    @BeforeEach
    public void setUp() {
        tracker = new JournalingTracker();
        assertEquals(0, tracker.getProgress());
    }

    @Override
    @Test
    public void testGetUnits() {
        assertEquals("times", tracker.getUnits());
    }

    @Test
    public void testGetUnitsWithValue(){
        assertEquals("times", tracker.getUnits(2));
    }
}
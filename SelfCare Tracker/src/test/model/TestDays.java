package model;

import model.trackers.Trackers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestDays {
    Days days;

    @BeforeEach
    public void setUp() {
        days = new Days();
        assertEquals(0, days.getNoOfDays());
    }

    @Test
    public void testAddOneDay() {
        Goals g = new Goals();
        days.addDay(g);
        assertEquals(1, days.getNoOfDays());
        assertEquals(g, days.getADay(0));
    }

    @Test
    public void testAddMultipleDay() {
        Goals g1 = new Goals();
        Goals g2 = new Goals();
        days.addDay(g1);
        days.addDay(g2);
        assertEquals(2, days.getNoOfDays());
        assertEquals(g1, days.getADay(0));
        assertEquals(g2, days.getADay(1));
    }

    @Test
    public void testAddDuplicateofPreviousDay() {
        Goals g1 = new Goals();
        days.addDay(g1);
        days.addDay();
        assertEquals(2, days.getNoOfDays());
        assertEquals(g1, days.getADay(0));
        assertNotEquals(g1, days.getADay(1));
    }
}

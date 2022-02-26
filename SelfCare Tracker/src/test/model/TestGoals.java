package model;

import model.trackers.Trackers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class TestGoals {
    Goals goals;

    @BeforeEach
    public void setUp(){
        goals = new Goals();
        assertEquals (0 , goals.noOfTrackersSet());
    }

    @Test
    public void testGetTracker(){
        try {
            goals.addGoal("Water");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Trackers tracker;
        tracker = goals.getTracker("Water");
        assertEquals(tracker , goals.getTracker("Water"));
    }

    @Test
    public void testAddGoalToEmpty(){
        try {
            goals.addGoal("Water");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(1, goals.noOfTrackersSet());
        assertTrue(goals.goalsAlreadySet().contains("water"));
    }

    @Test
    public void testAddGoalToNotEmpty(){
        try {
            goals.addGoal("Water");
            goals.addGoal("Mood");
            goals.addGoal("Sleep");
        } catch (KeyError e) {
            fail("Should not throw an exception");
        }
        assertEquals(3, goals.noOfTrackersSet());

        try {
            goals.addGoal("Meditation");
        } catch (KeyError e) {
           fail();
        }

        assertEquals(4, goals.noOfTrackersSet());
        assertTrue(goals.goalsAlreadySet().contains("water"));
        assertTrue(goals.goalsAlreadySet().contains("mood"));
        assertTrue(goals.goalsAlreadySet().contains("sleep"));
        assertTrue(goals.goalsAlreadySet().contains("meditation"));
    }

    @Test
    public void testAddGoalThrowKeyException(){
        try {
            goals.addGoal("someTracker");
            fail("Didn't throw an exception");
        } catch (Exception e) {
            // should throw an exception
        }
        assertEquals(0, goals.noOfTrackersSet());
    }



    @Test
    public void testRemoveGoalFromOne(){
        try {
            goals.addGoal("journal");
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(goals.goalsAlreadySet().contains("journal"));

        goals.removeTracker("journal");
        assertEquals(0, goals.noOfTrackersSet());
    }

    @Test
    public void testRemoveGoalFromMultiple(){
        try {
            goals.addGoal("Water");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            goals.addGoal("meditation");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            goals.addGoal("Sleep");
        } catch (Exception e) {
            e.printStackTrace();
        }

        goals.removeTracker("water");
        assertEquals(2, goals.noOfTrackersSet());
        assertTrue(goals.goalsAlreadySet().contains("meditation"));
        assertTrue(goals.goalsAlreadySet().contains("sleep"));

        goals.removeTracker("meditation");
        assertEquals(1, goals.noOfTrackersSet());
        assertTrue(goals.goalsAlreadySet().contains("sleep"));
    }

    @Test
    public void testSetAllTargetMetFalse(){
        try {
            goals.addGoal("water");

            Trackers t = goals.getTracker("water");
            t.setTarget(750);
            t.setProgress(750);

            goals.addGoal("meditation");
            t = goals.getTracker("meditation");
            t.setTarget(50);
            t.setProgress(50);

            goals.addGoal("sleep");
            t = goals.getTracker("sleep");
            t.setTarget(7);
            t.setProgress(5);

            goals.setAllTargetMet();

        } catch (Exception e) {
            e.printStackTrace();
        }
        assertFalse(goals.getAllTargetMet());

    }

    @Test
    public void testSetAllTargetMetTrue(){
        try {
            goals.addGoal("water");

            Trackers t = goals.getTracker("water");
            t.setTarget(750);
            t.setProgress(750);

            goals.addGoal("meditation");
            t = goals.getTracker("meditation");
            t.setTarget(50);
            t.setProgress(50);

            goals.addGoal("sleep");
            t = goals.getTracker("sleep");
            t.setTarget(7);
            t.setProgress(7);

            goals.setAllTargetMet();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(goals.getAllTargetMet());
    }

    @Test
    public void testCloneGoal(){
        try {
            goals.addGoal("journal");
            Trackers t1 = goals.getTracker("journal");
            t1.setTarget(7);
            t1.setProgress(5);
            Goals newGoal = goals.copyLastGoal();
            assertEquals(1, newGoal.noOfTrackersSet());
            assertTrue(newGoal.goalsAlreadySet().contains("journal"));
            Trackers t2 = newGoal.getTracker("journal");
            assertEquals(0, t2.getProgress());

            goals.addGoal("sleep");
            assertEquals(1, newGoal.noOfTrackersSet());
            t2.setTarget(4);
            assertEquals(7, t1.getTarget());
        } catch (Exception e) {
            System.out.println("this shouldn't happen");
        }
    }
}

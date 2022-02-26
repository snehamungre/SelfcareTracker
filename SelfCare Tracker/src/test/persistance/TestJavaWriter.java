package persistance;

import model.Days;
import model.Goals;
import model.KeyError;
import model.trackers.Trackers;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class TestJavaWriter extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Goals g = new Goals();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyGoalsInOneDay() {
        try {
            Days d = new Days();
            Goals g = new Goals();
            d.addDay(g);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyGoals.json");
            writer.open();
            writer.write(d);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyGoals.json");
            Days day = reader.read();
            Goals go = day.getADay(0);

            assertEquals(0, go.noOfTrackersSet());
        } catch (IOException | KeyError e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterOneGoalsInOneDay() {
        try {
            Days d = new Days();
            Goals g = new Goals();
            d.addDay(g);
            g.addGoal("water");
            Trackers t = g.getTracker("water");
            t.setTarget(560);
            t.setProgress(450);

            JsonWriter writer = new JsonWriter("./data/testWriterOneGoals.json");
            writer.open();
            writer.write(d);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterOneGoals.json");

            Days day = reader.read();
            Goals go = day.getADay(0);

            assertEquals(1, go.noOfTrackersSet());
            List<String> keys = go.goalsAlreadySet();
            t = go.getTracker(keys.get(0));

            assertEquals(560, t.getTarget());
            assertEquals(450, t.getProgress());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (KeyError e) {
            fail("Key Error should not have been thrown");
        }
    }

    @Test
    void testWriterMultipleGoalsInOneDay() {
        try {
            Days d = new Days();
            Goals g = new Goals();
            d.addDay(g);
            g.addGoal("sleep");
            g.addGoal("mood");
            g.addGoal("meditation");
            g.addGoal("journal");

            Trackers t = g.getTracker("sleep");
            t.setTarget(7);
            t.setProgress(3);


            t = g.getTracker("mood");
            t.setTarget(1);
            t.setProgress(3);

            t = g.getTracker("meditation");
            t.setTarget(15);
            t.setProgress(13);

            t = g.getTracker("journal");
            t.setTarget(3);
            t.setProgress(1);

            JsonWriter writer = new JsonWriter("./data/testWriterMultipleGoals.json");
            writer.open();
            writer.write(d);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterMultipleGoals.json");

            Days day = reader.read();
            Goals go = day.getADay(0);

            assertEquals(4, go.noOfTrackersSet());
            List<String> keys = go.goalsAlreadySet();

            //checkTrackers(keys, g, go);

            t = go.getTracker(keys.get(0));//sleep
            assertEquals(7, t.getTarget());
            assertEquals(3, t.getProgress());

            t = go.getTracker(keys.get(1));//journaling
            assertEquals(3, t.getTarget());
            assertEquals(1, t.getProgress());


            t = go.getTracker(keys.get(2));//mood
            assertEquals(1, t.getTarget());
            assertEquals(3, t.getProgress());

            t = go.getTracker(keys.get(3));//meditation
            assertEquals(15, t.getTarget());
            assertEquals(13, t.getProgress());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (KeyError e) {
            fail("Key Error should not have been thrown");
        }
    }

    @Test
    void testReaderOneGoalsInTwoDays() {
        try {
            Days d = new Days();
            Goals g1 = new Goals();
            Goals g2 = new Goals();
            d.addDay(g1);
            d.addDay(g2);
            g1.addGoal("water");
            Trackers t = g1.getTracker("water");
            t.setTarget(560);
            t.setProgress(450);

            g2.addGoal("sleep");
            t = g2.getTracker("sleep");
            t.setTarget(7);
            t.setProgress(7);

            JsonWriter writer = new JsonWriter("./data/testWriterOneGoalsTwoDays.json");
            writer.open();
            writer.write(d);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterOneGoalsTwoDays.json");

            Days day = reader.read();
            Goals go1 = day.getADay(0);
            Goals go2 = day.getADay(1);

            assertEquals(1, go1.noOfTrackersSet());
            List<String> keys = go1.goalsAlreadySet();
            t = go1.getTracker(keys.get(0));

            assertEquals(560, t.getTarget());
            assertEquals(450, t.getProgress());

            assertEquals(1, go2.noOfTrackersSet());
            keys = go2.goalsAlreadySet();
            t = go2.getTracker(keys.get(0));

            assertEquals(7, t.getTarget());
            assertEquals(7, t.getProgress());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (KeyError e) {
            fail("Key Error should not have been thrown");
        }
    }
}

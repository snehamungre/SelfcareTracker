package persistance;

import model.Days;
import model.Goals;
import model.KeyError;
import model.trackers.Trackers;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestJavaReader {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Days d = reader.read();
            fail("IOException expected");
        } catch (IOException | KeyError e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoomInOneDay() {
        JsonReader reader = new JsonReader("./data/testWriterEmptyGoals.json");
        try {
            Days d = reader.read();
            Goals g = d.getADay(0);

            assertEquals(0, g.noOfTrackersSet());
        } catch (IOException | KeyError e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderOneGoalsInOneDay() {
        JsonReader reader = new JsonReader("./data/testWriterOneGoals.json");
        try {
            Days d = reader.read();
            Goals go = d.getADay(0);

            assertEquals(1, go.noOfTrackersSet());
            List<String> keys = go.goalsAlreadySet();
            Trackers t = go.getTracker(keys.get(0));
            assertEquals(560, t.getTarget());
            assertEquals(450, t.getProgress());
        } catch (IOException | KeyError e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderMultipleGoalsInOneDay() {
        JsonReader reader = new JsonReader("./data/testWriterMultipleGoals.json");
        Trackers t;
        try {
            Days d = reader.read();
            Goals go = d.getADay(0);

            List<String> keys = go.goalsAlreadySet();

            t = go.getTracker(keys.get(0));//sleep
            assertEquals(7, t.getTarget());
            assertEquals(3, t.getProgress());

            t = go.getTracker(keys.get(1));//journal
            assertEquals(3, t.getTarget());
            assertEquals(1, t.getProgress());


            t = go.getTracker(keys.get(2));//mood
            assertEquals(1, t.getTarget());
            assertEquals(3, t.getProgress());

            t = go.getTracker(keys.get(3));//meditation
            assertEquals(15, t.getTarget());
            assertEquals(13, t.getProgress());
        } catch (IOException | KeyError e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderOneGoalsInTwoDays() {
        JsonReader reader = new JsonReader("./data/testWriterOneGoalsTwoDays.json");
        try {
            Days d = reader.read();
            Goals go1 = d.getADay(0);

            assertEquals(1, go1.noOfTrackersSet());
            List<String> keys = go1.goalsAlreadySet();
            Trackers t = go1.getTracker(keys.get(0));
            assertEquals(560, t.getTarget());
            assertEquals(450, t.getProgress());

            Goals go2 = d.getADay(1);

            assertEquals(1, go2.noOfTrackersSet());
            keys = go2.goalsAlreadySet();
            t = go2.getTracker(keys.get(0));
            assertEquals(7, t.getTarget());
            assertEquals(7, t.getProgress());
        } catch (IOException | KeyError e) {
            fail("Couldn't read from file");
        }
    }
}

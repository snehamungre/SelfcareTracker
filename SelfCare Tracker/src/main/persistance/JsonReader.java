package persistance;

import model.Days;
import model.Goals;
import model.KeyError;
import model.trackers.Trackers;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/*
Citation: code obtained from JsonSerializationDemo
     URL: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
*/

// Represents a reader which reads the Goals from a JSON file
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads goals from file and returns it;
    // throws IOException if an error occurs reading data from file
    // throws KeyError if an error occurs with the type of each tracker
    public Days read() throws IOException, KeyError {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseDays(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    // throws IOException if an error occurs reading data from file
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses Days from JSON object and returns it
    // throws KeyError if an error occurs with the type of each tracker
    private Days parseDays(JSONObject jsonObject) throws KeyError {
        Days days = new Days();
        addGoals(days, jsonObject);
        return days;
    }

    // MODIFIES: goals
    // EFFECTS: parses trackers from JSON object and adds them to goals
    // throws KeyError if an error occurs with the type of each tracker
    private void addGoals(Days day, JSONObject jsonObject) throws KeyError {
        JSONArray jsonArray = jsonObject.getJSONArray("days");
        for (Object json : jsonArray) {
            JSONObject nextGoal = (JSONObject) json;
            day.addDay(parseGoals(nextGoal));
        }
    }

    // EFFECTS: parses Goals from JSON object and returns it
    // throws KeyError if an error occurs with the type of each tracker
    private Goals parseGoals(JSONObject jsonObject) throws KeyError {
        Goals goals = new Goals();
        addTrackers(goals, jsonObject);
        return goals;
    }

    // MODIFIES: goals
    // EFFECTS: parses trackers from JSON object and adds them to goals
    private void addTrackers(Goals goals, JSONObject jsonObject) throws KeyError {
        JSONArray jsonArray = jsonObject.getJSONArray("trackers");
        for (Object json : jsonArray) {
            JSONObject nextTracker = (JSONObject) json;
            addTracker(goals, nextTracker);
        }
    }

    // MODIFIES: goals
    // EFFECTS: parses tracker from JSON object and adds it to goals
    private void addTracker(Goals goals, JSONObject nextTracker) throws KeyError {
        String type = nextTracker.getString("type");
        String units = nextTracker.getString("units");
        int target = nextTracker.getInt("target");
        int progress = nextTracker.getInt("progress");

        goals.addGoal(type);
        Trackers trackers = goals.getTracker(type);
        trackers.setTracker(type, target, progress, units);
    }
}

package model;

import model.trackers.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Represents a collection of trackers that the user has set.
public class Goals extends HashMap {
    private final HashMap<String, Trackers> goals;
    private boolean allTargetMet = false;

    // EFFECTS: creates a new collection to store goals being tracked
    public Goals() {
        goals = new HashMap<>();
    }

    // REQUIRES: key should not already exist in the set (no duplicates)
    // MODIFIES: this
    // EFFECTS: adds a new tracker after the previous one
    public void addGoal(String key) throws KeyError {
        Trackers tracker;
        key = key.toLowerCase();
        tracker = setTracker(key);
        goals.put(key, tracker);
    }

    // EFFECTS: Returns a tracker based on the type given
    public Trackers setTracker(String type) throws KeyError {
        switch (type) {
            case "water":
                return new WaterTracker();
            case "sleep":
                return new SleepTracker();
            case "mood":
                return new MoodTracker();
            case "journal":
                return new JournalingTracker();
            case "meditation":
                return new MeditationTracker();
            default:
                throw new KeyError();
        }
    }


    public boolean getAllTargetMet() {
        return allTargetMet;
    }

    public void setAllTargetMet() {
        for (String k : goalsAlreadySet()) {
            Trackers tracker = getTracker(k);

            if (tracker.targetMet()) {
                allTargetMet = true;
            } else {
                allTargetMet = false;
                break;
            }
        }
    }

    // REQUIRES: key is in the set
    // MODIFIES: this
    // EFFECTS: removes an already existing goal
    public void removeTracker(String key) {
        goals.remove(key);
    }

    // EFFECTS: provides number goals which have been set
    public int noOfTrackersSet() {
        return goals.size();
    }

    // REQUIRES: the key already exists in the set
    // EFFECTS: returns the Tracker for a particular goal
    public Trackers getTracker(String key) {
        return goals.get(key);
    }

    // EFFECTS: provides the list of all the trackers which have been set
    public List<String> goalsAlreadySet() {
        return new ArrayList<>(goals.keySet());
    }

    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("trackers", goalsToJson());
        return json;
    }

    // EFFECTS: returns trackers in this goals as a JSON array
    private JSONArray goalsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (String k : goalsAlreadySet()) {
            Trackers tracker = getTracker(k);
            jsonArray.put(tracker.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: Creates a copy of another Goals with progress set to 0 for all trackers
    public Goals copyLastGoal() throws KeyError {
        Goals newGoals = new Goals();
        for (String k : goalsAlreadySet()) {
            Trackers tracker = getTracker(k);
            int target = tracker.getTarget();
            newGoals.addGoal(tracker.getType());
            newGoals.getTracker(k).setTarget(target);
        }
        return newGoals;
    }
}

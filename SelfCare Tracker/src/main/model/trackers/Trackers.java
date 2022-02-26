package model.trackers;

import org.json.JSONObject;

// It is the tracker which stores the target and progress that the user makes
public abstract class Trackers {
    protected int target;
    protected int progress;
    protected String units;
    protected JSONObject json;
    protected String type;

    // EFFECTS: creates a new tracker with the progress set to 0
    public Trackers() {
        progress = 0;
    }

    // EFFECTS: creates a tracker with the fields already set
    public void setTracker(String name, int target, int progress, String units) {
        this.type = name;
        this.target = target;
        this.progress = progress;
        this.units = units;
    }

    public int getTarget() {
        return target;
    }

    public int getProgress() {
        return progress;
    }

    // EFFECTS: returns the type of the tracker
    public abstract String getType();

    // EFFECTS: returns the units for the value that each tracker tracks
    public abstract String getUnits();

    // EFFECTS: returns the units for the value that each tracker tracks according value entered
    public abstract String getUnits(int value);

    // MODIFIES: this
    // EFFECTS: sets the target for the particular goal if it meets the conditions
    public void setTarget(int target) {
        this.target = target;
    }

    // MODIFIES: this
    // EFFECTS: updates the progress of the goal
    public void setProgress(int progress) {
        this.progress += progress;
    }

    // EFFECTS: checks if the progress has met the target
    public boolean targetMet() {
        return (getProgress() >= getTarget());
    }

    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        json = new JSONObject();
        json.put("type", getType());
        json.put("target", target);
        json.put("progress", progress);
        json.put("units", getUnits());
        return json;
    }
}

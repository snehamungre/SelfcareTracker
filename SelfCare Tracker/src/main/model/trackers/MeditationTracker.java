package model.trackers;

// tracker for meditation/relaxation
public class MeditationTracker extends Trackers {
    public String getUnits() {
        return "minutes";
    }

    public String getUnits(int value) {
        return getUnits();
    }

    @Override
    public String getType() {
        return "meditation";
    }
}

package model.trackers;

// tracker for numbers of hours of sleep obtained
public class SleepTracker extends Trackers {

    @Override
    public String getUnits() {
        return "hours";
    }

    @Override
    public String getUnits(int value) {
        return getUnits();
    }

    @Override
    public String getType() {
        return "sleep";
    }

    // MODIFIES: progress
    // EFFECTS: updates the value rather than adding the value to progress
    @Override
    public void setProgress(int progress) {
        this.progress = progress;
    }
}

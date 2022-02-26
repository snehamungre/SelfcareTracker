package model.trackers;

// represents a tracker for journalling
public class JournalingTracker extends Trackers {

    public String getUnits() {
        return "times";
    }

    @Override
    public String getUnits(int value) {
        return getUnits();
    }

    @Override
    public String getType() {
        return "journal";
    }

}

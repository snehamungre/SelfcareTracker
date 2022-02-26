package model.trackers;

// tracker for water consumption
public class WaterTracker extends Trackers {

    public String getUnits() {
        return "ml";
    }

    @Override
    public String getUnits(int value) {
        return getUnits();
    }

    @Override
    public String getType() {
        return "water";
    }

}

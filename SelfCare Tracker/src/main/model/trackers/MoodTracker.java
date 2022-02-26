package model.trackers;

// allows users to pick their mood for the day
public class MoodTracker extends Trackers {

    public String getUnits() {
        return "\n :)   (1) \n :/   (2)  \n :(   (3)";
    }

    @Override
    public String getUnits(int val) {
        if (val == 1) {
            return ":)";
        } else if (val == 2) {
            return ":/";
        } else {
            return ":(";
        }
    }

    @Override
    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public String getType() {
        return "mood";
    }

    //EFFECTS: checks if the mood matches the target set
    @Override
    public boolean targetMet() {
        return getTarget() == getProgress();
    }

}

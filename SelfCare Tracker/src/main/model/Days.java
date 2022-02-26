package model;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// implementation of Goals over multiple days
public class Days {
    private final List<Goals> days;

    public Days() {
        days = new ArrayList<>();
    }

    // EFFECTS: Returns the number of days in the self-care tracker
    public Integer getNoOfDays() {
        return days.size();
    }

    //EFFECTS: adds a new goal tracker to the list
    public void addDay(Goals goal) {
        days.add(goal);
    }

    //EFFECTS: adds a new goal tracker to the list
    public void addDay() {
        Goals goals = days.get(days.size() - 1);
        try {
            days.add(goals.copyLastGoal());
        } catch (KeyError keyError) {
            //fail
        }
    }


    //EFFECTS: returns the goal for the given day
    public Goals getADay(int i) {
        days.get(i).setAllTargetMet();
        return days.get(i);
    }

    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("days", daystoJson());
        return json;
    }

    // EFFECTS: returns trackers in this goals as a JSON array
    public JSONArray daystoJson() {
        Goals goal;
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < getNoOfDays(); i++) {
            goal = days.get(i);
            jsonArray.put(goal.toJson());
        }
        return jsonArray;
    }
}

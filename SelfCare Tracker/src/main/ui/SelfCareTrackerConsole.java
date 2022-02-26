package ui;

import model.Days;
import model.Goals;
import model.KeyError;
import model.trackers.Trackers;
import persistance.JsonReader;
import persistance.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// the self-care tracker app
public class SelfCareTrackerConsole {
    private static final String JSON_STORE = "./data/selfCareTracker.json";
    private Days days;
    private Trackers tracker;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: constructs self-care tracker and an empty set of trackers
    public SelfCareTrackerConsole() {
        setUp();
        runTracker();
    }

    // EFFECTS: initializes the values required
    private void setUp() {
        days = new Days();
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runTracker() {
        loadSelfCareTracker();
        int value;
        do {
            displayMenu();
            value = Integer.valueOf(input.next());
            processCommand(value);
        } while (value != 0);
    }

    // EFFECTS: shows the menu to users
    private void displayMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("Edit progress for the previous days - (1)");
        System.out.println("Add another day to the self-care tracker - (2)");
        System.out.println("View the progress of all the days - (3)");
        System.out.println(" ");
        System.out.println("To exit and save the tracker, enter '0'");
    }

    // MODIFIES: days
    // EFFECTS: processes user command
    private void processCommand(int value) {
        Goals goals;
        if (value == 2) {
            try {
                goals = addGoalToDays();
                editGoal(goals);
            } catch (KeyError keyError) {
                System.out.println("Did not add new goals correctly");
            }
        } else if (value == 1) {
            goals = daysAlreadySet();
            editGoal(goals);
        } else if (value == 3) {
            viewProgressForAllDays();
        } else {
            quitAndSave();
        }
    }

    private void editGoal(Goals goal) {
        int value;
        do {
            displayMenuForGoals();
            value = Integer.valueOf(input.next());
            processCommandForGoals(value, goal);
        } while (value != 6);
    }

    private void quitAndSave() {
        System.out.println("Do you want to save progress:\n'Y' or 'N'");
        String in = input.next();
        if (in.equalsIgnoreCase("Y")) {
            saveSelfCareTracker();
        }
        System.out.println("Quitting...");
    }

    // EFFECTS: Shows the user the progress for all their goals over multiple days
    private void viewProgressForAllDays() {
        for (int i = 0; i < days.getNoOfDays(); i++) {
            System.out.println("\nProgress for Day " + (i + 1) + ":");
            Goals goals = days.getADay(i);
            viewProgress(goals);
        }
    }

    // MODIFIES: Goals
    // EFFECTS: Allows users to change the values for days for which the goals have already been set
    private Goals daysAlreadySet() {
        System.out.println("Enter which of the following days do you want to edit:");
        for (int i = 0; i < days.getNoOfDays(); i++) {
            System.out.println("- Edit Day (" + (i + 1) + ")");
        }
        int value = Integer.valueOf(input.next()) - 1;
        return days.getADay(value);
    }

    //EFFECTS: if the self tracker is new adds a new Goals
    //         else adds a copy of the last Goals with all the progress set to 0
    private Goals addGoalToDays() throws KeyError {
        Goals newGoal;
        if (days.getNoOfDays() == 0) {
            newGoal = new Goals();
        } else {
            newGoal = days.getADay(days.getNoOfDays() - 1).copyLastGoal();
        }
        days.addDay(newGoal);
        return newGoal;
    }


    // EFFECTS: shows the menu to users
    private void displayMenuForGoals() {
        System.out.println("\nTo add a goal - (1)");
        System.out.println("To remove a goal - (2)");
        System.out.println("To update the progress of a goal - (3)");
        System.out.println("To view the progress of all of the goals for the day - (4)");
        System.out.println("To change the target of a goal - (5)");
        System.out.println(" ");
        System.out.println("To return to the main menu, press '6'");
        System.out.println(" ");
        System.out.println("To exit and save the tracker, enter '0'");
    }

    // MODIFIES: Goals
    // EFFECTS: processes user command
    private void processCommandForGoals(int value, Goals goals) {
        if (value == 1) {
            addGoalToTracker(goals);
        } else if (value == 2) {
            removeTrackerFromGoals(goals);
        } else if (value == 3) {
            updateProgressOfGoal(goals);
        } else if (value == 4) {
            viewProgress(goals);
        } else if (value == 5) {
            changeTarget(goals);
        } else if (value == 6) {
            System.out.println(" ");
        } else {
            quitAndSave();
        }
    }


    // REQUIRES: no duplicates can be made of each tracker
    // MODIFIES: Goals, Trackers.target
    // EFFECTS: creates a new goal with a target
    public void addGoalToTracker(Goals goals) {
        defaultGoals();

        String key = input.next();
        key = key.toLowerCase();

        if (!goals.goalsAlreadySet().contains(key)) {
            try {
                goals.addGoal(key);
                tracker = goals.getTracker(key);
                setTarget(tracker, key);
            } catch (KeyError e) {
                System.out.println("Incorrect key entered!");
            }
        }
    }

    // MODIFIES: Trackers.target
    // EFFECTS: updates the target for a given tracker
    public void setTarget(Trackers tracker, String key) {
        System.out.println("Set a value for the target in " + tracker.getUnits());
        int target = input.nextInt();

        tracker.setTarget(target);
        System.out.println("The target of " + key + " is set as " + target + " " + tracker.getUnits(target));
    }


    // REQUIRES: the goal is already set
    // MODIFIES: Goals
    // EFFECTS: removes a goal
    public void removeTrackerFromGoals(Goals goals) {
        System.out.println("Enter which of the following goals do you want to remove:");
        goals.goalsAlreadySet().forEach((g) -> System.out.println("- Remove " + g));

        String key = input.next();
        key = key.toLowerCase();
        if (goals.goalsAlreadySet().contains(key)) {
            goals.removeTracker(key);
            System.out.println("Tracker " + key + " has been removed!");
        } else {
            System.out.println("Incorrect key entered!");
        }
    }

    // MODIFIES: Trackers.progress
    // EFFECTS: shows the target and process for each of the goals set
    public void updateProgressOfGoal(Goals goals) {
        System.out.println("The Goals which have been set:");
        goals.goalsAlreadySet().forEach((g) -> System.out.println("* Update " + g));

        String key = input.next();
        key = key.toLowerCase();
        try {
            tracker = goals.getTracker(key);

            System.out.println("Progress to add ");
            int value = Integer.valueOf(input.next());

            tracker.setProgress(value);
            goals.setAllTargetMet();
            int target = tracker.getTarget();
            System.out.print("The progress for " + key + " is " + tracker.getProgress() + " ");
            System.out.println(tracker.getUnits(value));
            System.out.println("The target for " + key + " = " + target + " " + tracker.getUnits(target));
        } catch (Exception e) {
            System.out.println("Incorrect key entered!");
        }
    }

    // REQUIRES: the goal is already set
    // EFFECTS: allows the user to change the target of a goal which is already set
    private void changeTarget(Goals goals) {
        System.out.println("Enter which of the following goals do you want to change target for:");
        goals.goalsAlreadySet().forEach((g) -> System.out.println("- Change " + g));

        String key = input.next();
        key = key.toLowerCase();

        if (goals.goalsAlreadySet().contains(key)) {
            tracker = goals.getTracker(key);
            setTarget(tracker, key);
        } else {
            System.out.println("Incorrect key entered!");
        }
    }

    // EFFECTS: prints out goals have been met and the progress for the rest of the goals
    public void viewProgress(Goals goals) {
        System.out.println("The progress so far is: ");

        for (String k : goals.goalsAlreadySet()) {
            tracker = goals.getTracker(k);
            int target = tracker.getTarget();

            if (tracker.targetMet()) {
                System.out.println("\t:) Daily target met for " + k);
            } else {
                System.out.print("\t:( Progress for " + k + " is " + tracker.getProgress() + " out of ");
                System.out.println(target + " " + tracker.getUnits(target));
            }
        }
    }

    // EFFECT: prints out a list of the default trackers which can be added to the self-care tracker
    public static void defaultGoals() {
        System.out.println("Enter one of the following values to set up a tracker for it:");
        System.out.println("\t + water");
        System.out.println("\t + sleep");
        System.out.println("\t + mood");
        System.out.println("\t + meditation");
        System.out.println("\t + journaling");
    }

    // EFFECTS: saves the days to JSON file
    private void saveSelfCareTracker() {
        try {
            jsonWriter.open();
            jsonWriter.write(days);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads days from file
    private void loadSelfCareTracker() {
        try {
            days = jsonReader.read();
        } catch (IOException | KeyError e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
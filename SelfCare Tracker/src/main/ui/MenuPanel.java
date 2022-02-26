package ui;

import model.Days;
import model.Goals;
import model.KeyError;
import model.trackers.Trackers;
import persistance.JsonReader;
import persistance.JsonWriter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.awt.BorderLayout.CENTER;

// Menu Panel which contains all the buttons for the GUI
public class MenuPanel extends JPanel {
    private static final String JSON_STORE = "./data/selfCareTracker.json";
    private DisplayPanel dp;
    private Sound sound;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JPanel mainMenu;
    private JPanel goalsMenu;
    private JPanel trackersMenu;

    //buttons
    private JButton editDay;
    private JButton addDay;
    private JButton viewAll;

    private JButton addGoal;
    private JButton updateProgress;
    private JButton updateTarget;
    private JButton deleteTracker;
    private JButton viewGoal;
    private JButton backToMain;
    private JButton enterButton;
    private JButton backToGoal;


    private Days days;
    private List allTrackers;
    private String trackerSelected;
    private String actionTracker;
    private Integer userValue;

    public MenuPanel() {
        setUp();
        days = new Days();
        loadSelfCareTracker();
        mainMenu();
        goalsMenu();
    }

    // EFFECTS: initializes the fields required for the Self Care Tracker
    private void setUp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        userValue = 0;
        mainMenu = new JPanel();
        goalsMenu = new JPanel();
        trackersMenu = new JPanel();
        backToGoal = new JButton();
        enterButton = new JButton("Enter");
        allTrackers = new ArrayList();
        sound = new Sound();
    }

    //getters and setters
    public JPanel getMainMenu() {
        return mainMenu;
    }

    public Days getDays() {
        return days;
    }

    public void setDisplayPanel(DisplayPanel displayPanel) {
        if (!displayPanel.equals(dp)) {
            dp = displayPanel;
        }
    }

    //EFFECTS: arranges all buttons for the main menu
    private void mainMenu() {
        mainMenu.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 30));
        mainMenu.setLayout(new GridLayout(5, 1));

        JLabel title = new JLabel("Main Menu:");
        title.setBounds(10, 20, 80, 20);
        mainMenu.add(title);

        editDay = new JButton("Edit progress for the previous day");
        editDay.setBounds(10, 40, 40, 20);
        mainMenu.add(editDay);

        addDay = new JButton("Add another day to the self-care tracker");
        addDay.setBounds(10, 60, 40, 20);
        mainMenu.add(addDay);

        viewAll = new JButton("View the progress of all the days");
        viewAll.setBounds(10, 80, 40, 20);
        mainMenu.add(viewAll);
    }

    //EFFECTS: arranges all buttons for the goals menu
    private void goalsMenu() {
        goalsMenu.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        goalsMenu.setLayout(new GridLayout(7, 1));

        backToMain = new JButton();
        backToMain.setBounds(10, 0, 30, 37);
        backButtonSetup(backToMain);
        goalsMenu.add(backToMain);

        JLabel title = new JLabel("Goals Menu:");
        title.setBounds(10, 20, 30, 20);
        goalsMenu.add(title);

        goalsMenuButtons();
    }

    //EFFECTS: initializes all the goal menu buttons
    private void goalsMenuButtons() {
        addGoal = new JButton("Add a goal");
        addGoal.setIcon(new ImageIcon("data/images/addIcon.png"));
        addGoal.setBounds(10, 40, 50, 20);
        goalsMenu.add(addGoal);

        updateProgress = new JButton("Update the progress of a goal");
        updateProgress.setBounds(10, 60, 30, 20);
        goalsMenu.add(updateProgress);

        updateTarget = new JButton("Change the target of a goal");
        updateTarget.setBounds(10, 80, 30, 20);
        goalsMenu.add(updateTarget);

        deleteTracker = new JButton("Remove a tracker");
        deleteTracker.setIcon(new ImageIcon("data/images/removeIcon.png"));
        deleteTracker.setBounds(10, 120, 50, 20);
        goalsMenu.add(deleteTracker);

        viewGoal = new JButton("View the progress");
        viewGoal.setBounds(10, 160, 30, 20);
        goalsMenu.add(viewGoal);
    }

    //EFFECTS: sets up all the buttons for the tracker menu
    private void trackerMenu(JPanel card1, JPanel card2) {
        trackersMenu.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 30));
        trackersMenu.setLayout(new BorderLayout());

        JPanel main = new JPanel(new FlowLayout());
        main.add(card1);
        main.add(card2);

        trackersMenu.add(backButtonSetup(backToGoal), BorderLayout.NORTH);
        trackersMenu.add(main, CENTER);
        trackersMenu.add(enterButton, BorderLayout.PAGE_END);
    }

    //EFFECTS: sets up all the buttons for changing a tracker's progress
    private void trackersProgressMenu() {
        trackersMenu.removeAll();
        JPanel card1 = new JPanel();
        JPanel card2 = new JPanel(new GridLayout());

        JComboBox trackerMenu = changeTrackersSet();
        card1.add(trackerMenu);

        JLabel updateProgress = new JLabel("Change Progress To:");
        updateProgress.setBounds(10, 20, 80, 20);

        JTextField updateProgressBox = new JTextField();
        actionTracker = "progress";

        selectingTrackerMenu(trackerMenu, updateProgressBox);

        card2.add(updateProgress);
        card2.add(updateProgressBox);
        trackerMenu(card1, card2);
    }

    //EFFECTS: sets up all the buttons for adding a new tracker
    private void trackersAddMenu() {
        trackersMenu.removeAll();
        JPanel card1 = new JPanel();
        JPanel card2 = new JPanel(new GridLayout());

        actionTracker = "add";

        JComboBox trackerMenu = addTrackersMenu();

        card1.add(trackerMenu);
        JLabel addTarget = new JLabel("Set the Target:");
        JTextField target = new JTextField();

        selectingTrackerMenu(trackerMenu, target);

        card2.add(addTarget);
        card2.add(target);

        trackerMenu(card1, card2);
    }

    //EFFECTS: sets up all the buttons for changing a tracker's target
    private void trackersTargetMenu() {
        trackersMenu.removeAll();
        JPanel card1 = new JPanel();
        JPanel card2 = new JPanel(new GridLayout());

        JComboBox targetTrackerMenu = changeTrackersSet();

        card1.add(targetTrackerMenu);

        JLabel updateTarget = new JLabel("Change Target To:");
        updateTarget.setBounds(10, 20, 80, 20);

        JTextField changeTarget = new JTextField();
        actionTracker = "target";

        selectingTrackerMenu(targetTrackerMenu, changeTarget);

        card2.add(updateTarget);
        card2.add(changeTarget);
        trackerMenu(card1, card2);
    }

    //EFFECTS: sets up all the buttons for removing an existing tracker
    private void trackersRemoveTarget() {
        trackersMenu.removeAll();
        JPanel card1 = new JPanel();
        JPanel card2 = new JPanel(new GridLayout());

        JComboBox targetTrackerMenu = changeTrackersSet();
        targetTrackerMenu.addActionListener(ae -> {
            trackerSelected = (String) targetTrackerMenu.getSelectedItem();
            actionTracker = "remove";
        });
        JLabel updateTarget = new JLabel("Remove Tracker:");
        updateTarget.setBounds(10, 20, 80, 20);

        card2.add(targetTrackerMenu);
        trackerMenu(card1, card2);
    }

    //EFFECTS: adding Action events to all textFields and comboBox
    private void selectingTrackerMenu(JComboBox comboBox, JTextField textField) {
        comboBox.addActionListener(e -> trackerSelected = String.valueOf(comboBox.getSelectedItem()));

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                userValue = Integer.parseInt((textField.getText()));
            }
        });
    }

    //EFFECTS: Adds listeners to the main menu buttons
    public void listenerMainMenu(JPanel frame) {
        editDayListener(frame);
        addDayListener(frame);
        viewAllListener(frame);
    }

    //EFFECTS: Adds listeners to the goals menu buttons
    public void listenerGoalsMenu(JPanel frame) {
        addGoalListener(frame);
        updateProgressListener(frame);
        updateTargetListener(frame);
        viewGoalListener(frame);
        deleteTrackerListener(frame);
        backToMainListener(frame);

    }

    //EFFECTS: Adds listeners to the tracker menu buttons and text fields
    public void listenerTrackerMenu(JPanel frame) {
        backToGoalListener(frame);
        enterActionTracker(frame);
    }

    // EFFECTS: Adds listener to the back button to go back to the goals menu
    private void backToGoalListener(JPanel frame) {
        backToGoal.addActionListener(ae -> {
            frame.remove(trackersMenu);
            frame.add(goalsMenu);
            frame.revalidate();
            frame.repaint();
        });
    }

    // EFFECTS: Adds listener to the edit day button
    private void editDayListener(JPanel frame) {
        editDay.addActionListener(e -> {
            sound.play();
            dp.defaultDisplay();
            frame.remove(mainMenu);
            frame.add(goalsMenu);
            Goals goals = days.getADay(days.getNoOfDays() - 1);
            dp.addToDisplay(dp.displayGoals(goals));
            frame.revalidate();
            frame.repaint();
        });
    }

    //EFFECTS: adds effect for viewing goals for all days
    private void viewAllListener(JPanel frame) {
        viewAll.addActionListener(ae -> {
            saveSelfCareTracker();
            dp.defaultDisplay();
            Component c = dp.displayDays();
            dp.addToDisplay(c);
            frame.revalidate();
            frame.repaint();
        });
    }

    // MODIFIES: days
    //EFFECTS: adds a new day with the same trackers as the day before
    private void addDayListener(JPanel frame) {
        addDay.addActionListener(ae -> {
            sound.play();
            updateNotSaved();
            dp.defaultDisplay();
            days.addDay();
            frame.remove(mainMenu);
            frame.add(goalsMenu);
            Goals goals = days.getADay(days.getNoOfDays() - 1);
            dp.addToDisplay(dp.displayGoals(goals));
            saveSelfCareTracker();
            frame.revalidate();
            frame.repaint();
        });
    }


    //EFFECTS: adds functionality to go back to main menu
    private void backToMainListener(JPanel frame) {
        backToMain.addActionListener(ae -> {
            frame.remove(goalsMenu);
            frame.add(mainMenu);
            frame.revalidate();
            frame.repaint();
            dp.defaultDisplay();
            saveSelfCareTracker();
        });
    }

    // MODIFIES: goals
    //EFFECTS: removes a already set tracker
    private void deleteTrackerListener(JPanel frame) {
        deleteTracker.addActionListener(ae -> {
            updateNotSaved();
            trackersRemoveTarget();
            frame.remove(goalsMenu);
            frame.add(trackersMenu);
            Goals goals = days.getADay(days.getNoOfDays() - 1);
            dp.addToDisplay(dp.displayGoals(goals));
            frame.revalidate();
            frame.repaint();
        });
    }

    //EFFECTS: view goals on the display panel
    private void viewGoalListener(JPanel frame) {
        viewGoal.addActionListener(ae -> {
            Goals goals = days.getADay(days.getNoOfDays() - 1);
            dp.addToDisplay(dp.displayGoals(goals));
            frame.revalidate();
            frame.repaint();
        });
    }

    //EFFECTS: updates target of the selected tracker
    private void updateTargetListener(JPanel frame) {
        updateTarget.addActionListener(ae -> {
            updateNotSaved();
            trackersTargetMenu();
            Goals goals = days.getADay(days.getNoOfDays() - 1);
            dp.addToDisplay(dp.displayGoals(goals));
            frame.remove(goalsMenu);
            frame.add(trackersMenu);
            frame.revalidate();
            frame.repaint();
        });
    }

    //EFFECTS: updates progress of the selected tracker
    private void updateProgressListener(JPanel frame) {
        updateProgress.addActionListener(ae -> {
            updateNotSaved();
            trackersProgressMenu();
            frame.remove(goalsMenu);
            frame.add(trackersMenu);
            frame.revalidate();
            frame.repaint();
        });
    }

    //EFFECTS: adds a new tracker to goal
    private void addGoalListener(JPanel frame) {
        addGoal.addActionListener(ae -> {
            actionTracker = "add";
            updateNotSaved();
            trackersAddMenu();
            frame.remove(goalsMenu);
            frame.add(trackersMenu);
            frame.revalidate();
            frame.repaint();
        });
    }

    // EFFECTS: takes in user input
    private void enterActionTracker(JPanel frame) {
        enterButton.addActionListener(ae -> {
            dp.defaultDisplay();
            Goals goals = days.getADay(days.getNoOfDays() - 1);
            modifyTracker();
            dp.addToDisplay(dp.displayGoals(goals));
            saveSelfCareTracker();
            frame.remove(trackersMenu);
            frame.add(goalsMenu);
            frame.revalidate();
            frame.repaint();
        });
    }

    //EFFECTS: performs the action on the selected tracker
    private void modifyTracker() {
        Trackers t;
        Goals goals = days.getADay(days.getNoOfDays() - 1);
        if (trackerSelected == null) {
            trackerSelected = goals.goalsAlreadySet().get(0);
        }
        switch (actionTracker) {
            case "add":
                addToGoals(goals);
                break;
            case "progress":
                t = goals.getTracker(trackerSelected);
                t.setProgress(userValue);
                break;
            case "target":
                t = goals.getTracker(trackerSelected);
                t.setTarget(userValue);
                break;
            case "remove":
                goals.removeTracker(trackerSelected);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + actionTracker);
        }
    }

    // MODIFIES: goals
    // EFFECTS: Adds the tracker selected to goals
    private void addToGoals(Goals goals) {
        try {
            goals.addGoal(trackerSelected);
            Trackers t = goals.getTracker(trackerSelected);
            t.setTarget(userValue);
        } catch (KeyError keyError) {
            System.out.println("no tracker was added!");
        }
    }

    //EFFECTS: sets up the back button
    private JButton backButtonSetup(JButton button) {
        ImageIcon backButtonImage = new ImageIcon("data/images/backButton.png");
        button.setIcon(backButtonImage);
        button.setSize(20, 30);
        return button;
    }

    //EFFECTS: provides users the option to add trackers which have not been added
    private JComboBox addTrackersMenu() {
        List toAdd = allTrackers();
        Goals goals = days.getADay(days.getNoOfDays() - 1);
        List alreadyAdded = goals.goalsAlreadySet();
        toAdd.removeAll(alreadyAdded);
        String[] canAdd = new String[toAdd.size()];
        toAdd.toArray(canAdd);
        return new JComboBox(canAdd);
    }

    //EFFECTS: list of all the trackers which can be added
    private List allTrackers() {
        allTrackers.add("water");
        allTrackers.add("journal");
        allTrackers.add("sleep");
        allTrackers.add("mood");
        allTrackers.add("meditation");
        return allTrackers;
    }

    //EFFECTS: provides users the option to change trackers which have been added
    private JComboBox changeTrackersSet() {
        Goals goals = days.getADay(days.getNoOfDays() - 1);
        List<String> change = goals.goalsAlreadySet();
        Object[] canChange;
        canChange = change.toArray();
        return new JComboBox(canChange);
    }

    // MODIFIES: displayPanel
    // EFFECTS: changes the save status of the file when changes are being made
    private void updateNotSaved() {
        dp.setSaved(false);
        dp.saveStatus();
    }

    // EFFECTS: saves the days to JSON file
    public void saveSelfCareTracker() {
        try {
            jsonWriter.open();
            jsonWriter.write(days);
            jsonWriter.close();
            dp.setSaved(true);
            dp.saveStatus();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads days from file
    public void loadSelfCareTracker() {
        try {
            days = jsonReader.read();
        } catch (IOException | KeyError e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuPanel)) {
            return false;
        }
        MenuPanel menuPanel = (MenuPanel) o;
        return mainMenu.equals(menuPanel.mainMenu)
                && goalsMenu.equals(menuPanel.goalsMenu)
                && trackersMenu.equals(menuPanel.trackersMenu)
                && Objects.equals(days, menuPanel.days)
                && Objects.equals(allTrackers, menuPanel.allTrackers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mainMenu, goalsMenu, trackersMenu, days, allTrackers);
    }


    // Class which initializes the sound of clicking buttons
    private static class Sound {
        private Clip clip;

        private Sound() {
            try {
                File clickSoundFile = new File("data/audio/clickSound.wav");
                AudioInputStream clickSound = AudioSystem.getAudioInputStream(clickSoundFile);
                clip = AudioSystem.getClip();
                clip.open(clickSound);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // EFFECTS: plays the given sound from the file
        public void play() {
            clip.setFramePosition(0);
            clip.start();
        }

    }
}

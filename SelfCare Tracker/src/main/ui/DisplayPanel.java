package ui;

import model.*;
import model.trackers.*;

import javax.swing.*;
import java.awt.*;

// Display Panel which shows all the days, goals and trackers which are being used
public class DisplayPanel extends JPanel {
    private JPanel displayBox;
    private MenuPanel mp;
    private JPanel targetLine;
    private JScrollPane scroll;

    private JLabel saveStatus;
    private boolean saved;

    private Icon tick;
    private Icon notChecked;
    private Icon savedIcon;
    private Icon calender;


    public DisplayPanel(int width, int height) {
        displayBox = new JPanel();
        saveStatus = new JLabel();
        saveStatus.setBorder(BorderFactory.createEmptyBorder(3, 5, 7, 2));
        displayBox.setSize(width, height);
        displayBox.setLayout(new BorderLayout());
        displayBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setIcons();
        saved = true;
    }

    //setters and getters
    public void setMenuPanel(MenuPanel menuPanel) {
        if (mp == null || !mp.equals(menuPanel)) {
            mp = menuPanel;
        }
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public JPanel getDP() {
        return displayBox;
    }

    // EFFECTS: sets up the main components for the startup screen
    public void mainDisplay() {
        saveStatus();
        displayBox.add(saveStatus, BorderLayout.PAGE_START);
        addToDisplay((displayDays()));
    }

    // EFFECTS: adds the given component to the display
    public void addToDisplay(Component component) {
        scroll = new JScrollPane(component);
        scroll.setVerticalScrollBarPolicy(22);
        displayBox.add(scroll, BorderLayout.CENTER);
        displayBox.setVisible(true);
        displayBox.revalidate();
        displayBox.repaint();
    }

    // EFFECTS: loads the default display for the display panel
    public void defaultDisplay() {
        displayBox.removeAll();
        mainDisplay();
        scroll.removeAll();
    }

    // EFFECTS: shows the status of saving the trackers added so far
    public void saveStatus() {
        if (!saved) {
            saveStatus.setText("not saved");
            saveStatus.setIcon(new ImageIcon("data/images/notSave.png"));
        } else {
            saveStatus.setText("saved");
            saveStatus.setIcon(savedIcon);
        }
    }

    //EFFECTS: Displays the values for each tracker
    public JPanel displayTarget(Trackers tracker) {
        targetLine = new JPanel(new FlowLayout());
        String type = tracker.getType();
        int target = tracker.getTarget();
        int progress = tracker.getProgress();

        String message;
        JLabel value;

        if (tracker.getType().equals("mood")) {
            message = "Mood Today " + tracker.getUnits(tracker.getProgress());
            Icon i;
            i = getMoodIcon(progress);
            value = new JLabel(message);
            value.setIcon(i);
        } else if (tracker.targetMet()) {
            value = trackerTargetMet(tracker, type, target, progress);
        } else {
            value = trackerTargetFailed(tracker, type, target, progress);
        }
        targetLine.add(value);
        targetLine.setBackground(Color.WHITE);
        return targetLine;
    }

    //EFFECTS: returns the icon associated with the mood user entered
    private Icon getMoodIcon(int progress) {
        Icon i;
        if (progress == 1) {
            i = new ImageIcon("data/images/happyIcon.png");
        } else if (progress == 2) {
            i = new ImageIcon("data/images/okayIcon.png");
        } else {
            i = new ImageIcon("data/images/sadIcon.png");
        }
        return i;
    }

    //EFFECTS: Creates a JLabel with graphics for trackers which didn't meet their targets
    private JLabel trackerTargetFailed(Trackers tracker, String type, int target, int progress) {
        JLabel value;
        String message;
        message = "\tProgress for " + type + " is " + progress + " out of " + target + " "
                + tracker.getUnits(target);
        value = new JLabel(message);
        value.setIcon(null);
        value.setIcon(notChecked);
        return value;
    }

    //EFFECTS: Creates a JLabel with graphics for trackers which did meet their targets
    private JLabel trackerTargetMet(Trackers tracker, String type, int target, int progress) {
        JLabel value;
        String message;
        message = "Daily target met for " + type
                + ". Completed " + progress + " out of " + target + " ";
        value = new JLabel(message);
        value.setIcon(null);
        value.setIcon(tick);
        return value;
    }

    // EFFECTS: Displays all the targets which have been set out
    public Container displayGoals(Goals goals) {
        Box goalPanel = new Box(BoxLayout.PAGE_AXIS);
        for (String k : goals.goalsAlreadySet()) {
            Trackers trackers = goals.getTracker(k);
            goalPanel.add(displayTarget(trackers), LEFT_ALIGNMENT);
        }
        return goalPanel;
    }

    // EFFECTS: Displays all the days starting from the most first entry to most recent
    public Container displayDays() {
        Days days = mp.getDays();
        Box daysContainer = new Box(BoxLayout.PAGE_AXIS);
        for (int i = 0; i < days.getNoOfDays(); i++) {
            JLabel dayCount = new JLabel();
            dayCount.setIcon(calender);
            String count = "Day " + (i + 1);
            dayCount.setText(count);

            Goals goals = days.getADay(i);

            daysContainer.add(dayCount, LEFT_ALIGNMENT);
            daysContainer.add(displayGoals(goals), LEFT_ALIGNMENT);
        }
        return daysContainer;
    }

    //EFFECTS: Creates all the icons which are used in the display panel
    private void setIcons() {
        tick = new ImageIcon("data/images/tick.png");
        notChecked = new ImageIcon("data/images/notChecked.png");
        savedIcon = new ImageIcon("data/images/savedIcon.gif");
        calender = new ImageIcon("data/images/calenderIcon.png");
    }
}

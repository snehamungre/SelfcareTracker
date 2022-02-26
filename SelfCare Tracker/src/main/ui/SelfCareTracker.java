package ui;

import javax.swing.*;
import java.awt.*;

// Contains all the parts of the self tracker app and creates the GUI
public class SelfCareTracker extends JFrame {
    private final int widthFrame = 1000;
    private final int heightFrame = 350;
    private JFrame selfCareTracker;
    private MenuPanel mp;
    private DisplayPanel dp;

    public SelfCareTracker() {
        setUp();
        dp.saveStatus();

        JPanel layoutFrame = new JPanel();
        layoutFrame.setLayout(new GridLayout());
        JPanel display = dp.getDP();
        dp.mainDisplay();

        layoutFrame.add(display);

        JPanel mainMenu = mp.getMainMenu();
        layoutFrame.add(mainMenu);
        loadMenu(layoutFrame);

        selfCareTracker.add(layoutFrame, BorderLayout.CENTER);

        selfCareTracker.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                mp.saveSelfCareTracker();
                System.exit(0);
            }
        });

        selfCareTracker.pack();
        selfCareTracker.setVisible(true);
    }

    //EFFECTS: Initialize all the values required for selfCareTracker
    private void setUp() {
        selfCareTracker = new JFrame("Self Care Tracker");
        selfCareTracker.setPreferredSize(new Dimension(widthFrame, heightFrame));
        selfCareTracker.setLayout(new BorderLayout());

        mp = new MenuPanel();
        mp.loadSelfCareTracker();
        dp = new DisplayPanel(widthFrame, heightFrame);
        dp.setMenuPanel(mp);
        mp.setDisplayPanel(dp);
    }

    private void loadMenu(JPanel f) {
        mp.listenerMainMenu(f);
        mp.listenerGoalsMenu(f);
        mp.listenerTrackerMenu(f);
    }

    public static void main(String[] args) {
        new SelfCareTracker();
    }

}

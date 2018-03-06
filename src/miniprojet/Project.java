package miniprojet;

import miniprojet.ui.MainView;

import javax.swing.*;

/**
 * @author Axel LE BOT
 */
class Project {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        MainView application = new MainView();
        application.run();
    }
}

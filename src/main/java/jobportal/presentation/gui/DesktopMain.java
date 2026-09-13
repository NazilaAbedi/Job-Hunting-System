package jobportal.presentation.gui;

import jobportal.config.ApplicationContext;
import javax.swing.*;

public final class DesktopMain {
    private DesktopMain() {}
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ApplicationContext context = new ApplicationContext();
            context.seedDemoData();
            new JobPortalFrame(context).setVisible(true);
        });
    }
}


package jobportal.presentation.cli;

import jobportal.presentation.gui.DesktopMain;

public class Main {
    public static void main(String[] args) {
        if (args.length > 0 && "--cli".equalsIgnoreCase(args[0])) {
            App app = new App();
            app.seed();
            app.start();
            return;
        }
        DesktopMain.main(args);
    }
}


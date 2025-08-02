package org.example.views.strategies;

import org.example.views.CLIFlow;
import org.example.views.displays.Display;

public class QuitCLIFlow implements CLIFlow {

    private final Display display;

    public QuitCLIFlow(Display display) {
        this.display = display;
    }

    @Override
    public void run() {
        display.println("Programa finalizado.");
        System.exit(0);
    }
}

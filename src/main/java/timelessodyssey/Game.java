package timelessodyssey;

import timelessodyssey.gui.GUI;
import timelessodyssey.gui.LanternaGUI;
import timelessodyssey.model.menu.MainMenu;
import timelessodyssey.sound.JavaSoundPlayer;
import timelessodyssey.states.MainMenuState;
import timelessodyssey.states.State;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game {
    public static final int PIXEL_WIDTH = 160;
    public static final int PIXEL_HEIGHT = 90;
    private final GUI gui;
    private State state;
    private static final int NUMBER_OF_LEVELS = 3;

    public Game() throws FontFormatException, IOException, URISyntaxException {
        this.gui = new LanternaGUI(PIXEL_WIDTH, PIXEL_HEIGHT);
        this.state = new MainMenuState(new MainMenu());
    }

    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Game.class.getName());
        try {
            new Game().start();
        } catch (Exception e) {
            logger.log(Level.INFO, "An error occurred", e);
        }
    }

    public void setState(State state) {
        this.state = state;
    }

    public GUI.Resolution getResolution() {
        return gui.getResolution();
    }

    public void setResolution(GUI.Resolution resolution) throws IOException, URISyntaxException, FontFormatException {
        gui.setResolution(resolution);
    }

    public static int getNumberOfLevels() {
        return NUMBER_OF_LEVELS;
    }

    private void start() throws IOException, InterruptedException, URISyntaxException, FontFormatException {
        int FPS = 30;
        long frameTime = 1000 / FPS;
        long frameCount = 0;

        while (this.state != null) {
            long startTime = System.currentTimeMillis();

            state.step(this, gui, frameCount);

            long elapsedTime = System.currentTimeMillis() - startTime;
            long sleepTime = frameTime - elapsedTime;

            if (sleepTime > 0) Thread.sleep(sleepTime);
            frameCount++;
        }

        gui.close();
    }
}

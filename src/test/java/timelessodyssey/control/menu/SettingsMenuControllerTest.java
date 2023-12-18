package timelessodyssey.control.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import timelessodyssey.Game;
import timelessodyssey.gui.GUI;
import timelessodyssey.model.menu.SettingsMenu;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.mockito.ArgumentMatchers.eq;

public class SettingsMenuControllerTest {

    private Game game;
    private SettingsMenu settingsMenu;
    private EntryController entryController;
    private SettingsMenuController settingsMenuController;
    @BeforeEach
    public void setup() {
        this.game = Mockito.mock(Game.class);
        this.settingsMenu = Mockito.mock(SettingsMenu.class);

        this.entryController = Mockito.mock(EntryController.class);

        this.settingsMenuController = new SettingsMenuController(settingsMenu, entryController);
    }

    @Test
    public void ActionDownEntry() throws IOException, URISyntaxException, FontFormatException {
        for (int counter = 1; counter < 20; counter++){
            settingsMenuController.step(game, GUI.Action.DOWN, 0);
            Mockito.verify(settingsMenu, Mockito.times(counter)).moveDown();
        }
    }

    @Test
    public void ActionUpEntry() throws IOException, URISyntaxException, FontFormatException {
        for (int counter = 1; counter < 20; counter++){
            settingsMenuController.step(game, GUI.Action.UP, 0);
            Mockito.verify(settingsMenu, Mockito.times(counter)).moveUp();
        }
    }

    @Test
    public void ActionQuit() throws IOException, URISyntaxException, FontFormatException {
        settingsMenuController.step(game, GUI.Action.QUIT, 0);
        Mockito.verify(game, Mockito.times(1)).setState(Mockito.any());
    }

    @Test
    public void ActionOthers() throws IOException, URISyntaxException, FontFormatException {
        settingsMenuController.step(game, GUI.Action.NONE, 0);
        Mockito.verify(entryController, Mockito.times(1))
                .step(eq(game), eq(GUI.Action.NONE), Mockito.anyLong());
    }
}
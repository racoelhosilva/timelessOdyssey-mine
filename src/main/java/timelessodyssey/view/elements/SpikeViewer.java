package timelessodyssey.view.elements;

import com.googlecode.lanterna.TextColor;
import timelessodyssey.gui.GUI;
import timelessodyssey.model.game.elements.Spike;

import java.io.IOException;

public class SpikeViewer implements ElementViewer<Spike>{
    @Override
    public void draw(Spike model, GUI gui) throws IOException {
        int noise = 1;
        for (int x = 0; x < 8; x++) {
            for (int y = 4; y < 7; y++) {
                gui.drawPixel(model.getPosition().x() + x, model.getPosition().y() + y + noise, new TextColor.RGB(100, 255, 100));
                if (noise == 0){ noise = 1;}
                else if (noise == 1){ noise = 0;}
            }
        }
    }
}
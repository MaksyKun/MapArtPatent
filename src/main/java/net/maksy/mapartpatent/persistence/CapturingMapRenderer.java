package net.maksy.mapartpatent.persistence;

import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.entity.Player;

import java.awt.image.BufferedImage;

public class CapturingMapRenderer extends MapRenderer {
    private BufferedImage image;

    public CapturingMapRenderer() {
        super(true); // Ensure we replace any existing renderers
    }

    @Override
    public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
        // Capture the image from the map canvas
        if (image == null) {
            image = new BufferedImage(128, 128, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < 128; x++) {
                for (int y = 0; y < 128; y++) {
                    image.setRGB(x, y, mapCanvas.getPixel(x, y));
                }
            }
        }
    }

    public BufferedImage getImage() {
        return image;
    }
}
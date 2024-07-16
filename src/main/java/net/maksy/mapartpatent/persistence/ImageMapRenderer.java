package net.maksy.mapartpatent.persistence;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.awt.image.BufferedImage;

public class ImageMapRenderer extends MapRenderer {
    private final BufferedImage image;

    public ImageMapRenderer(BufferedImage image) {
        super(false); // Do not respect vanilla rendering
        this.image = image;
    }

    @Override
    public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
        if (image != null) {
            mapCanvas.drawImage(0, 0, image);
        }
    }
}

package com.hardcoreleveleditor.panels;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ResourcePanel extends JPanel
{
    private static final String resourcePath = "/Users/alex/Desktop/Code/Hardcore2D/res/environments/";
    private final List<Image> resourceImages = new ArrayList<Image>();
    private final List<GridCellPanel> resourceCells = new ArrayList<GridCellPanel>();

    public ResourcePanel()
    {
        super(new GridLayout(0, 2));
        loadResources();
        fillResourceCellsWithResourceImages();
    }

    private void loadResources()
    {
        try
        {
            Files.walk(Paths.get(resourcePath))
                    .filter(Files::isRegularFile)
                    .forEach((file) -> { parseResourceFile(file); });
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void parseResourceFile(final Path filePath)
    {
        final String fileName = filePath.getFileName().toString();
        if (fileName.endsWith("0.png"))
        {
            try
            {
                resourceImages.add(ImageIO.read(filePath.toFile()));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void fillResourceCellsWithResourceImages()
    {
        for (Image image: resourceImages)
        {
            GridCellPanel gridCell = new GridCellPanel(64, 64);
            gridCell.setImage(image);
            add(gridCell);
            resourceCells.add(gridCell);
        }
    }
}

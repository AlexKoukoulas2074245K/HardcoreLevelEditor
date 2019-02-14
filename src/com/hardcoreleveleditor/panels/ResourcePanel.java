package com.hardcoreleveleditor.panels;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

public class ResourcePanel extends JPanel
{
    //private static final String RESOURCE_PATH = System.getProperty("os.name").indexOf("Win") >= 0 ? "C:/Users/alex.koukoulas/Code/Hardcore2D/res/" : "/Users/alex/Desktop/Code/Hardcore2D/res/environments/";
    private static final String RESOURCE_ENVIRONMENTS_PATH = System.getProperty("os.name").indexOf("Win") >= 0 ? "C:/Users/alex.koukoulas/Code/Hardcore2D/res/environments" : "/Users/alex/Desktop/Code/Hardcore2D/res/environments/";
    private static final String RESOURCE_CHARACTERS_PATH = System.getProperty("os.name").indexOf("Win") >= 0 ? "C:/Users/alex.koukoulas/Code/Hardcore2D/res/characters" : "/Users/alex/Desktop/Code/Hardcore2D/res/characters/";

    private static final String TOP_LEVEL_RESOURCES_DIRECTORY_NAME = "res";
    private static final String BOTTOM_LEVEL_ANIMATION_DIRECTORY_NAME = "idle";
    private static final int RESOURCE_GRID_CELL_SIZE = 80;

    private final ComponentsPanel componentsPanel;
    private final Map<Image, String> resourceImagesToAbsolutePaths = new HashMap<>();
    private final List<GridCellPanel> resourceCells = new ArrayList<>();
    private Image emptyImage = null;

    public ResourcePanel(final ComponentsPanel componentsPanel)
    {
        super(new GridLayout(0, 2));
        this.componentsPanel = componentsPanel;

        loadResources();
        fillResourceCellsWithResourceImages();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.white);
        g2.fillRect(0, 0, getSize().width, getSize().height);

        for (GridCellPanel gridCellPanel: resourceCells)
        {
            g2.setColor(Color.black);
            g2.drawLine(gridCellPanel.getX(), gridCellPanel.getY()+ RESOURCE_GRID_CELL_SIZE - 1, gridCellPanel.getX() + RESOURCE_GRID_CELL_SIZE - 1, gridCellPanel.getY() + RESOURCE_GRID_CELL_SIZE - 1);
            g2.drawLine(gridCellPanel.getX() + RESOURCE_GRID_CELL_SIZE - 1 , gridCellPanel.getY(), gridCellPanel.getX() + RESOURCE_GRID_CELL_SIZE - 1, gridCellPanel.getY() + RESOURCE_GRID_CELL_SIZE - 1);

            if (gridCellPanel.getImage() != null)
            {
                g2.drawImage(gridCellPanel.getImage(), gridCellPanel.getX(), gridCellPanel.getY(), RESOURCE_GRID_CELL_SIZE, RESOURCE_GRID_CELL_SIZE, null);
            }

            if (gridCellPanel == GridCellPanel.sSelectedGridCell)
            {
                g2.drawImage(GridCellPanel.sSelectedCellImage, gridCellPanel.getX(), gridCellPanel.getY(), RESOURCE_GRID_CELL_SIZE, RESOURCE_GRID_CELL_SIZE, null);
            }
        }

        g2.dispose();
    }

    public Image getImageFromAnimationName(final String animationName)
    {
        for (GridCellPanel cell: resourceCells)
        {
            if (cell.getAnimationName().equals(animationName))
            {
                return cell.getImage();
            }
        }

        return null;
    }

    private void loadResources()
    {
        try {
            Files.walk(Paths.get(RESOURCE_ENVIRONMENTS_PATH))
                    .filter(Files::isRegularFile)
                    .forEach((file) -> {
                        parseResourceFile(file);
                    });

            Files.walk(Paths.get(RESOURCE_CHARACTERS_PATH))
                    .filter(Files::isRegularFile)
                    .forEach((file) -> {
                        parseResourceFile(file);
                    });
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void parseResourceFile(final Path filePath)
    {
        final String filePathString = filePath.toString();

        if (filePathString.indexOf("idle") < 0)
        {
            return;
        }

        if (filePathString.endsWith("0.png"))
        {
            try
            {
                Image loadedImage = ImageIO.read(filePath.toFile());

                if (filePath.toString().indexOf("empty") >= 0)
                {
                    emptyImage = loadedImage;
                }
                else
                {
                    resourceImagesToAbsolutePaths.put(loadedImage, getAnimationNameFromFilePath(filePath.toString()));
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void fillResourceCellsWithResourceImages()
    {
        GridCellPanel emptyGridCell = new GridCellPanel(componentsPanel, RESOURCE_GRID_CELL_SIZE, RESOURCE_GRID_CELL_SIZE, true);
        emptyGridCell.setVisual(emptyImage, "environments/empty");
        add(emptyGridCell);
        resourceCells.add(emptyGridCell);

        for (Map.Entry<Image, String> imagePathEntry: resourceImagesToAbsolutePaths.entrySet())
        {
            GridCellPanel gridCell = new GridCellPanel(componentsPanel, RESOURCE_GRID_CELL_SIZE, RESOURCE_GRID_CELL_SIZE, true);
            gridCell.setVisual(imagePathEntry.getKey(), imagePathEntry.getValue());
            add(gridCell);
            resourceCells.add(gridCell);
        }
    }

    private String getAnimationNameFromFilePath(final String filePath)
    {
        StringBuilder animationNameBuilder = new StringBuilder();
        String pattern = Pattern.quote(System.getProperty("file.separator"));
        String[] filePathComponents = filePath.split(pattern);

        boolean recordingAnimationName = false;

        for (String directory: filePathComponents)
        {
            if (directory.equals(BOTTOM_LEVEL_ANIMATION_DIRECTORY_NAME))
            {
                break;
            }

            if (recordingAnimationName)
            {
                animationNameBuilder.append(directory + "/");
            }

            if (directory.equals(TOP_LEVEL_RESOURCES_DIRECTORY_NAME))
            {
                recordingAnimationName = true;
            }
        }

        animationNameBuilder.deleteCharAt(animationNameBuilder.length() - 1);
        System.out.println(animationNameBuilder.toString());
        return animationNameBuilder.toString();
    }
}

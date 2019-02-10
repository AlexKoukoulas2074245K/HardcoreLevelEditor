package com.hardcoreleveleditor.panels;

import com.hardcoreleveleditor.components.AnimationComponent;
import com.hardcoreleveleditor.components.PhysicsComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class LevelEditorPanel extends JPanel
{
    public static int sCurrentCellSize = 0;

    private static final Color HITBOX_COLOR = new Color(255, 0,255, 128);

    private final ComponentsPanel componentsPanel;
    private final int cellSize;
    private final List<GridCellPanel> levelGridCells = new ArrayList<>();
    private Map<Image, Rectangle> backgroundAnimations = new HashMap<>();


    public LevelEditorPanel(final ComponentsPanel componentsPanel, final int levelEditorCellRows, final int levelEditorCellCols, final int cellSize)
    {
        super(new GridLayout(levelEditorCellRows, levelEditorCellCols));

        this.componentsPanel = componentsPanel;
        this.cellSize = cellSize;
        LevelEditorPanel.sCurrentCellSize = cellSize;

        for (int y = 0; y < levelEditorCellRows; ++y)
        {
            for (int x = 0; x < levelEditorCellCols; ++x)
            {
                GridCellPanel gridCellPanel = new GridCellPanel(componentsPanel, cellSize, cellSize, false);
                levelGridCells.add(gridCellPanel);
                add(gridCellPanel);
            }
        }
    }

    public void addBackgroundAnimation(final Image image, final Rectangle backgroundRectangle)
    {
        backgroundAnimations.put(image, backgroundRectangle);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.white);
        g2.fillRect(0, 0, getSize().width, getSize().height);

        if (backgroundAnimations.size() > 0)
        {
            for (Map.Entry<Image, Rectangle> backgroundEntry : backgroundAnimations.entrySet())
            {
                Image image = backgroundEntry.getKey();
                Rectangle rect = backgroundEntry.getValue();
                g2.drawImage(image, rect.x, rect.y, rect.width, rect.height, null);
            }
        }

        for (GridCellPanel gridCellPanel: levelGridCells)
        {
            g2.setColor(Color.black);
            g2.drawLine(gridCellPanel.getX(), gridCellPanel.getY() + cellSize - 1, gridCellPanel.getX() + cellSize - 1, gridCellPanel.getY() + cellSize - 1);
            g2.drawLine(gridCellPanel.getX() + cellSize - 1 , gridCellPanel.getY(), gridCellPanel.getX() + cellSize - 1, gridCellPanel.getY() + cellSize - 1);

            if (gridCellPanel.getImage() != null)
            {
                g2.drawImage(gridCellPanel.getImage(), gridCellPanel.getX(), gridCellPanel.getY(), cellSize, cellSize, null);
            }

            if (gridCellPanel.getCellComponents().containsKey("PhysicsComponent"))
            {
                PhysicsComponent physicsComponent = (PhysicsComponent)gridCellPanel.getCellComponents().get("PhysicsComponent");
                g2.setColor(HITBOX_COLOR);
                g2.fillRect((int)(gridCellPanel.getX() + LevelEditorPanel.sCurrentCellSize/2 + physicsComponent.hitBoxCenterPoint.getX() - physicsComponent.hitBoxDimensions.getX()/2),
                            (int)(gridCellPanel.getY() + LevelEditorPanel.sCurrentCellSize/2 - physicsComponent.hitBoxCenterPoint.getY() - physicsComponent.hitBoxDimensions.getY()/2),
                            (int)(physicsComponent.hitBoxDimensions.getX()), (int)(physicsComponent.hitBoxDimensions.getY()));
            }

            if (gridCellPanel == GridCellPanel.sSelectedGridCell)
            {
                g2.drawImage(GridCellPanel.sSelectedCellImage, gridCellPanel.getX(), gridCellPanel.getY(), cellSize, cellSize, null);
            }
        }
    }

    public List<GridCellPanel> getAllLevelGridCells()
    {
        return levelGridCells;
    }
}

package com.hardcoreleveleditor.panels;

import com.hardcoreleveleditor.components.IComponent;
import com.hardcoreleveleditor.components.PhysicsComponent;
import com.hardcoreleveleditor.components.ShaderComponent;

import java.util.Timer;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class LevelEditorPanel extends JPanel
{
    public static int sCurrentCellSize = 0;

    private static final Color HITBOX_COLOR = new Color(255, 0,255, 128);

    private final ComponentsPanel componentsPanel;
    private final int cellSize;
    private final int cellRows;
    private final int cellCols;
    private final List<GridCellPanel> levelGridCells = new ArrayList<>();
    private GridCellPanel backgroundInvisibleCell = null;
    private GridCellPanel[][] gridCells = null;


    private class GridCellBackupInfo
    {
        public final Map<String,IComponent> gridCellComponents;
        public final Image animationIdleImage;
        public final String animationName;
        public final String customCellName;

        public GridCellBackupInfo(final Map<String, IComponent> gridCellComponents, final Image animationIdleImage, final String animationName, final String customCellName)
        {
            this.gridCellComponents = gridCellComponents;
            this.animationIdleImage = animationIdleImage;
            this.animationName = animationName;
            this.customCellName = customCellName;
        }
    }

    private Map<GridCellPanel, GridCellBackupInfo> gridCellsPreviousState = new HashMap<>();

    public LevelEditorPanel(final ComponentsPanel componentsPanel, final int levelEditorCellRows, final int levelEditorCellCols, final int cellSize)
    {
        super(new GridLayout(levelEditorCellRows, levelEditorCellCols));

        this.componentsPanel = componentsPanel;
        this.cellSize = cellSize;
        this.cellCols = levelEditorCellCols;
        this.cellRows = levelEditorCellRows;

        LevelEditorPanel.sCurrentCellSize = cellSize;

        gridCells = new GridCellPanel[levelEditorCellRows][levelEditorCellCols];

        for (int y = 0; y < levelEditorCellRows; ++y)
        {
            for (int x = 0; x < levelEditorCellCols; ++x)
            {
                GridCellPanel gridCellPanel = new GridCellPanel(componentsPanel, cellSize, cellSize, false);
                gridCellPanel.setCoords(x, y, levelEditorCellRows);
                levelGridCells.add(gridCellPanel);
                gridCells[y][x] = gridCellPanel;
                add(gridCellPanel);
            }
        }
    }

    public void addBackgroundAnimation(final Image image, final String animationName)
    {
        backgroundInvisibleCell = new GridCellPanel(componentsPanel, 2, 2, false);
        backgroundInvisibleCell.setVisual(image, animationName);
        backgroundInvisibleCell.getCellComponents().remove("PhysicsComponent");
        backgroundInvisibleCell.getCellComponents().put("ShaderComponent", new ShaderComponent("background"));
    }

    public GridCellPanel getCellWithCoords(final int cellCol, final int cellRow)
    {
        return gridCells[cellRow][cellCol];
    }

    public GridCellPanel getCellWithCustomName(final String customName)
    {
        for (GridCellPanel cell: levelGridCells)
        {
            if (customName.equals(cell.getCustomCellName()))
            {
                return cell;
            }
        }

        return null;
    }

    public GridCellPanel getBackgroundCell()
    {
        return backgroundInvisibleCell;
    }

    public int getCellRowCount()
    {
        return cellRows;
    }

    public int getCellColCount()
    {
        return cellCols;
    }

    public int getCellSize()
    {
        return cellSize;
    }

    public void doSomethingRepeatedly() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate( new TimerTask()
        {
            public void run()
            {
                try
                {
                    synchronized (gridCellsPreviousState)
                    {
                        gridCellsPreviousState.clear();
                        for (GridCellPanel cell : levelGridCells)
                        {
                            gridCellsPreviousState.put(cell, new GridCellBackupInfo(cell.getCloneOfComponents(), cell.getImage(), cell.getAnimationName(), cell.getCustomCellName()));
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        }, 0, 5000);
    }

    public void undo()
    {
        synchronized (gridCellsPreviousState)
        {
            for (Map.Entry<GridCellPanel, GridCellBackupInfo> cellInfoEntry : gridCellsPreviousState.entrySet())
            {
                GridCellBackupInfo cellBackupInfo = cellInfoEntry.getValue();

                cellInfoEntry.getKey().getCellComponents().clear();

                for (Map.Entry<String, IComponent> componentEntry: cellBackupInfo.gridCellComponents.entrySet())
                {
                    cellInfoEntry.getKey().getCellComponents().put(componentEntry.getKey(), componentEntry.getValue());
                }

                cellInfoEntry.getKey().setAnimationName(cellBackupInfo.animationName);
                cellInfoEntry.getKey().setImage(cellBackupInfo.animationIdleImage);
                cellInfoEntry.getKey().setCustomCellName(cellBackupInfo.customCellName);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.white);
        g2.fillRect(0, 0, getSize().width, getSize().height);

        if (backgroundInvisibleCell != null)
        {
            Image image = backgroundInvisibleCell.getImage();
            g2.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        }

        for (GridCellPanel gridCellPanel: levelGridCells)
        {
            // Draw cell outlines
            g2.setColor(Color.black);
            g2.drawLine(gridCellPanel.getX(), gridCellPanel.getY() + cellSize - 1, gridCellPanel.getX() + cellSize - 1, gridCellPanel.getY() + cellSize - 1);
            g2.drawLine(gridCellPanel.getX() + cellSize - 1 , gridCellPanel.getY(), gridCellPanel.getX() + cellSize - 1, gridCellPanel.getY() + cellSize - 1);

            // Draw cell panel image
            if (gridCellPanel.getImage() != null)
            {
                g2.drawImage(gridCellPanel.getImage(), gridCellPanel.getX(), gridCellPanel.getY(), cellSize, cellSize, null);
            }

            // Draw physics hitbox
            if (gridCellPanel.getCellComponents().containsKey("PhysicsComponent"))
            {
                PhysicsComponent physicsComponent = (PhysicsComponent)gridCellPanel.getCellComponents().get("PhysicsComponent");
                g2.setColor(HITBOX_COLOR);
                g2.fillRect((int)(gridCellPanel.getX() + LevelEditorPanel.sCurrentCellSize/2 + physicsComponent.hitBoxCenterPoint.getX() - physicsComponent.hitBoxDimensions.getX()/2),
                            (int)(gridCellPanel.getY() + LevelEditorPanel.sCurrentCellSize/2 - physicsComponent.hitBoxCenterPoint.getY() - physicsComponent.hitBoxDimensions.getY()/2),
                            (int)(physicsComponent.hitBoxDimensions.getX()), (int)(physicsComponent.hitBoxDimensions.getY()));
            }

            // Draw custom name if cell has one
            if (gridCellPanel.getCustomCellName() != null)
            {
                g2.setColor(Color.BLACK);
                g2.drawString(gridCellPanel.getCustomCellName(), gridCellPanel.getX() + 10, gridCellPanel.getY() + this.cellSize - 10);
            }

            if (gridCellPanel == GridCellPanel.sSelectedGridCell)
            {
                // Draw selected image
                g2.drawImage(GridCellPanel.sSelectedCellImage, gridCellPanel.getX(), gridCellPanel.getY(), cellSize, cellSize, null);

                // Draw coords string if not resource cell
                if (gridCellPanel.isResourceCell() == false)
                {
                    g2.setColor(Color.BLACK);
                    g2.drawString((gridCellPanel.getCol() + ", " + gridCellPanel.getRow()), gridCellPanel.getX() + 10, gridCellPanel.getY() + 20);
                }
            }
        }
    }

    public List<GridCellPanel> getAllLevelGridCells()
    {
        return levelGridCells;
    }
}

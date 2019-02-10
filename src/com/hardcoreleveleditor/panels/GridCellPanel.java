package com.hardcoreleveleditor.panels;

import com.hardcoreleveleditor.components.AnimationComponent;
import com.hardcoreleveleditor.components.IComponent;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GridCellPanel extends JPanel implements MouseListener
{
    public static GridCellPanel sSelectedGridCell;
    public static Image sSelectedCellImage;

    private static boolean sMouseDown;

    static
    {
        try
        {
            sSelectedCellImage = ImageIO.read(GridCellPanel.class.getResourceAsStream("/selected_cell.png"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private final ComponentsPanel componentsPanel;
    private final Map<String, IComponent> cellComponents = new HashMap<>();
    private final int cellCol;
    private final int cellRow;

    private final int cellWidth;
    private final int cellHeight;

    private boolean isResourceCell;
    private Image animationIdleImage;
    private String animationName;

    public GridCellPanel(final ComponentsPanel componentsPanel, final int cellCol, final int cellRow, final int cellWidth, final int cellHeight, final boolean isResourceCell)
    {
        super();

        addMouseListener(this);

        this.componentsPanel = componentsPanel;
        this.cellCol = cellCol;
        this.cellRow = cellRow;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
        this.isResourceCell = isResourceCell;
        this.animationIdleImage = null;

        setPreferredSize(new Dimension(cellWidth, cellHeight));
    }

    public Map<String, IComponent> getCellComponents()
    {
        return cellComponents;
    }

    public void setAnimationImage(final Image image, final String animationName)
    {
        this.animationIdleImage = image;
        this.animationName = animationName;

        if (!isResourceCell)
        {
            if (animationName.endsWith("empty"))
            {
                cellComponents.remove("AnimationComponent");
            }
            else
            {
                cellComponents.put("AnimationComponent", new AnimationComponent(animationName));
            }
        }
    }

    public boolean isResourceCell()
    {
        return isResourceCell;
    }

    public Image getImage()
    {
        return this.animationIdleImage;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        return;
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {

    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        sMouseDown = true;
        if (isResourceCell)
        {
            onResourceTilePressed();
        }
        else if (!isResourceCell)
        {
            onLevelEditorTilePressed();
        }


        getRootPane().revalidate();
        getRootPane().repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        sMouseDown = false;
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        if (!isResourceCell && sMouseDown)
        {
            onLevelEditorTilePressed();
        }

        getRootPane().revalidate();
        getRootPane().repaint();
    }

    @Override
    public void mouseExited(MouseEvent e)
    {

    }

    private void onLevelEditorTilePressed()
    {
        if (sSelectedGridCell != null && sSelectedGridCell.isResourceCell)
        {
            setAnimationImage(sSelectedGridCell.animationIdleImage, sSelectedGridCell.animationName);
        }
        else
        {
            sSelectedGridCell = sSelectedGridCell == this ? null : this;
            componentsPanel.onCellPressed(sSelectedGridCell);
        }
    }

    private void onResourceTilePressed()
    {
        sSelectedGridCell = sSelectedGridCell == this ? null : this;
        componentsPanel.onCellPressed(sSelectedGridCell);
    }
}

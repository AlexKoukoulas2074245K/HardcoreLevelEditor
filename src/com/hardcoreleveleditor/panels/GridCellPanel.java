package com.hardcoreleveleditor.panels;

import com.hardcoreleveleditor.components.AnimationComponent;
import com.hardcoreleveleditor.components.IComponent;
import com.hardcoreleveleditor.components.PhysicsComponent;
import com.hardcoreleveleditor.components.ShaderComponent;
import com.hardcoreleveleditor.util.JSONUtils;
import javafx.geometry.Point3D;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class GridCellPanel extends JPanel implements MouseListener
{
    public static GridCellPanel sCopyOrCutGridCell = null;
    public static GridCellPanel sSelectedGridCell = null;
    public static Image sSelectedCellImage = null;

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
    private final Map<String, IComponent> cellComponents = new TreeMap<>();

    private final int cellWidth;
    private final int cellHeight;

    private int cellCol;
    private int cellRow;

    private boolean isResourceCell;
    private Image animationIdleImage;
    private String animationName;
    private String customCellName;

    public GridCellPanel(final ComponentsPanel componentsPanel, final int cellWidth, final int cellHeight, final boolean isResourceCell)
    {
        super();

        addMouseListener(this);

        this.componentsPanel = componentsPanel;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
        this.cellCol = -1;
        this.cellRow = -1;
        this.isResourceCell = isResourceCell;
        this.customCellName = null;

        resetDynamicProperties();

        setPreferredSize(new Dimension(cellWidth, cellHeight));
    }

    public GridCellPanel getClone()
    {
        GridCellPanel clone = new GridCellPanel(this.componentsPanel, this.cellWidth, this.cellHeight, this.isResourceCell);
        clone.animationIdleImage = this.animationIdleImage;
        clone.animationName = this.animationName;

        for (Map.Entry<String, IComponent> entry: cellComponents.entrySet())
        {
            clone.getCellComponents().put(entry.getKey(), entry.getValue().getClone());
        }

        return clone;
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
                cellComponents.remove("ShaderComponent");
                cellComponents.remove("PhysicsComponent");
            }
            else
            {
                cellComponents.put("AnimationComponent", new AnimationComponent(animationName));
                cellComponents.put("ShaderComponent", new ShaderComponent("basic"));
                cellComponents.put("PhysicsComponent", new PhysicsComponent());
            }
        }
    }

    public boolean isResourceCell()
    {
        return isResourceCell;
    }

    public String getCustomCellName() { return this.customCellName; }

    public void setCustomCellName(final String customCellName) { this.customCellName = customCellName; }

    public Image getImage()
    {
        return this.animationIdleImage;
    }

    public String getAnimationName() { return this.animationName; }

    public boolean hasCoords() { return this.cellCol != -1 && this.cellRow != -1; }

    public void setCoords(final int col, final int row, final int rowCount)
    {
        this.cellCol = col;
        this.cellRow = ((rowCount - 1) - row);
    }

    public String getTransformComponentJSONString()
    {
        Point3D translation = new Point3D(cellWidth/2 + cellCol * cellWidth, cellHeight/2 + cellRow * cellHeight, 1.0);
        return "\"TransformComponent\": { \"translation\": " + JSONUtils.toJSONString(translation) + ", \"rotation\": [0.0, 0.0, 0.0], \"scale\": [" + (double)(cellWidth) + ", " + (double)(cellHeight) + ", 1.0] }";
    }

    @Override
    public void paintComponent(Graphics g)
    {
        return;
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        if (e.getClickCount() == 2)
        {
            if (isResourceCell)
            {
                onResourceTilePressed();
            }
            else if (!isResourceCell)
            {
                sSelectedGridCell = sSelectedGridCell == this ? null : this;
                componentsPanel.updateComponentsPanel();
            }
        }

        getRootPane().revalidate();
        getRootPane().repaint();
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

    public void resetDynamicProperties()
    {
        this.cellComponents.clear();
        this.animationIdleImage = null;
        this.animationName = null;
        this.animationIdleImage = null;
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
            componentsPanel.updateComponentsPanel();
        }
    }

    private void onResourceTilePressed()
    {
        sSelectedGridCell = sSelectedGridCell == this ? null : this;
        componentsPanel.updateComponentsPanel();
    }
}

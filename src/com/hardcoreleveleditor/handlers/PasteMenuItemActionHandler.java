package com.hardcoreleveleditor.handlers;

import com.hardcoreleveleditor.components.IComponent;
import com.hardcoreleveleditor.panels.GridCellPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class PasteMenuItemActionHandler implements ActionListener
{
    private final JFrame mainFrame;

    public PasteMenuItemActionHandler(final JFrame frame)
    {
        this.mainFrame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (GridCellPanel.sCopyOrCutGridCell != null &&
            GridCellPanel.sSelectedGridCell != null &&
            GridCellPanel.sCopyOrCutGridCell != GridCellPanel.sSelectedGridCell &&
            GridCellPanel.sSelectedGridCell.isResourceCell() == false)
        {
            GridCellPanel.sSelectedGridCell.resetDynamicProperties();

            for (Map.Entry<String, IComponent> entry: GridCellPanel.sCopyOrCutGridCell.getCellComponents().entrySet())
            {
                GridCellPanel.sSelectedGridCell.getCellComponents().put(entry.getKey(), entry.getValue().getClone());
            }

            GridCellPanel.sSelectedGridCell.setVisual(GridCellPanel.sCopyOrCutGridCell.getImage(), GridCellPanel.sCopyOrCutGridCell.getAnimationName());
        }

        mainFrame.getRootPane().revalidate();
        mainFrame.getRootPane().repaint();
    }
}

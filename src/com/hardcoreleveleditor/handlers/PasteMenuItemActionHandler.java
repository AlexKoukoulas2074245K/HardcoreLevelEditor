package com.hardcoreleveleditor.handlers;

import com.hardcoreleveleditor.components.IComponent;
import com.hardcoreleveleditor.main.MainFrame;
import com.hardcoreleveleditor.panels.GridCellPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class PasteMenuItemActionHandler implements ActionListener
{
    private final MainFrame mainFrame;

    public PasteMenuItemActionHandler(final MainFrame frame)
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

            GridCellPanel.sSelectedGridCell.setVisual(GridCellPanel.sCopyOrCutGridCell.getImage(), GridCellPanel.sCopyOrCutGridCell.getAnimationName());
            
            for (Map.Entry<String, IComponent> entry: GridCellPanel.sCopyOrCutGridCell.getCellComponents().entrySet())
            {
                GridCellPanel.sSelectedGridCell.getCellComponents().put(entry.getKey(), entry.getValue().getClone());
            }

            mainFrame.getMainPanel().getComponentsPanel().updateComponentsPanel();
        }

        mainFrame.getRootPane().revalidate();
        mainFrame.getRootPane().repaint();
    }
}

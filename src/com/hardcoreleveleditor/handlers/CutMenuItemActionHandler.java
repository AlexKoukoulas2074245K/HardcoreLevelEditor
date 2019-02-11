package com.hardcoreleveleditor.handlers;

import com.hardcoreleveleditor.panels.GridCellPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CutMenuItemActionHandler implements ActionListener
{
    private final JFrame mainFrame;

    public CutMenuItemActionHandler(final JFrame frame)
    {
        this.mainFrame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (GridCellPanel.sSelectedGridCell != null && GridCellPanel.sSelectedGridCell.isResourceCell() == false)
        {
            GridCellPanel.sCopyOrCutGridCell = GridCellPanel.sSelectedGridCell.getClone();
            GridCellPanel.sSelectedGridCell.resetDynamicProperties();
        }

        mainFrame.revalidate();
        mainFrame.repaint();
    }
}

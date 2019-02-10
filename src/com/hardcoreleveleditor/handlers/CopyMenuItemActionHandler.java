package com.hardcoreleveleditor.handlers;

import com.hardcoreleveleditor.panels.GridCellPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CopyMenuItemActionHandler implements ActionListener
{
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (GridCellPanel.sSelectedGridCell != null && GridCellPanel.sSelectedGridCell.isResourceCell() == false)
        {
            GridCellPanel.sCopyOrCutGridCell = GridCellPanel.sSelectedGridCell;
        }
    }
}

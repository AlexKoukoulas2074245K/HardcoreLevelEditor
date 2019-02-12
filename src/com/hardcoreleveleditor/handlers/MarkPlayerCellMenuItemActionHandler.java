package com.hardcoreleveleditor.handlers;

import com.hardcoreleveleditor.panels.GridCellPanel;
import com.hardcoreleveleditor.panels.LevelEditorPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MarkPlayerCellMenuItemActionHandler implements ActionListener
{
    private final JFrame mainFrame;
    private final LevelEditorPanel levelEditorPanel;

    public MarkPlayerCellMenuItemActionHandler(final JFrame mainFrame, final LevelEditorPanel levelEditorPanel)
    {
        this.mainFrame = mainFrame;
        this.levelEditorPanel = levelEditorPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (GridCellPanel.sSelectedGridCell != null)
        {
            if (GridCellPanel.sSelectedGridCell.isResourceCell() == false)
            {
                GridCellPanel currentMarkedPlayerCell = levelEditorPanel.getCellWithCustomName("player");
                if (currentMarkedPlayerCell != null)
                {
                    currentMarkedPlayerCell.setCustomCellName(null);
                }

                GridCellPanel.sSelectedGridCell.setCustomCellName("player");
                mainFrame.getRootPane().revalidate();
                mainFrame.getRootPane().repaint();
            }
            else
            {
                JOptionPane.showMessageDialog(mainFrame, "No level editor cell has been selected!", "Mark Player Cell Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(mainFrame, "No cell has been selected!", "Mark Player Cell Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

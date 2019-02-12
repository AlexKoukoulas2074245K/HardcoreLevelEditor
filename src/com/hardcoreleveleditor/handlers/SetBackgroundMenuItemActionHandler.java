package com.hardcoreleveleditor.handlers;

import com.hardcoreleveleditor.panels.GridCellPanel;
import com.hardcoreleveleditor.panels.LevelEditorPanel;
import com.hardcoreleveleditor.panels.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;

public class SetBackgroundMenuItemActionHandler implements ActionListener
{
    private final MainPanel mainPanel;

    public SetBackgroundMenuItemActionHandler(final MainPanel mainPanel)
    {
        this.mainPanel = mainPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (GridCellPanel.sSelectedGridCell != null)
        {
            if (GridCellPanel.sSelectedGridCell.isResourceCell())
            {
                mainPanel.getLevelEditorPanel().addBackgroundAnimation(GridCellPanel.sSelectedGridCell.getImage(), GridCellPanel.sSelectedGridCell.getAnimationName());
                mainPanel.getRootPane().revalidate();
                mainPanel.getRootPane().repaint();
            }
            else
            {
                JOptionPane.showMessageDialog(mainPanel, "No resource cell has been selected!", "Set background error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(mainPanel, "No cell has been selected!", "Set background error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

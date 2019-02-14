package com.hardcoreleveleditor.handlers;

import com.hardcoreleveleditor.components.HealthComponent;
import com.hardcoreleveleditor.components.PhysicsComponent;
import com.hardcoreleveleditor.main.MainFrame;
import com.hardcoreleveleditor.panels.ComponentsPanel;
import com.hardcoreleveleditor.panels.GridCellPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddHealthComponentMenuItemActionHandler implements ActionListener
{
    private final MainFrame mainFrame;

    public AddHealthComponentMenuItemActionHandler(final MainFrame mainFrame)
    {
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (GridCellPanel.sSelectedGridCell != null)
        {
            if (GridCellPanel.sSelectedGridCell.isResourceCell() == false)
            {
                GridCellPanel.sSelectedGridCell.getCellComponents().put("HealthComponent", new HealthComponent());
                mainFrame.getMainPanel().getComponentsPanel().updateComponentsPanel();
                mainFrame.getRootPane().revalidate();
                mainFrame.getRootPane().repaint();
            }
            else
            {
                JOptionPane.showMessageDialog(mainFrame, "No level editor cell has been selected!", "Add health component error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(mainFrame, "No cells has been selected!", "Add health component error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

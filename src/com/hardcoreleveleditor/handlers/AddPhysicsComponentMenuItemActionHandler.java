package com.hardcoreleveleditor.handlers;

import com.hardcoreleveleditor.components.PhysicsComponent;
import com.hardcoreleveleditor.panels.ComponentsPanel;
import com.hardcoreleveleditor.panels.GridCellPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddPhysicsComponentMenuItemActionHandler implements ActionListener
{
    private final JFrame mainFrame;
    private final ComponentsPanel componentsPanel;

    public AddPhysicsComponentMenuItemActionHandler(final JFrame mainFrame, final ComponentsPanel componentsPanel)
    {
        this.mainFrame = mainFrame;
        this.componentsPanel = componentsPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (GridCellPanel.sSelectedGridCell != null)
        {
            if (GridCellPanel.sSelectedGridCell.isResourceCell() == false)
            {
                GridCellPanel.sSelectedGridCell.getCellComponents().put("PhysicsComponent", new PhysicsComponent());
                componentsPanel.updateComponentsPanel();
                mainFrame.getRootPane().revalidate();
                mainFrame.getRootPane().repaint();
            }
            else
            {
                JOptionPane.showMessageDialog(mainFrame, "No level editor cell has been selected!", "Add physics component error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(mainFrame, "No cells has been selected!", "Add physics component error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

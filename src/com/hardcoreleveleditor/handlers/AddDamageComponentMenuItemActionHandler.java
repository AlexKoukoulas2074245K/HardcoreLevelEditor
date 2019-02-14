package com.hardcoreleveleditor.handlers;

import com.hardcoreleveleditor.components.DamageComponent;
import com.hardcoreleveleditor.components.PhysicsComponent;
import com.hardcoreleveleditor.main.MainFrame;
import com.hardcoreleveleditor.panels.ComponentsPanel;
import com.hardcoreleveleditor.panels.GridCellPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddDamageComponentMenuItemActionHandler implements ActionListener
{
    private final MainFrame mainFrame;

    public AddDamageComponentMenuItemActionHandler(final MainFrame mainFrame)
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
                GridCellPanel.sSelectedGridCell.getCellComponents().put("DamageComponent", new DamageComponent());
                mainFrame.getMainPanel().getComponentsPanel().updateComponentsPanel();
                mainFrame.getRootPane().revalidate();
                mainFrame.getRootPane().repaint();
            }
            else
            {
                JOptionPane.showMessageDialog(mainFrame, "No level editor cell has been selected!", "Add damage component error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(mainFrame, "No cells has been selected!", "Add damage component error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

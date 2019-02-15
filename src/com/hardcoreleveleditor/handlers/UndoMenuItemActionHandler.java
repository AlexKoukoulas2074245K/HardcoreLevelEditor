package com.hardcoreleveleditor.handlers;

import com.hardcoreleveleditor.main.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UndoMenuItemActionHandler implements ActionListener
{
    private final MainFrame mainFrame;

    public UndoMenuItemActionHandler(final MainFrame mainFrame)
    {
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        mainFrame.getMainPanel().getLevelEditorPanel().undo();
        mainFrame.getMainPanel().getComponentsPanel().updateComponentsPanel();
        mainFrame.revalidate();
        mainFrame.repaint();
    }
}

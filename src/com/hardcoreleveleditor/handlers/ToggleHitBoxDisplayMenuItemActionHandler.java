package com.hardcoreleveleditor.handlers;

import com.hardcoreleveleditor.panels.LevelEditorPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToggleHitBoxDisplayMenuItemActionHandler implements ActionListener
{
    private final JFrame mainFrame;

    public ToggleHitBoxDisplayMenuItemActionHandler(final JFrame mainFrame)
    {
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        LevelEditorPanel.sHitBoxDisplay = !LevelEditorPanel.sHitBoxDisplay;
        mainFrame.revalidate();
        mainFrame.repaint();
    }
}

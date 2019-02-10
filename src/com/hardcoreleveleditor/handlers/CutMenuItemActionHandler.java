package com.hardcoreleveleditor.handlers;

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
        mainFrame.revalidate();
        mainFrame.repaint();
    }
}

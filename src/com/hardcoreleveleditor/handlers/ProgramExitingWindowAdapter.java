package com.hardcoreleveleditor.handlers;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ProgramExitingWindowAdapter extends WindowAdapter
{
    @Override
    public void windowClosing(final WindowEvent __)
    {
        int selOption = JOptionPane.showConfirmDialog (null, "The program is exiting, would you like to save your progress?", "Exiting Option", JOptionPane.YES_NO_OPTION);
        if (selOption == JOptionPane.YES_OPTION)
        {
            return;
        }
        else if (selOption == JOptionPane.NO_OPTION)
        {
            System.exit(0);
        }
    }
}

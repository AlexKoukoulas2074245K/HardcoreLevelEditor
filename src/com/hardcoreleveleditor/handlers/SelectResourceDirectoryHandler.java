package com.hardcoreleveleditor.handlers;

import com.hardcoreleveleditor.main.MainFrame;
import com.hardcoreleveleditor.panels.ResourcePanel;
import javafx.stage.DirectoryChooser;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;

public class SelectResourceDirectoryHandler implements ActionListener
{
    private final MainFrame mainFrame;

    public SelectResourceDirectoryHandler(final MainFrame mainFrame)
    {
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        selectResourceDirectory();
    }

    public void selectResourceDirectory()
    {
        try {
            EventQueue.invokeAndWait(new Runnable()
            {
                @Override

                public void run()
                {
                    JFileChooser fc = new JFileChooser(".");
                    fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                    int choice = fc.showOpenDialog(mainFrame);
                    if (choice == JFileChooser.APPROVE_OPTION)
                    {
                        final String absolutePath = fc.getSelectedFile().getAbsolutePath().replace('\\', '/');
                        if (new File(absolutePath + ResourcePanel.RESOURCE_ENVIRONMENTS_RELATIVE_PATH).exists() == false)
                        {
                            JOptionPane.showMessageDialog(mainFrame, "Chosen file was not a resource directory.\nPlease select the project's root resource directory", "Resource directory", JOptionPane.ERROR_MESSAGE);
                            selectResourceDirectory();
                        }
                        mainFrame.setResourceDirectoryAbsolutePath(absolutePath);
                    }
                    else if (choice == JFileChooser.CANCEL_OPTION)
                    {
                        System.exit(0);
                    }
                }
            });
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
    }
}

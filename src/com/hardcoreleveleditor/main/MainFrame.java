package com.hardcoreleveleditor.main;

import com.hardcoreleveleditor.panels.MainPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame
{
    public static MainFrame instance = null;

    private static final int INIT_WINDOW_WIDTH = 1024;
    private static final int INIT_WINDOW_HEIGHT = 768;

    MainFrame()
    {
        super("Hardcore Level Editor");

        instance = this;

        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception e) { e.printStackTrace(); }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new MainPanel());
        JMenu fileMenu = new JMenu("File");

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);

        setJMenuBar(menuBar);

        pack();
        setMinimumSize(getSize());
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

    }
}

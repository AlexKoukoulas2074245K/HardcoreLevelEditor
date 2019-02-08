package com.hardcoreleveleditor.main;

import com.hardcoreleveleditor.panels.MainPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame
{
    public static MainFrame instance = null;

    private static final int INIT_WINDOW_WIDTH = 1024;
    private static final int INIT_WINDOW_HEIGHT = 818;

    MainFrame()
    {
        super("Hardcore Level Editor");

        instance = this;

        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception e) { e.printStackTrace(); }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new MainPanel());
        setMinimumSize(new Dimension(INIT_WINDOW_WIDTH, INIT_WINDOW_HEIGHT));
        setLocationRelativeTo(null);
        setVisible(true);

        JMenu fileMenu = new JMenu("File");

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(fileMenu);

        setJMenuBar(menuBar);

        pack();
    }
}

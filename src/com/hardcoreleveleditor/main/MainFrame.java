package com.hardcoreleveleditor.main;

import com.hardcoreleveleditor.panels.MainPanel;

import javax.swing.*;

public class MainFrame extends JFrame
{
    public static MainFrame instance = null;
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

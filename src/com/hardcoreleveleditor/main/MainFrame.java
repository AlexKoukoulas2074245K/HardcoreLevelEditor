package com.hardcoreleveleditor.main;

import com.hardcoreleveleditor.handlers.*;
import com.hardcoreleveleditor.panels.MainPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MainFrame extends JFrame
{
    private MainPanel mainPanel;

    public MainFrame()
    {
        super("Hardcore Level Editor");

        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception e) { e.printStackTrace(); }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        resetContentPane(new MainPanel(20, 20, 64));
        setMenuItems();
        pack();
        setMinimumSize(getSize());
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        //addWindowListener(new ProgramExitingWindowAdapter());
    }

    public void resetContentPane(final MainPanel mainPanel)
    {
        this.mainPanel = mainPanel;
        setContentPane(mainPanel);
    }

    private void setMenuItems()
    {
        JMenuBar menuBar = new JMenuBar();

        menuBar.add(createFileMenu());
        menuBar.add(createBackgroundMenu());

        setJMenuBar(menuBar);
    }

    private JMenu createFileMenu()
    {
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New..");
        JMenuItem saveMenuItem = new JMenuItem("Save As..");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newMenuItem.addActionListener(new NewMenuItemActionHandler(this));

        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        saveMenuItem.addActionListener(new SaveAsMenuItemActionHandler());

        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        exitMenuItem.addActionListener(new ExitMenuItemActionHandler(this));

        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(exitMenuItem);

        return fileMenu;
    }

    private JMenu createBackgroundMenu()
    {
        JMenu backgroundMenu = new JMenu("Background");
        JMenuItem setBackgroundMenuItem = new JMenuItem("Set Selected Resource as Background");

        setBackgroundMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
        setBackgroundMenuItem.addActionListener(new SetBackgroundMenuItemActionHandler(mainPanel));

        backgroundMenu.add(setBackgroundMenuItem);

        return backgroundMenu;
    }

}

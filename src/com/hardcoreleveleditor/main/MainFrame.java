package com.hardcoreleveleditor.main;

import com.hardcoreleveleditor.handlers.*;
import com.hardcoreleveleditor.panels.MainPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MainFrame extends JFrame
{
    private MainPanel mainPanel;
    private static final int MENU_MODIFIER_KEY = System.getProperty("os.name").indexOf("Win") >= 0 ? ActionEvent.CTRL_MASK : ActionEvent.META_MASK;

    public MainFrame()
    {
        super("Hardcore Level Editor");

        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception e) { e.printStackTrace(); }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        resetContentPane(new MainPanel(this, 20, 20, 80));
        createMenus();
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

    private void createMenus()
    {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(createEditMenu());
        menuBar.add(createBackgroundMenu());
        menuBar.add(createComponentsMenu());
        setJMenuBar(menuBar);
    }

    private JMenu createFileMenu()
    {
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New..");
        JMenuItem saveMenuItem = new JMenuItem("Save As..");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, MENU_MODIFIER_KEY));
        newMenuItem.addActionListener(new NewMenuItemActionHandler(this));

        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, MENU_MODIFIER_KEY));
        saveMenuItem.addActionListener(new SaveAsMenuItemActionHandler(this, mainPanel.getLevelEditorPanel()));

        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, MENU_MODIFIER_KEY));
        exitMenuItem.addActionListener(new ExitMenuItemActionHandler(this));

        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(exitMenuItem);

        return fileMenu;
    }

    private JMenu createEditMenu()
    {
        JMenu editMenu = new JMenu("Edit");

        JMenuItem cutMenuItem = new JMenuItem("Cut");
        JMenuItem copyMenuItem = new JMenuItem("Copy");
        JMenuItem pasteMenuItem = new JMenuItem("Paste");

        cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, MENU_MODIFIER_KEY));
        cutMenuItem.addActionListener(new CutMenuItemActionHandler(this));

        copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, MENU_MODIFIER_KEY));
        copyMenuItem.addActionListener(new CopyMenuItemActionHandler());

        pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, MENU_MODIFIER_KEY));
        pasteMenuItem.addActionListener(new PasteMenuItemActionHandler(this));

        editMenu.add(cutMenuItem);
        editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);

        return editMenu;
    }

    private JMenu createBackgroundMenu()
    {
        JMenu backgroundMenu = new JMenu("Background");
        JMenuItem setBackgroundMenuItem = new JMenuItem("Set Selected Resource as Background");

        setBackgroundMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, MENU_MODIFIER_KEY));
        setBackgroundMenuItem.addActionListener(new SetBackgroundMenuItemActionHandler(mainPanel));

        backgroundMenu.add(setBackgroundMenuItem);

        return backgroundMenu;
    }

    private JMenu createComponentsMenu()
    {
        JMenu componentsMenu = new JMenu("Components");

        JMenuItem addPhysicsComponentMenuItem  = new JMenuItem("Add Physics Component..");
        addPhysicsComponentMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, MENU_MODIFIER_KEY));
        addPhysicsComponentMenuItem.addActionListener(new AddPhysicsComponentMenuItemActionHandler(this, mainPanel.getComponentsPanel()));

        JMenuItem addAIComponentMenuItem = new JMenuItem("Add AI Component..");
        addAIComponentMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, MENU_MODIFIER_KEY));
        addAIComponentMenuItem.addActionListener(new AddAIComponentMenuItemActionHandler(this, mainPanel.getComponentsPanel()));

        componentsMenu.add(addPhysicsComponentMenuItem);
        componentsMenu.add(addAIComponentMenuItem);

        return componentsMenu;
    }
}

package com.hardcoreleveleditor.panels;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel
{
    public MainPanel()
    {
        super(new BorderLayout(10, 10));

        JPanel toolsPanel = new JPanel();
        toolsPanel.setPreferredSize(new Dimension(1024, 32));
        toolsPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(toolsPanel, BorderLayout.NORTH);

        JPanel levelEditorPanel = new LevelEditorPanel(30, 30);
        JScrollPane scrollPane = new JScrollPane(levelEditorPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        scrollPane.setPreferredSize(new Dimension(768, 700));
        add(scrollPane, BorderLayout.CENTER);

        JPanel resourcePanel = new JPanel();
        resourcePanel.setPreferredSize(new Dimension(128, 700));
        resourcePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(resourcePanel, BorderLayout.EAST);
    }
}

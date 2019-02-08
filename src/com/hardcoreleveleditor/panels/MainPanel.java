package com.hardcoreleveleditor.panels;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel
{
    private static final int SCROLL_UNIT = 8;

    public MainPanel()
    {
        super(new BorderLayout(10, 10));

        JPanel toolsPanel = new JPanel(new GridLayout(1, 4));
        toolsPanel.add(new JButton("Test Button 1"));
        toolsPanel.add(new JButton("Test Button 123"));
        toolsPanel.setPreferredSize(new Dimension(816, 32));
        toolsPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(toolsPanel, BorderLayout.NORTH);

        JPanel levelEditorPanel = new LevelEditorPanel(20, 20);
        JScrollPane levelEditorScrollPane = new JScrollPane(levelEditorPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        levelEditorScrollPane.getVerticalScrollBar().setUnitIncrement(SCROLL_UNIT);
        levelEditorScrollPane.getHorizontalScrollBar().setUnitIncrement(SCROLL_UNIT);
        levelEditorScrollPane.setPreferredSize(new Dimension(768, 700));
        add(levelEditorScrollPane, BorderLayout.CENTER);

        JPanel resourcePanel = new ResourcePanel();
        JScrollPane resourcesScrollPane = new JScrollPane(resourcePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        resourcesScrollPane.getVerticalScrollBar().setUnitIncrement(SCROLL_UNIT);
        resourcesScrollPane.setPreferredSize(new Dimension(148, 700));
        add(resourcesScrollPane, BorderLayout.EAST);
    }
}

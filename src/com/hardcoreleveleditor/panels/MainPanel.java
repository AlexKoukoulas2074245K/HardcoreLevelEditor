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


        JPanel levelEditorPanel = new JPanel();
        Dimension gridSize = new Dimension(60, 22);
        GridLayout levelEditorGridLayout = new GridLayout(gridSize.height, gridSize.width);
        levelEditorPanel.setLayout(levelEditorGridLayout);
        //levelEditorPanel.setPreferredSize(new Dimension(768, 700));

        for (int y = 0; y < gridSize.height; ++y)
        {
            for (int x = 0; x < gridSize.width; ++x)
            {
                JPanel gridSquarePanel = new JPanel();
                gridSquarePanel.setBackground(new Color(x * 3, y * 3, x * 3));
                gridSquarePanel.setPreferredSize(new Dimension(32, 32));
                levelEditorPanel.add(gridSquarePanel);
            }
        }

        JScrollPane scrollPane = new JScrollPane(levelEditorPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(768, 700));
        add(scrollPane, BorderLayout.CENTER);

        JPanel resourcePanel = new JPanel();
        resourcePanel.setPreferredSize(new Dimension(128, 700));
        resourcePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(resourcePanel, BorderLayout.EAST);
    }
}

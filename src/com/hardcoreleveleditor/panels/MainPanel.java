package com.hardcoreleveleditor.panels;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel
{
    public MainPanel()
    {
        super(new BorderLayout(10, 10));

        JPanel toolsPanel = new JPanel();
        toolsPanel.setPreferredSize(new Dimension(1024, 50));
        toolsPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(toolsPanel, BorderLayout.NORTH);


        JPanel levelEditorPanel = new JPanel();
        GridLayout levelEditorGridLayout = new GridLayout(24,28);
        levelEditorPanel.setLayout(levelEditorGridLayout);
        for (int y = 0; y < 24; ++y)
        {
            for (int x = 0; x < 28; ++x)
            {
                JPanel gridSquarePanel = new JPanel();
                gridSquarePanel.setBackground(new Color(x * 9, y * 9, x * 9));
                gridSquarePanel.setPreferredSize(new Dimension(32, 32));
                levelEditorPanel.add(gridSquarePanel);
            }
        }

        levelEditorPanel.setPreferredSize(new Dimension(896, 768));
        levelEditorPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(levelEditorPanel, BorderLayout.CENTER);

        JPanel resourcePanel = new JPanel();
        resourcePanel.setPreferredSize(new Dimension(128, 768));
        resourcePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(resourcePanel, BorderLayout.EAST);
    }
}

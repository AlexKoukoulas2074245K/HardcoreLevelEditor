package com.hardcoreleveleditor.panels;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.logging.Level;


public class MainPanel extends JPanel
{
    private static final int SCROLL_UNIT = 8;
    private static final int H_COMPONENT_GAP = 10;
    private static final int V_COMPONENT_GAP = 10;

    private final JFrame mainFrame;
    private final LevelEditorPanel levelEditorPanel;
    private final ComponentsPanel componentsPanel;

    public MainPanel(final JFrame mainFrame, final int levelEditorCellRows, final int levelEditorCellCols, final int cellSize)
    {
        super(new BorderLayout(H_COMPONENT_GAP, V_COMPONENT_GAP));

        this.mainFrame = mainFrame;

        JPanel toolsPanel = new JPanel(new GridLayout(1, 4));
        toolsPanel.add(new JButton("Test Button 1"));
        toolsPanel.add(new JButton("Test Button 123"));
        toolsPanel.setPreferredSize(new Dimension(816, 32));
        toolsPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        componentsPanel = new ComponentsPanel(mainFrame);
        JScrollPane componentsScrollPane = new JScrollPane(componentsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        componentsScrollPane.getVerticalScrollBar().setUnitIncrement(SCROLL_UNIT);
        componentsScrollPane.setPreferredSize(new Dimension(180, 350));

        JPanel resourcePanel = new ResourcePanel(componentsPanel);
        JScrollPane resourcesScrollPane = new JScrollPane(resourcePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        resourcesScrollPane.getVerticalScrollBar().setUnitIncrement(SCROLL_UNIT);
        resourcesScrollPane.setPreferredSize(new Dimension(180, 350));

        JPanel resourceAndComponentPanel = new JPanel(new BorderLayout(10, 10));
        resourceAndComponentPanel.add(resourcesScrollPane, BorderLayout.NORTH);
        resourceAndComponentPanel.add(componentsScrollPane, BorderLayout.SOUTH);

        levelEditorPanel = new LevelEditorPanel(componentsPanel, levelEditorCellRows, levelEditorCellCols, cellSize);
        JScrollPane levelEditorScrollPane = new JScrollPane(levelEditorPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        levelEditorScrollPane.getVerticalScrollBar().setUnitIncrement(SCROLL_UNIT);
        levelEditorScrollPane.getHorizontalScrollBar().setUnitIncrement(SCROLL_UNIT);
        levelEditorScrollPane.setPreferredSize(new Dimension(768, 700));

        add(toolsPanel, BorderLayout.NORTH);
        add(resourceAndComponentPanel, BorderLayout.EAST);
        add(levelEditorScrollPane, BorderLayout.CENTER);
    }

    public List<GridCellPanel> getAllLevelEditorGridCells()
    {
        return levelEditorPanel.getAllLevelGridCells();
    }

    public LevelEditorPanel getLevelEditorPanel()
    {
        return levelEditorPanel;
    }
    public ComponentsPanel getComponentsPanel() { return componentsPanel; }
}

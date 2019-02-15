package com.hardcoreleveleditor.panels;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
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
    private final ResourcePanel resourcePanel;

    public MainPanel(final JFrame mainFrame, final String resourceDirectoryAbsolutePath, final int levelEditorCellRows, final int levelEditorCellCols, final int cellSize)
    {
        super(new BorderLayout(H_COMPONENT_GAP, V_COMPONENT_GAP));

        this.mainFrame = mainFrame;

        componentsPanel = new ComponentsPanel(mainFrame);
        JScrollPane componentsScrollPane = new JScrollPane(componentsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        componentsScrollPane.getVerticalScrollBar().setUnitIncrement(SCROLL_UNIT);
        componentsScrollPane.setPreferredSize(new Dimension(180, 350));

        resourcePanel = new ResourcePanel(componentsPanel, resourceDirectoryAbsolutePath);
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
        
        add(resourceAndComponentPanel, BorderLayout.EAST);
        add(levelEditorScrollPane, BorderLayout.CENTER);

        levelEditorPanel.doSomethingRepeatedly();
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
    public ResourcePanel getResourcePanel() { return resourcePanel; }
}

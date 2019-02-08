package com.hardcoreleveleditor.panels;

import javax.swing.*;
import java.awt.*;

public class LevelEditorPanel extends JPanel
{
    public LevelEditorPanel(final int levelEditorCellRows, final int levelEditorCellCols)
    {
        super(new GridLayout(levelEditorCellRows, levelEditorCellCols));

        for (int y = 0; y < levelEditorCellRows; ++y)
        {
            for (int x = 0; x < levelEditorCellCols; ++x)
            {
                add(new GridCellPanel(64, 64));
            }
        }
    }
}

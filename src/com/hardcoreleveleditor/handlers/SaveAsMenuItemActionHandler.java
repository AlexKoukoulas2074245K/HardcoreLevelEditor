package com.hardcoreleveleditor.handlers;

import com.hardcoreleveleditor.components.IComponent;
import com.hardcoreleveleditor.panels.GridCellPanel;
import com.hardcoreleveleditor.panels.LevelEditorPanel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SaveAsMenuItemActionHandler implements ActionListener
{
    private static final String LEVEL_FILE_EXTENSION = ".json";

    private final JFrame mainFrame;
    private final LevelEditorPanel levelEditorPanel;

    public SaveAsMenuItemActionHandler(final JFrame mainFrame, final LevelEditorPanel levelEditorPanel)
    {
        this.mainFrame = mainFrame;
        this.levelEditorPanel = levelEditorPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        JFileChooser fc = new JFileChooser(".");
        FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("JSON (*.json)", "json");
        fc.setFileFilter(fileFilter);

        int choice = fc.showSaveDialog(mainFrame);
        if (choice == JFileChooser.APPROVE_OPTION)
        {
            File selFile = fc.getSelectedFile();
            File adjustedFile = selFile;

            if (!selFile.getName().endsWith(LEVEL_FILE_EXTENSION))
            {
                adjustedFile = new File(selFile.getAbsolutePath() + LEVEL_FILE_EXTENSION);
            }

            if (adjustedFile.exists())
            {
                int selOption = JOptionPane.showConfirmDialog (null, "Overwrite existing file?", "Save Option", JOptionPane.YES_NO_OPTION);
                if (selOption == JOptionPane.YES_OPTION)
                {
                    saveLevelToFile(adjustedFile);
                }
            }
            else
            {
                saveLevelToFile(adjustedFile);
            }
        }
    }

    private void saveLevelToFile(final File file)
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file)))
        {
            StringBuilder sb = new StringBuilder();

            final double levelHorBound = (double)(levelEditorPanel.getCellColCount() * levelEditorPanel.getCellSize());
            final double levelVerBound = (double)(levelEditorPanel.getCellRowCount() * levelEditorPanel.getCellSize());

            sb.append("{"); sb.append('\n');
            sb.append("\t\"horBounds\": { \"left\": 0.0, \"right\": " + levelHorBound + " },"); sb.append('\n');
            sb.append("\t\"verBounds\": { \"bottom\": 0.0, \"top\": " + levelVerBound + " },"); sb.append('\n');
            sb.append("\t\"cached_animations\": [\"effects/player_swing/idle\", \"effects/player_shuriken/idle\"],"); sb.append('\n');
            sb.append("\t\"entities\": "); sb.append('\n');
            sb.append("\t["); sb.append('\n');

            if (levelEditorPanel.getBackgroundCell() != null)
            {
                sb.append("\t\t{"); sb.append('\n');

                sb.append("\t\t\t\"name\": \"background\","); sb.append('\n');
                sb.append("\t\t\t\"components\":"); sb.append('\n');
                sb.append("\t\t\t{"); sb.append('\n');

                for (Map.Entry<String, IComponent> entry: levelEditorPanel.getBackgroundCell().getCellComponents().entrySet())
                {
                    sb.append("\t\t\t\t");
                    sb.append(entry.getValue().toJSONString());
                    sb.append(",\n");
                }

                sb.append("\t\t\t\t");
                sb.append("\"TransformComponent\": { \"translation\": [0.0, 0.0, 0.0], \"rotation\": [0.0, 0.0, 0.0], \"scale\": [2.0, 2.0, 2.0] }");
                sb.append('\n');
                sb.append("\t\t\t}"); sb.append('\n');
                sb.append("\t\t},"); sb.append('\n');
            }

            for (int i = 0; i < levelEditorPanel.getAllLevelGridCells().size(); ++i)
            {
                GridCellPanel cell = levelEditorPanel.getAllLevelGridCells().get(i);
                if (cell.getCellComponents().size() == 0)
                {
                    continue;
                }

                sb.append("\t\t{"); sb.append('\n');

                final String cellName = cell.getCustomCellName() == null ? (cell.getAnimationName() + "-" + i) : cell.getCustomCellName();
                sb.append("\t\t\t\"name\": \"" + cellName + "\","); sb.append('\n');
                sb.append("\t\t\t\"components\":"); sb.append('\n');
                sb.append("\t\t\t{"); sb.append('\n');

                for (Map.Entry<String, IComponent> entry: cell.getCellComponents().entrySet())
                {
                    sb.append("\t\t\t\t");
                    sb.append(entry.getValue().toJSONString());
                    sb.append(",\n");
                }

                sb.append("\t\t\t\t");
                sb.append(cell.getTransformComponentJSONString());
                sb.append('\n');
                sb.append("\t\t\t}"); sb.append('\n');
                sb.append("\t\t},"); sb.append('\n');
            }

            sb.setLength(sb.length() - 2);
            sb.append('\n');
            sb.append("\t]"); sb.append('\n');
            sb.append("}"); sb.append('\n');

            bw.write(sb.toString());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}

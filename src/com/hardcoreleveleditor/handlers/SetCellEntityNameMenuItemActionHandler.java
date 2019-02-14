package com.hardcoreleveleditor.handlers;

import com.hardcoreleveleditor.components.ShaderComponent;
import com.hardcoreleveleditor.main.MainFrame;
import com.hardcoreleveleditor.panels.GridCellPanel;
import com.hardcoreleveleditor.panels.LevelEditorPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class SetCellEntityNameMenuItemActionHandler implements ActionListener
{
    private final MainFrame mainFrame;

    public SetCellEntityNameMenuItemActionHandler(final MainFrame mainFrame)
    {
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (GridCellPanel.sSelectedGridCell != null)
        {
            if (GridCellPanel.sSelectedGridCell.isResourceCell() == false)
            {
                JDialog cellCustomNameDialog = new JDialog(mainFrame, "Set Cell Entity Name", Dialog.ModalityType.APPLICATION_MODAL);
                cellCustomNameDialog.getRootPane().registerKeyboardAction(new DisposeDialogHandler(cellCustomNameDialog), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
                cellCustomNameDialog.setLayout(new BorderLayout());

                JPanel cellCustomNamePanel = new JPanel();
                JTextField cellEntityNameField = new JTextField(GridCellPanel.sSelectedGridCell.getCustomCellName() == null ? "" : GridCellPanel.sSelectedGridCell.getCustomCellName(), 20);

                cellCustomNamePanel.add(new JLabel("Cell Entity Name: "));
                cellCustomNamePanel.add(cellEntityNameField);

                JButton okButton = new JButton("Ok");
                okButton.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        GridCellPanel cellAlreadyContainingCustomName = mainFrame.getMainPanel().getLevelEditorPanel().getCellWithCustomName(cellEntityNameField.getText());
                        if (cellAlreadyContainingCustomName != null)
                        {
                            cellAlreadyContainingCustomName.setCustomCellName(null);
                        }

                        GridCellPanel.sSelectedGridCell.setCustomCellName(cellEntityNameField.getText());

                        mainFrame.getRootPane().revalidate();
                        mainFrame.getRootPane().repaint();
                        cellCustomNameDialog.dispose();
                    }
                });

                JButton cancelButton = new JButton("Cancel");
                cancelButton.addActionListener(new DisposeDialogHandler(cellCustomNameDialog));

                JPanel actionButtonsPanel = new JPanel();
                actionButtonsPanel.add(okButton);
                actionButtonsPanel.add(cancelButton);

                cellCustomNameDialog.add(cellCustomNamePanel, BorderLayout.NORTH);
                cellCustomNameDialog.add(actionButtonsPanel, BorderLayout.SOUTH);

                cellCustomNameDialog.pack();
                cellCustomNameDialog.getRootPane().setDefaultButton(okButton);
                cellCustomNameDialog.setResizable(false);
                cellCustomNameDialog.setLocationRelativeTo(mainFrame);
                cellCustomNameDialog.setVisible(true);

                mainFrame.getRootPane().repaint();
            }
            else
            {
                JOptionPane.showMessageDialog(mainFrame, "No level editor cell has been selected!", "Set Cell Entity Name Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(mainFrame, "No cell has been selected!", "Set Cell Entity Name Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

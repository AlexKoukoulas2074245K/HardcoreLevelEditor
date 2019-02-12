package com.hardcoreleveleditor.handlers;

import com.hardcoreleveleditor.components.AIComponent;
import com.hardcoreleveleditor.components.PhysicsComponent;
import com.hardcoreleveleditor.components.ShaderComponent;
import com.hardcoreleveleditor.panels.ComponentsPanel;
import com.hardcoreleveleditor.panels.GridCellPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class AddAIComponentMenuItemActionHandler implements ActionListener
{
    private final JFrame mainFrame;
    private final ComponentsPanel componentsPanel;

    public AddAIComponentMenuItemActionHandler(final JFrame mainFrame, final ComponentsPanel componentsPanel)
    {
        this.mainFrame = mainFrame;
        this.componentsPanel = componentsPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (GridCellPanel.sSelectedGridCell == null)
        {
            JOptionPane.showMessageDialog(mainFrame, "No cells has been selected!", "Create AIComponent Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (GridCellPanel.sSelectedGridCell.isResourceCell() == true)
        {
            JOptionPane.showMessageDialog(mainFrame, "No level editor cell has been selected!", "Create AIComponent Error", JOptionPane.ERROR_MESSAGE);
        }

        JDialog aiComponentCreationDialog = new JDialog(mainFrame, "Create AIComponent", Dialog.ModalityType.APPLICATION_MODAL);
        aiComponentCreationDialog.getRootPane().registerKeyboardAction(new DisposeDialogHandler(aiComponentCreationDialog), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
        aiComponentCreationDialog.setLayout(new BorderLayout());

        JPanel aiComponentPropertiesPanel = new JPanel();
        JTextField aiComponentClassNameField = new JTextField("", 20);

        aiComponentPropertiesPanel.add(new JLabel("AIComponent Class Name: "));
        aiComponentPropertiesPanel.add(aiComponentClassNameField);

        JButton createButton = new JButton("Create");
        createButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                GridCellPanel.sSelectedGridCell.getCellComponents().put("AIComponent", new AIComponent(aiComponentClassNameField.getText()));
                componentsPanel.updateComponentsPanel();
                mainFrame.getRootPane().revalidate();
                mainFrame.getRootPane().repaint();
                aiComponentCreationDialog.dispose();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new DisposeDialogHandler(aiComponentCreationDialog));

        JPanel actionButtonsPanel = new JPanel();
        actionButtonsPanel.add(createButton);
        actionButtonsPanel.add(cancelButton);

        aiComponentCreationDialog.add(aiComponentPropertiesPanel, BorderLayout.NORTH);
        aiComponentCreationDialog.add(actionButtonsPanel, BorderLayout.SOUTH);

        aiComponentCreationDialog.pack();
        aiComponentCreationDialog.getRootPane().setDefaultButton(createButton);
        aiComponentCreationDialog.setResizable(false);
        aiComponentCreationDialog.setLocationRelativeTo(mainFrame);
        aiComponentCreationDialog.setVisible(true);
    }
}

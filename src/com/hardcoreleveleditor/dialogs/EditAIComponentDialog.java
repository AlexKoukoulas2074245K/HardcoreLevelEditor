package com.hardcoreleveleditor.dialogs;

import com.hardcoreleveleditor.components.AIComponent;
import com.hardcoreleveleditor.components.IComponent;
import com.hardcoreleveleditor.handlers.DisposeDialogHandler;
import com.hardcoreleveleditor.panels.GridCellPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class EditAIComponentDialog extends JDialog
{
    public EditAIComponentDialog(final JFrame mainFrame, final IComponent abstractComponent)
    {
        super(mainFrame, "Edit AI Component", Dialog.ModalityType.APPLICATION_MODAL);
        getRootPane().registerKeyboardAction(new DisposeDialogHandler(this), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
        setLayout(new BorderLayout());

        AIComponent aiComponent = (AIComponent)abstractComponent;

        JPanel aiComponentPropertiesPanel = new JPanel();
        JTextField aiComponentClassNameField = new JTextField(aiComponent.getAIComponentClassName(), 20);

        aiComponentPropertiesPanel.add(new JLabel("AIComponent Class Name: "));
        aiComponentPropertiesPanel.add(aiComponentClassNameField);

        JButton okButton = new JButton("Ok");
        okButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                aiComponent.setAIComponentClassName(aiComponentClassNameField.getText());

                mainFrame.getRootPane().revalidate();
                mainFrame.getRootPane().repaint();
                dispose();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new DisposeDialogHandler(this));

        JPanel actionButtonsPanel = new JPanel();
        actionButtonsPanel.add(okButton);
        actionButtonsPanel.add(cancelButton);

        add(aiComponentPropertiesPanel, BorderLayout.NORTH);
        add(actionButtonsPanel, BorderLayout.SOUTH);

        pack();
        getRootPane().setDefaultButton(okButton);
        setResizable(false);
        setLocationRelativeTo(mainFrame);
        setVisible(true);
    }
}

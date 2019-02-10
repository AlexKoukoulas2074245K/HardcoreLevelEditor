package com.hardcoreleveleditor.dialogs;

import com.hardcoreleveleditor.components.IComponent;
import com.hardcoreleveleditor.components.ShaderComponent;
import com.hardcoreleveleditor.handlers.DisposeDialogHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class EditShaderComponentDialog extends JDialog
{
    public EditShaderComponentDialog(final JFrame mainFrame, IComponent abstractComponent)
    {
        super(mainFrame, "Edit Shader Component", Dialog.ModalityType.APPLICATION_MODAL);
        getRootPane().registerKeyboardAction(new DisposeDialogHandler(this), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
        setLayout(new BorderLayout());

        ShaderComponent shaderComponent = (ShaderComponent)abstractComponent;

        JPanel shaderPropertiesPanel = new JPanel();
        JTextField shaderNameTextField = new JTextField(shaderComponent.getShaderName(), 12);

        shaderPropertiesPanel.add(new JLabel("Shader Name: "));
        shaderPropertiesPanel.add(shaderNameTextField);

        JButton okButton = new JButton("Ok");
        okButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                shaderComponent.setShaderName(shaderNameTextField.getText());
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

        add(shaderPropertiesPanel, BorderLayout.NORTH);
        add(actionButtonsPanel, BorderLayout.SOUTH);

        pack();
        getRootPane().setDefaultButton(okButton);
        setResizable(false);
        setLocationRelativeTo(mainFrame);
        setVisible(true);
    }
}

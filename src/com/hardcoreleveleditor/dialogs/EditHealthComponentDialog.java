package com.hardcoreleveleditor.dialogs;

import com.hardcoreleveleditor.components.AnimationComponent;
import com.hardcoreleveleditor.components.HealthComponent;
import com.hardcoreleveleditor.components.IComponent;
import com.hardcoreleveleditor.handlers.DisposeDialogHandler;
import com.hardcoreleveleditor.panels.LabelledInputPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;

public class EditHealthComponentDialog extends JDialog
{
    public EditHealthComponentDialog(final JFrame mainFrame, IComponent abstractComponent)
    {
        super(mainFrame, "Edit Health Component", Dialog.ModalityType.APPLICATION_MODAL);
        setLayout(new BorderLayout());
        getRootPane().registerKeyboardAction(new DisposeDialogHandler(this), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

        HealthComponent healthComponent = (HealthComponent) abstractComponent;

        DecimalFormat healthValuesFormatter = new DecimalFormat("#.#####");

        LabelledInputPanel healthPointsPanel = new LabelledInputPanel("Health Points: ", healthValuesFormatter, 10, healthComponent.getHealth());
        LabelledInputPanel healthInvincibilityDurationPanel = new LabelledInputPanel("Invincibility Duration: ", healthValuesFormatter, 10, healthComponent.getInvincibilityDuration());

        JPanel healthComponentPropertiesPanel = new JPanel(new BorderLayout());
        healthComponentPropertiesPanel.add(healthPointsPanel, BorderLayout.NORTH);
        healthComponentPropertiesPanel.add(healthInvincibilityDurationPanel, BorderLayout.SOUTH);

        JButton okButton = new JButton("Ok");
        okButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                healthComponent.setHealth(Double.parseDouble(healthPointsPanel.getTextField().getText()));
                healthComponent.setInvincibilityDuration(Double.parseDouble(healthInvincibilityDurationPanel.getTextField().getText()));
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

        add(healthComponentPropertiesPanel, BorderLayout.NORTH);
        add(actionButtonsPanel, BorderLayout.SOUTH);
        pack();
        getRootPane().setDefaultButton(okButton);
        setResizable(false);
        setLocationRelativeTo(mainFrame);
        setVisible(true);
    }
}

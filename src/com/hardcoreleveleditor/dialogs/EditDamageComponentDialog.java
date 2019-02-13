package com.hardcoreleveleditor.dialogs;

import com.hardcoreleveleditor.components.AnimationComponent;
import com.hardcoreleveleditor.components.DamageComponent;
import com.hardcoreleveleditor.components.IComponent;
import com.hardcoreleveleditor.components.PhysicsComponent;
import com.hardcoreleveleditor.handlers.DisposeDialogHandler;
import com.hardcoreleveleditor.panels.LabelledInputPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;

public class EditDamageComponentDialog extends JDialog
{
    public EditDamageComponentDialog(final JFrame mainFrame, IComponent abstractComponent)
    {
        super(mainFrame, "Edit Damage Component", Dialog.ModalityType.APPLICATION_MODAL);
        setLayout(new BorderLayout());
        getRootPane().registerKeyboardAction(new DisposeDialogHandler(this), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

        DamageComponent damageComponent = (DamageComponent) abstractComponent;

        DecimalFormat damageValuesFormatter = new DecimalFormat("#.#####");

        LabelledInputPanel damagePanel = new LabelledInputPanel("Damage: ", damageValuesFormatter, 10, damageComponent.getDamage());

        JLabel canDamageSameEntityMultipleTimesLabel = new JLabel("Can Damage Same Entity Multiple Times: ");
        String[] booleanValues = { "true", "false" };
        JComboBox booleanDropdown = new JComboBox(booleanValues);
        booleanDropdown.setSelectedIndex(damageComponent.getCanDamageSameEntityMultipleTimes() ? 0 : 1);

        JPanel damageCanDamageSameEntityMultipleTimesPanel = new JPanel();
        damageCanDamageSameEntityMultipleTimesPanel.add(canDamageSameEntityMultipleTimesLabel);
        damageCanDamageSameEntityMultipleTimesPanel.add(booleanDropdown);

        JPanel damagePropertiesPanel = new JPanel(new BorderLayout());
        damagePropertiesPanel.add(damagePanel, BorderLayout.NORTH);
        damagePropertiesPanel.add(damageCanDamageSameEntityMultipleTimesPanel, BorderLayout.SOUTH);

        JButton okButton = new JButton("Ok");
        okButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                damageComponent.setDamage(Double.parseDouble(damagePanel.getTextField().getText()));
                damageComponent.setCanDamageSameEntityMultipleTimes(booleanDropdown.getSelectedIndex() == 0);
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

        add(damagePropertiesPanel, BorderLayout.NORTH);
        add(actionButtonsPanel, BorderLayout.SOUTH);
        pack();
        getRootPane().setDefaultButton(okButton);
        setResizable(false);
        setLocationRelativeTo(mainFrame);
        setVisible(true);
    }
}

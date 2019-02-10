package com.hardcoreleveleditor.dialogs;

import com.hardcoreleveleditor.components.AnimationComponent;
import com.hardcoreleveleditor.components.IComponent;
import com.hardcoreleveleditor.handlers.DisposeDialogHandler;
import com.hardcoreleveleditor.panels.LabelledInputPanel;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class EditAnimationComponentDialog extends JDialog
{
    public EditAnimationComponentDialog(final JFrame mainFrame, IComponent abstractComponent)
    {
        super(mainFrame, "Edit Animation Component", Dialog.ModalityType.APPLICATION_MODAL);
        setLayout(new BorderLayout());
        getRootPane().registerKeyboardAction(new DisposeDialogHandler(this), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

        AnimationComponent animationComponent = (AnimationComponent) abstractComponent;
        JLabel animationNameLabel = new JLabel("Animation Name: ");
        JTextField animationNameTextField = new JTextField(animationComponent.getAnimationName(), animationComponent.getAnimationName().length() * 2/3);
        animationNameTextField.setEditable(false);

        JPanel animationNamePanel = new JPanel();
        animationNamePanel.add(animationNameLabel);
        animationNamePanel.add(animationNameTextField);

        DecimalFormat animationTimerValuesFormatter = new DecimalFormat("#.#####");

        LabelledInputPanel animationFrameDurationPanel = new LabelledInputPanel("Animation Frame Duration: ", animationTimerValuesFormatter, 10, animationComponent.getAnimationFrameDuration());
        JPanel animationFrameDurationMasterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        animationFrameDurationMasterPanel.add(animationFrameDurationPanel);

        JPanel animationPropertiesPanel = new JPanel(new BorderLayout());
        animationPropertiesPanel.add(animationNamePanel, BorderLayout.NORTH);
        animationPropertiesPanel.add(animationFrameDurationMasterPanel, BorderLayout.SOUTH);

        JButton okButton = new JButton("Ok");
        okButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                animationComponent.setAnimationFrameDuration(Double.parseDouble(animationFrameDurationPanel.getTextField().getText()));
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

        add(animationPropertiesPanel, BorderLayout.NORTH);
        add(actionButtonsPanel, BorderLayout.SOUTH);
        pack();
        getRootPane().setDefaultButton(okButton);
        setResizable(false);
        setLocationRelativeTo(mainFrame);
        setVisible(true);
    }
}

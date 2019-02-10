package com.hardcoreleveleditor.handlers;

import com.hardcoreleveleditor.main.MainFrame;
import com.hardcoreleveleditor.panels.LabelledInputPanel;
import com.hardcoreleveleditor.panels.MainPanel;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;

public class NewMenuItemActionHandler implements ActionListener
{
    private final MainFrame mainFrame;

    public NewMenuItemActionHandler(final MainFrame mainFrame)
    {
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        JDialog jDialog = new JDialog(mainFrame, "New Level Specification", Dialog.ModalityType.APPLICATION_MODAL);
        jDialog.getRootPane().registerKeyboardAction(new DisposeDialogHandler(jDialog), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

        NumberFormatter dimensionsFormatter = new NumberFormatter(NumberFormat.getInstance());
        dimensionsFormatter.setValueClass(Integer.class);
        dimensionsFormatter.setMinimum(1);
        dimensionsFormatter.setCommitsOnValidEdit(false);

        LabelledInputPanel levelColsPanel = new LabelledInputPanel("Columns: ", dimensionsFormatter, 2, 20);
        LabelledInputPanel levelRowsPanel = new LabelledInputPanel("Rows: ", dimensionsFormatter, 2, 20);

        JPanel newLevelDimensionsPanel = new JPanel();
        newLevelDimensionsPanel.setLayout(new BoxLayout(newLevelDimensionsPanel, BoxLayout.X_AXIS));
        newLevelDimensionsPanel.add(levelColsPanel);
        newLevelDimensionsPanel.add(new JLabel("x"));
        newLevelDimensionsPanel.add(levelRowsPanel);

        LabelledInputPanel newLevelCellSizePanel = new LabelledInputPanel("Cell Size: ", dimensionsFormatter, 3, 80);

        JPanel newLevelSpecsPanel = new JPanel();
        newLevelSpecsPanel.setLayout(new BoxLayout(newLevelSpecsPanel, BoxLayout.Y_AXIS));
        newLevelSpecsPanel.add(newLevelDimensionsPanel);
        newLevelSpecsPanel.add(newLevelCellSizePanel);

        JButton createButton = new JButton("Create");
        createButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                mainFrame.resetContentPane(new MainPanel(mainFrame, (int)levelRowsPanel.getTextField().getValue(), (int)levelColsPanel.getTextField().getValue(), (int)newLevelCellSizePanel.getTextField().getValue()));
                jDialog.dispose();
                mainFrame.getRootPane().revalidate();
                mainFrame.getRootPane().repaint();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new DisposeDialogHandler(jDialog));

        JPanel actionButtonsPanel = new JPanel();
        actionButtonsPanel.add(createButton);
        actionButtonsPanel.add(cancelButton);

        JPanel newLevelActionButtonsPanel = new JPanel(new BorderLayout());
        newLevelActionButtonsPanel.add(actionButtonsPanel, BorderLayout.EAST);

        JPanel newLevelPanel = new JPanel(new BorderLayout());
        newLevelPanel.add(newLevelSpecsPanel, BorderLayout.NORTH);
        newLevelPanel.add(newLevelActionButtonsPanel, BorderLayout.SOUTH);

        jDialog.setContentPane(newLevelPanel);
        jDialog.getRootPane().setDefaultButton(createButton);
        jDialog.pack();
        jDialog.setResizable(false);
        jDialog.setLocationRelativeTo(mainFrame);
        jDialog.setVisible(true);
    }
}

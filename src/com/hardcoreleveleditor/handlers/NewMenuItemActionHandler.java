package com.hardcoreleveleditor.handlers;

import com.hardcoreleveleditor.main.MainFrame;
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

        JFormattedTextField colsField = new JFormattedTextField(dimensionsFormatter);
        JPanel levelColsPanel = createLevelSpecInputPanel(colsField,"Columns: ", 20, dimensionsFormatter);

        JFormattedTextField rowsField = new JFormattedTextField(dimensionsFormatter);
        JPanel levelRowsPanel = createLevelSpecInputPanel(rowsField, "Rows: ", 20, dimensionsFormatter);

        JPanel newLevelDimensionsPanel = new JPanel();
        newLevelDimensionsPanel.setLayout(new BoxLayout(newLevelDimensionsPanel, BoxLayout.X_AXIS));
        newLevelDimensionsPanel.add(levelColsPanel);
        newLevelDimensionsPanel.add(new JLabel("x"));
        newLevelDimensionsPanel.add(levelRowsPanel);

        JFormattedTextField cellSizeField = new JFormattedTextField(dimensionsFormatter);
        JPanel newLevelCellSizePanel = createLevelSpecInputPanel(cellSizeField, "Cell Size: ", 32, dimensionsFormatter);

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
                mainFrame.resetContentPane(new MainPanel(mainFrame, (int)rowsField.getValue(), (int)colsField.getValue(), (int)cellSizeField.getValue()));
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

    private JPanel createLevelSpecInputPanel(final JFormattedTextField inputField, final String inputPanelDescription, final int defaultValue, final NumberFormatter inputFormatter)
    {
        JLabel levelSpecInputLabel = new JLabel(inputPanelDescription);
        inputField.setValue(defaultValue);
        inputField.setColumns(3);
        inputField.addFocusListener(new SelectAllFocusListener(inputField));

        JPanel levelSpecPanel = new JPanel();
        levelSpecPanel.add(levelSpecInputLabel);
        levelSpecPanel.add(inputField);

        return levelSpecPanel;
    }
}

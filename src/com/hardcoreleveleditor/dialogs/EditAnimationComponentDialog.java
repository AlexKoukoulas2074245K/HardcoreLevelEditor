package com.hardcoreleveleditor.dialogs;

import com.hardcoreleveleditor.components.IComponent;
import com.hardcoreleveleditor.handlers.DisposeDialogHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class EditAnimationComponentDialog extends JDialog
{
    public EditAnimationComponentDialog(final JFrame mainFrame, IComponent animationComponent)
    {
        super(mainFrame, "Edit Animation Component", Dialog.ModalityType.APPLICATION_MODAL);
        getRootPane().registerKeyboardAction(new DisposeDialogHandler(this), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

        //setContentPane(newLevelPanel);
        //getRootPane().setDefaultButton(createButton);
        //pack();
        setResizable(false);
        setLocationRelativeTo(mainFrame);
        setVisible(true);
    }
}

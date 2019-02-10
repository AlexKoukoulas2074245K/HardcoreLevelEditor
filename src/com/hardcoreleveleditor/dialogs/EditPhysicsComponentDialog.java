package com.hardcoreleveleditor.dialogs;

import com.hardcoreleveleditor.components.IComponent;
import com.hardcoreleveleditor.components.PhysicsComponent;
import com.hardcoreleveleditor.handlers.DisposeDialogHandler;
import com.hardcoreleveleditor.panels.LabelledInputPanel;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;

public class EditPhysicsComponentDialog extends JDialog
{
    public EditPhysicsComponentDialog(final JFrame mainFrame, IComponent abstractComponent)
    {
        super(mainFrame, "Edit Physics Component", Dialog.ModalityType.APPLICATION_MODAL);
        getRootPane().registerKeyboardAction(new DisposeDialogHandler(this), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
        setLayout(new BorderLayout());

        PhysicsComponent physicsComponent = (PhysicsComponent)abstractComponent;

        JPanel physicsBodyTypePanel = new JPanel();
        physicsBodyTypePanel.add(new JLabel("Physics Body Type: "));

        String[] bodyTypes = { PhysicsComponent.BodyType.STATIC.toString(), PhysicsComponent.BodyType.KINEMATIC.toString(), PhysicsComponent.BodyType.DYNAMIC.toString() };
        JComboBox bodyTypeDropdown = new JComboBox(bodyTypes);
        bodyTypeDropdown.setSelectedIndex(Arrays.asList(bodyTypes).indexOf(physicsComponent.bodyType.toString()));
        physicsBodyTypePanel.add(bodyTypeDropdown);

        DecimalFormat physicsValuesFormatter = new DecimalFormat("#.#####");

        LabelledInputPanel physicsGravityXInputPanel = new LabelledInputPanel("x:", physicsValuesFormatter, 10, physicsComponent.gravity.getX());
        LabelledInputPanel physicsGravityYInputPanel = new LabelledInputPanel("y:", physicsValuesFormatter, 10, physicsComponent.gravity.getY());
        LabelledInputPanel physicsGravityZInputPanel = new LabelledInputPanel("z:", physicsValuesFormatter, 10, physicsComponent.gravity.getZ());

        JPanel physicsGravityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        physicsGravityPanel.add(new JLabel("Physics gravity "));
        physicsGravityPanel.add(physicsGravityXInputPanel);
        physicsGravityPanel.add(physicsGravityYInputPanel);
        physicsGravityPanel.add(physicsGravityZInputPanel);

        LabelledInputPanel physicsMaxVelocityXInputPanel = new LabelledInputPanel("x:", physicsValuesFormatter, 10, physicsComponent.maxVelocity.getX());
        LabelledInputPanel physicsMaxVelocityYInputPanel = new LabelledInputPanel("y:", physicsValuesFormatter, 10, physicsComponent.maxVelocity.getY());
        LabelledInputPanel physicsMaxVelocityZInputPanel = new LabelledInputPanel("z:", physicsValuesFormatter, 10, physicsComponent.maxVelocity.getZ());

        JPanel physicsMaxVelocityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        physicsMaxVelocityPanel.add(new JLabel("Physics Max Velocity "));
        physicsMaxVelocityPanel.add(physicsMaxVelocityXInputPanel);
        physicsMaxVelocityPanel.add(physicsMaxVelocityYInputPanel);
        physicsMaxVelocityPanel.add(physicsMaxVelocityZInputPanel);

        LabelledInputPanel physicsMinVelocityXInputPanel = new LabelledInputPanel("x:", physicsValuesFormatter, 10, physicsComponent.minVelocity.getX());
        LabelledInputPanel physicsMinVelocityYInputPanel = new LabelledInputPanel("y:", physicsValuesFormatter, 10, physicsComponent.minVelocity.getY());
        LabelledInputPanel physicsMinVelocityZInputPanel = new LabelledInputPanel("z:", physicsValuesFormatter, 10, physicsComponent.minVelocity.getZ());

        JPanel physicsMinVelocityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        physicsMinVelocityPanel.add(new JLabel("Physics Min Velocity "));
        physicsMinVelocityPanel.add(physicsMinVelocityXInputPanel);
        physicsMinVelocityPanel.add(physicsMinVelocityYInputPanel);
        physicsMinVelocityPanel.add(physicsMinVelocityZInputPanel);

        LabelledInputPanel physicsHitBoxCenterPointXPanel = new LabelledInputPanel("x:", physicsValuesFormatter, 10, physicsComponent.hitBoxCenterPoint.getX());
        LabelledInputPanel physicsHitBoxCenterPointYPanel = new LabelledInputPanel("y:", physicsValuesFormatter, 10, physicsComponent.hitBoxCenterPoint.getY());

        JPanel physicsHitBoxCenterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        physicsHitBoxCenterPanel.add(new JLabel("Physics HitBox Center "));
        physicsHitBoxCenterPanel.add(physicsHitBoxCenterPointXPanel);
        physicsHitBoxCenterPanel.add(physicsHitBoxCenterPointYPanel);


        LabelledInputPanel physicsHitBoxWidthPanel = new LabelledInputPanel("width: ", physicsValuesFormatter, 10, physicsComponent.hitBoxDimensions.getX());
        LabelledInputPanel physicsHitBoxHeightPanel = new LabelledInputPanel("height: ", physicsValuesFormatter, 10, physicsComponent.hitBoxDimensions.getY());

        JPanel physicsHitBoxDimensionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        physicsHitBoxDimensionsPanel.add(new JLabel("Physics HitBox "));
        physicsHitBoxDimensionsPanel.add(physicsHitBoxWidthPanel);
        physicsHitBoxDimensionsPanel.add(physicsHitBoxHeightPanel);

        JPanel physicsPropertiesPanel = new JPanel();
        physicsPropertiesPanel.setLayout(new BoxLayout(physicsPropertiesPanel, BoxLayout.Y_AXIS));
        physicsPropertiesPanel.add(physicsBodyTypePanel);
        physicsPropertiesPanel.add(physicsGravityPanel);
        physicsPropertiesPanel.add(physicsMaxVelocityPanel);
        physicsPropertiesPanel.add(physicsMinVelocityPanel);
        physicsPropertiesPanel.add(physicsHitBoxCenterPanel);
        physicsPropertiesPanel.add(physicsHitBoxDimensionsPanel);

        JButton okButton = new JButton("Ok");
        okButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                physicsComponent.bodyType = PhysicsComponent.BodyType.valueOf(bodyTypes[bodyTypeDropdown.getSelectedIndex()]);
                physicsComponent.gravity = new Point3D(Double.parseDouble(physicsGravityYInputPanel.getTextField().getText()), Double.parseDouble(physicsGravityYInputPanel.getTextField().getText()), Double.parseDouble(physicsGravityZInputPanel.getTextField().getText()));
                physicsComponent.maxVelocity = new Point3D(Double.parseDouble(physicsMaxVelocityXInputPanel.getTextField().getText()), Double.parseDouble(physicsMaxVelocityYInputPanel.getTextField().getText()), Double.parseDouble(physicsMaxVelocityZInputPanel.getTextField().getText()));
                physicsComponent.minVelocity = new Point3D(Double.parseDouble(physicsMinVelocityXInputPanel.getTextField().getText()), Double.parseDouble(physicsMinVelocityYInputPanel.getTextField().getText()), Double.parseDouble(physicsMinVelocityZInputPanel.getTextField().getText()));
                physicsComponent.hitBoxCenterPoint = new Point2D(Double.parseDouble(physicsHitBoxCenterPointXPanel.getTextField().getText()), Double.parseDouble(physicsHitBoxCenterPointYPanel.getTextField().getText()));
                physicsComponent.hitBoxDimensions = new Point2D(Double.parseDouble(physicsHitBoxWidthPanel.getTextField().getText()), Double.parseDouble(physicsHitBoxHeightPanel.getTextField().getText()));

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

        add(physicsPropertiesPanel, BorderLayout.NORTH);
        add(actionButtonsPanel, BorderLayout.SOUTH);

        pack();
        getRootPane().setDefaultButton(okButton);
        setResizable(false);
        setLocationRelativeTo(mainFrame);
        setVisible(true);
    }
}

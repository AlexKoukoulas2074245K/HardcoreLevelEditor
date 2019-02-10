package com.hardcoreleveleditor.panels;

import com.hardcoreleveleditor.components.IComponent;
import com.hardcoreleveleditor.dialogs.EditAnimationComponentDialog;
import com.sun.tools.corba.se.idl.constExpr.BooleanOr;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.ByteOrder;
import java.util.Map;

public class ComponentsPanel extends JPanel
{
    private static final int COMPONENT_HEIGHT = 60;

    private final JFrame mainFrame;

    public ComponentsPanel(final JFrame mainFrame)
    {
        super();
        this.mainFrame = mainFrame;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.white);
        g2.fillRect(0, 0, getSize().width, getSize().height);
        g2.dispose();
    }

    public void onCellPressed(final GridCellPanel cell)
    {
        removeAll();

        if (cell != null && cell.isResourceCell() == false)
        {
            Map<String, IComponent> cellComponents = cell.getCellComponents();
            for (Map.Entry<String, IComponent> entry: cellComponents.entrySet())
            {
                JPanel componentPanel = new JPanel(new BorderLayout());

                JPanel componentNamePanel = new JPanel(new BorderLayout());
                componentNamePanel.add(new JLabel(entry.getKey(), SwingConstants.CENTER), BorderLayout.CENTER);
                componentNamePanel.setBorder(new EmptyBorder(5, 0, 5,0 ));


                JButton editButton = new JButton("Edit");
                editButton.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        new EditAnimationComponentDialog(mainFrame, entry.getValue());
                    }
                });

                JButton deleteButton = new JButton("Delete");
                deleteButton.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        cell.setAnimationImage(null, "");
                        cellComponents.remove(entry.getKey());

                        remove(componentPanel);
                        getRootPane().revalidate();
                        getRootPane().repaint();
                    }
                });

                JPanel componentButtonsPanel = new JPanel();
                componentButtonsPanel.setLayout(new BoxLayout(componentButtonsPanel, BoxLayout.X_AXIS));
                componentButtonsPanel.add(editButton);
                componentButtonsPanel.add(deleteButton);

                componentPanel.setBorder(new LineBorder(Color.BLACK));
                componentPanel.setPreferredSize(new Dimension(getWidth(), COMPONENT_HEIGHT));
                componentPanel.setMinimumSize(new Dimension(getWidth(), COMPONENT_HEIGHT));
                componentPanel.setMaximumSize(new Dimension(getWidth(), COMPONENT_HEIGHT));

                componentPanel.add(componentNamePanel, BorderLayout.NORTH);
                componentPanel.add(componentButtonsPanel, BorderLayout.SOUTH);

                add(componentPanel);
            }
        }
    }
}

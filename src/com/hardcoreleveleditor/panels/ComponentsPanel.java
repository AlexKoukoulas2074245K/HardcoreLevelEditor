package com.hardcoreleveleditor.panels;

import com.hardcoreleveleditor.components.IComponent;
import com.hardcoreleveleditor.dialogs.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.TreeMap;

public class ComponentsPanel extends JPanel
{
    private static final int COMPONENT_HEIGHT = 60;

    private final JFrame mainFrame;
    private final Map<String, Component> componentNamesToGUIComponents = new TreeMap<>();

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

    public void updateComponentsPanel()
    {
        removeAll();
        componentNamesToGUIComponents.clear();

        if (GridCellPanel.sSelectedGridCell != null && GridCellPanel.sSelectedGridCell.isResourceCell() == false)
        {
            Map<String, IComponent> cellComponents = GridCellPanel.sSelectedGridCell.getCellComponents();
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
                        switch (entry.getKey())
                        {
                            case "AnimationComponent": new EditAnimationComponentDialog(mainFrame, entry.getValue()); break;
                            case "AIComponent": new EditAIComponentDialog(mainFrame, entry.getValue()); break;
                            case "PhysicsComponent": new EditPhysicsComponentDialog(mainFrame, entry.getValue()); break;
                            case "ShaderComponent": new EditShaderComponentDialog(mainFrame, entry.getValue()); break;
                            case "HealthComponent": new EditHealthComponentDialog(mainFrame, entry.getValue()); break;
                            case "DamageComponent": new EditDamageComponentDialog(mainFrame, entry.getValue()); break;
                        }

                    }
                });

                JButton deleteButton = new JButton("Delete");
                deleteButton.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        if (entry.getKey().equals("AnimationComponent") || entry.getKey().equals("ShaderComponent"))
                        {
                            GridCellPanel.sSelectedGridCell.setAnimationImage(null, "");
                            if (entry.getKey().equals("AnimationComponent"))
                            {
                                cellComponents.remove("ShaderComponent");
                                remove(componentNamesToGUIComponents.get("ShaderComponent"));
                                componentNamesToGUIComponents.remove("ShaderComponent");
                            }
                            else
                            {
                                cellComponents.remove("AnimationComponent");
                                remove(componentNamesToGUIComponents.get("AnimationComponent"));
                                componentNamesToGUIComponents.remove("AnimationComponent");
                            }
                        }

                        cellComponents.remove(entry.getKey());
                        remove(componentPanel);
                        componentNamesToGUIComponents.remove(entry.getKey());

                        getRootPane().revalidate();
                        getRootPane().repaint();
                    }
                });

                JPanel componentButtonsPanel = new JPanel();
                componentButtonsPanel.add(editButton);
                componentButtonsPanel.add(deleteButton);

                componentPanel.setBorder(new LineBorder(Color.BLACK));
                componentPanel.setPreferredSize(new Dimension(getWidth(), COMPONENT_HEIGHT));
                componentPanel.setMinimumSize(new Dimension(getWidth(), COMPONENT_HEIGHT));
                componentPanel.setMaximumSize(new Dimension(getWidth(), COMPONENT_HEIGHT));

                componentPanel.add(componentNamePanel, BorderLayout.NORTH);
                componentPanel.add(componentButtonsPanel, BorderLayout.SOUTH);

                add(componentPanel);
                componentNamesToGUIComponents.put(entry.getKey(), componentPanel);
            }
        }
    }
}

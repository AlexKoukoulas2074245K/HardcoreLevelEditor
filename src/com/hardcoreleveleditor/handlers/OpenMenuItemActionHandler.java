package com.hardcoreleveleditor.handlers;

import com.hardcoreleveleditor.components.*;
import com.hardcoreleveleditor.main.MainFrame;
import com.hardcoreleveleditor.panels.GridCellPanel;
import com.hardcoreleveleditor.panels.MainPanel;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;

public class OpenMenuItemActionHandler implements ActionListener
{
    private final MainFrame mainFrame;

    public OpenMenuItemActionHandler(final MainFrame mainFrame)
    {
        this.mainFrame = mainFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        JFileChooser fc = new JFileChooser(".");
        FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("JSON (*.json)", "json");
        fc.setFileFilter(fileFilter);

        int choice = fc.showOpenDialog(mainFrame);
        if (choice == JFileChooser.APPROVE_OPTION)
        {
            loadLevelFromFile(fc.getSelectedFile());
        }
    }

    private void loadLevelFromFile(final File file)
    {
        try
        {
            String fileContents = new String(Files.readAllBytes(file.toPath()));
            JSONObject rootJsonObject = new JSONObject(fileContents);

            final int cellSize = 80;
            final int cellCols = (int)(rootJsonObject.getJSONObject("horBounds").getDouble("right")/cellSize);
            final int cellRows = (int)(rootJsonObject.getJSONObject("verBounds").getDouble("top")/cellSize);

            MainPanel mainPanel = new MainPanel(mainFrame, cellRows ,cellCols, cellSize);
            mainFrame.resetContentPane(mainPanel);

            JSONArray entityArray = rootJsonObject.getJSONArray("entities");

            if (entityArray.getJSONObject(0).getString("name").equals("background"))
            {
                JSONObject backgroundObject = entityArray.getJSONObject(0);
                String backgroundAnimationName = backgroundObject.getJSONObject("components").getJSONObject("AnimationComponent").getString("path");
                mainPanel.getLevelEditorPanel().addBackgroundAnimation(mainPanel.getResourcePanel().getImageFromAnimationName(backgroundAnimationName), backgroundAnimationName);
            }

            for (int i = 1; i < entityArray.length(); ++i)
            {
                JSONObject entityObject = entityArray.getJSONObject(i);
                JSONObject entityComponents = entityObject.getJSONObject("components");
                JSONObject transformComponent = entityComponents.getJSONObject("TransformComponent");

                final int cellCol = (int)((transformComponent.getJSONArray("translation").getDouble(0) - cellSize/2)/cellSize);
                final int cellRow = (cellRows - 1) - (int)((transformComponent.getJSONArray("translation").getDouble(1) - cellSize/2)/cellSize);

                GridCellPanel targetGridCell = mainPanel.getLevelEditorPanel().getCellWithCoords(cellCol, cellRow);

                Iterator<String> componentKeyIterator = entityComponents.keys();
                while (componentKeyIterator.hasNext())
                {
                    String componentName = componentKeyIterator.next();

                    if (componentName.endsWith("AIComponent"))
                    {
                        targetGridCell.getCellComponents().put("AIComponent", new AIComponent(componentName));
                    }
                    else
                    {
                        JSONObject component = entityComponents.getJSONObject(componentName);
                        switch(componentName)
                        {
                            case "AnimationComponent":
                            {
                                final String animationName = component.getString("path");
                                targetGridCell.setAnimationName(animationName);
                                targetGridCell.setImage(mainPanel.getResourcePanel().getImageFromAnimationName(animationName));

                                AnimationComponent animationComponent = new AnimationComponent(animationName);
                                animationComponent.setAnimationFrameDuration(component.getDouble("animationFrameDuration"));

                                targetGridCell.getCellComponents().put("AnimationComponent", animationComponent);
                            } break;

                            case "DamageComponent":
                            {
                                DamageComponent damageComponent = new DamageComponent();
                                damageComponent.setDamage(component.getDouble("damage"));
                                damageComponent.setCanDamageSameEntityMultipleTimes(component.getBoolean("canDamageSameEntityMultipleTimes"));

                                targetGridCell.getCellComponents().put("DamageComponent", damageComponent);
                            } break;

                            case "HealthComponent":
                            {
                                HealthComponent healthComponent = new HealthComponent();
                                healthComponent.setHealth(component.getDouble("health"));
                                healthComponent.setInvincibilityDuration(component.getDouble("invincibilityDuration"));

                                targetGridCell.getCellComponents().put("HealthComponent", healthComponent);
                            } break;

                            case "PhysicsComponent":
                            {

                                PhysicsComponent physicsComponent = new PhysicsComponent();

                                physicsComponent.bodyType = PhysicsComponent.BodyType.valueOf(component.getString("bodyType"));

                                physicsComponent.hitBoxCenterPoint = new Point2D(component.getJSONObject("hitBox").getJSONArray("centerPoint").getDouble(0),
                                                                                 component.getJSONObject("hitBox").getJSONArray("centerPoint").getDouble(1));

                                physicsComponent.hitBoxDimensions = new Point2D(component.getJSONObject("hitBox").getJSONArray("dimensions").getDouble(0),
                                                                                component.getJSONObject("hitBox").getJSONArray("dimensions").getDouble(1));

                                physicsComponent.gravity = new Point3D(component.getJSONArray("gravity").getDouble(0),
                                                                       component.getJSONArray("gravity").getDouble(1),
                                                                       component.getJSONArray("gravity").getDouble(2));

                                physicsComponent.maxVelocity = new Point3D(component.getJSONArray("maxVelocity").getDouble(0),
                                                                           component.getJSONArray("maxVelocity").getDouble(1),
                                                                           component.getJSONArray("maxVelocity").getDouble(2));

                                physicsComponent.minVelocity = new Point3D(component.getJSONArray("minVelocity").getDouble(0),
                                                                           component.getJSONArray("minVelocity").getDouble(1),
                                                                           component.getJSONArray("minVelocity").getDouble(2));

                                targetGridCell.getCellComponents().put("PhysicsComponent", physicsComponent);
                            } break;

                            case "ShaderComponent":
                            {
                                ShaderComponent shaderComponent = new ShaderComponent("basic");
                                shaderComponent.setShaderName(component.getString("shaderName"));

                                targetGridCell.getCellComponents().put("ShaderComponent", shaderComponent);
                            } break;

                            default: break;
                        }
                    }
                }

                if (entityObject.getString("name").isEmpty() == false)
                {
                    targetGridCell.setCustomCellName(entityObject.getString("name"));
                }
            }

            mainFrame.getRootPane().revalidate();
            mainFrame.getRootPane().repaint();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}

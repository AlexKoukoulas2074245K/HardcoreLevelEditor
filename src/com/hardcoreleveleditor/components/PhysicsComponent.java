package com.hardcoreleveleditor.components;

import com.hardcoreleveleditor.panels.LevelEditorPanel;
import javafx.geometry.Point2D;
import javafx.geometry.Point3D;

public class PhysicsComponent implements IComponent
{
    public enum BodyType
    {
        STATIC, KINEMATIC, DYNAMIC
    }

    public BodyType bodyType;
    public Point3D gravity;
    public Point3D minVelocity;
    public Point3D maxVelocity;
    public Point2D hitBoxCenterPoint;
    public Point2D hitBoxDimensions;

    public PhysicsComponent()
    {
        this.bodyType = BodyType.STATIC;
        this.gravity = new Point3D(0.0, 0.0, 0.0);
        this.minVelocity = new Point3D(-10000.0, -10000.0, 0.0);
        this.maxVelocity = new Point3D(10000.0, 10000.0, 0.0);
        this.hitBoxCenterPoint = new Point2D(0.0, 0.0);
        this.hitBoxDimensions = new Point2D((double)LevelEditorPanel.sCurrentCellSize, (double)LevelEditorPanel.sCurrentCellSize);
    }

    @Override
    public IComponent getClone()
    {
        PhysicsComponent clone = new PhysicsComponent();
        clone.bodyType = this.bodyType;
        clone.gravity = this.gravity;
        clone.minVelocity = this.minVelocity;
        clone.maxVelocity = this.maxVelocity;
        clone.hitBoxCenterPoint = this.hitBoxCenterPoint;
        clone.hitBoxDimensions = this.hitBoxDimensions;
        return clone;
    }

}

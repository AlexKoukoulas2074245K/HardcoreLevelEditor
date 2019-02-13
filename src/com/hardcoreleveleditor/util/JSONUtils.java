package com.hardcoreleveleditor.util;


import javafx.geometry.Point2D;
import javafx.geometry.Point3D;

public class JSONUtils
{
    public static String toJSONString(final Point2D point2d)
    {
        return "[" + point2d.getX() + ", " + point2d.getY() + "]";
    }

    public static String toJSONString(final Point3D point3d)
    {
        return "[" + point3d.getX() + ", " + point3d.getY() + ", " + point3d.getZ() + "]";
    }

    public static String toJSONString(final boolean value)
    {
        return value ? "true" : "false";
    }
}

package com.hardcoreleveleditor.components;

public interface IComponent
{
    IComponent getClone();
    String toJSONString();
}

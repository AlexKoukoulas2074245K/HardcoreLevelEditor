package com.hardcoreleveleditor.components;

public class AIComponent implements IComponent
{
    private String aiComponentClassName;

    public AIComponent(final String aiComponentClassName)
    {
        this.aiComponentClassName = aiComponentClassName;
    }

    @Override
    public IComponent getClone()
    {
        AIComponent clone = new AIComponent(this.aiComponentClassName);
        return clone;
    }

    public String getAIComponentClassName()
    {
        return aiComponentClassName;
    }


    public void setAIComponentClassName(final String aiComponentClassName)
    {
        this.aiComponentClassName = aiComponentClassName;
    }
}

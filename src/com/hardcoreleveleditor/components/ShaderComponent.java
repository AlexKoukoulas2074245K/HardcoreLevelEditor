package com.hardcoreleveleditor.components;

public class ShaderComponent implements IComponent
{
    private String shaderName;

    public ShaderComponent(final String shaderName)
    {
        this.shaderName = shaderName;
    }

    @Override
    public IComponent getClone()
    {
        ShaderComponent clone = new ShaderComponent(this.shaderName);
        return clone;
    }

    @Override
    public String toJSONString()
    {
        return "\"ShaderComponent\": { \"shaderName\": \"" + shaderName + "\" }";
    }

    public String getShaderName()
    {
        return shaderName;
    }

    public void setShaderName(final String shaderName)
    {
        this.shaderName = shaderName;
    }
}

package com.hardcoreleveleditor.components;

public class AnimationComponent implements IComponent
{
    private final String animationName;
    private float animationTimer;

    public AnimationComponent(final String animationName)
    {
        this.animationName = animationName;
        this.animationTimer = 100.0f;
    }

    @Override
    public String getName()
    {
        return "AnimationComponent";
    }

    public String getAnimationName()
    {
        return animationName;
    }

    public float getAnimationTimer()
    {
        return animationTimer;
    }
}

package com.hardcoreleveleditor.components;

public class AnimationComponent implements IComponent
{
    private final String animationName;
    private double animationFrameDuration;

    public AnimationComponent(final String animationName)
    {
        this.animationName = animationName;
        this.animationFrameDuration = 100.0;
    }

    @Override
    public IComponent getClone()
    {
        AnimationComponent clone = new AnimationComponent(this.animationName);
        clone.animationFrameDuration = this.animationFrameDuration;
        return clone;
    }

    public String getAnimationName()
    {
        return animationName;
    }

    public double getAnimationFrameDuration()
    {
        return animationFrameDuration;
    }

    public void setAnimationFrameDuration(double animationFrameDuration) { this.animationFrameDuration = animationFrameDuration; }
}

package com.hardcoreleveleditor.components;

public class HealthComponent implements IComponent
{
    private double health;
    private double invincibilityDuration;

    public HealthComponent()
    {
        this.health = 100.0f;
        this.invincibilityDuration = 0.0f;
    }

    @Override
    public IComponent getClone()
    {
        HealthComponent clone = new HealthComponent();
        clone.setHealth(this.health);
        clone.setInvincibilityDuration(this.invincibilityDuration);

        return clone;
    }

    @Override
    public String toJSONString()
    {
        return "\"HealthComponent\": { \"health\": " + this.health + ", \"invincibilityDuration\": " + invincibilityDuration + " }";
    }

    public double getHealth()
    {
        return health;
    }

    public double getInvincibilityDuration()
    {
        return invincibilityDuration;
    }

    public void setHealth(final double health)
    {
        this.health = health;
    }

    public void setInvincibilityDuration(final double invincibilityDuration)
    {
        this.invincibilityDuration = invincibilityDuration;
    }
}

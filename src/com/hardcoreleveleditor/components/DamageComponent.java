package com.hardcoreleveleditor.components;

import com.hardcoreleveleditor.util.JSONUtils;

public class DamageComponent implements IComponent
{
    private double damage;
    private boolean canDamageSameEntityMultipleTimes;

    public DamageComponent()
    {
        this.damage = 0.0f;
        this.canDamageSameEntityMultipleTimes = true;
    }

    @Override
    public IComponent getClone()
    {
        DamageComponent clone = new DamageComponent();
        clone.setDamage(this.damage);
        clone.setCanDamageSameEntityMultipleTimes(this.canDamageSameEntityMultipleTimes);
        return clone;
    }

    @Override
    public String toJSONString()
    {
        return "\"DamageComponent\": { \"damage\": " + this.damage + ", \"canDamageSameEntityMultipleTimes\": " + JSONUtils.toJSONString(this.canDamageSameEntityMultipleTimes) + " }";
    }

    public double getDamage()
    {
        return this.damage;
    }

    public boolean getCanDamageSameEntityMultipleTimes()
    {
        return this.canDamageSameEntityMultipleTimes;
    }

    public void setDamage(final double damage)
    {
        this.damage = damage;
    }

    public void setCanDamageSameEntityMultipleTimes(final boolean canDamageSameEntityMultipleTimes)
    {
        this.canDamageSameEntityMultipleTimes = canDamageSameEntityMultipleTimes;
    }
}

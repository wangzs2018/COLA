package com.huawei.charging.domain.charge.chargeplan;

/**
 * 计费套餐抽象类
 * @param <T>
 */
public abstract class AbstractChargePlan<T extends Resource> implements Comparable<AbstractChargePlan>{

    protected int priority;

    public abstract  T getResource();

    public abstract ChargePlanType getType();

    /**
     * 不同套餐之间的优先级关系
     * @param other the object to be compared.
     * @return
     */
    @Override
    public int compareTo(AbstractChargePlan other) {
        return other.priority - this.priority;
    }

    @Override
    public String toString() {
        return "ChargePlan{chargeType=" + getType()+
                ", priority=" + priority +
                '}';
    }
}

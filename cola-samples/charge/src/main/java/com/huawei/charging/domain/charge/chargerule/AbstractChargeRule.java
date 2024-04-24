package com.huawei.charging.domain.charge.chargerule;

import com.huawei.charging.domain.charge.chargeplan.AbstractChargePlan;

/**
 * 计费规则抽象类
 */
public abstract class AbstractChargeRule implements ChargeRule{
    protected AbstractChargePlan abstractChargePlan;

    @Override
    public void belongsTo(AbstractChargePlan abstractChargePlan){
        this.abstractChargePlan = abstractChargePlan;
    }
}

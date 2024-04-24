package com.huawei.charging.domain.charge.chargerule;

import com.huawei.charging.domain.charge.chargeplan.ChargePlan;

/**
 * 计费规则抽象类
 */
public abstract class AbstractChargeRule implements ChargeRule{
    protected ChargePlan chargePlan;

    @Override
    public void belongsTo(ChargePlan chargePlan){
        this.chargePlan = chargePlan;
    }


}

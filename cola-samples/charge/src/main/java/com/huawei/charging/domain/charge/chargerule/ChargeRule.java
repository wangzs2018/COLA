package com.huawei.charging.domain.charge.chargerule;

import com.huawei.charging.domain.charge.ChargeRecord;
import com.huawei.charging.domain.charge.ChargeContext;
import com.huawei.charging.domain.charge.chargeplan.AbstractChargePlan;

public interface ChargeRule {
    ChargeRecord doCharge(ChargeContext ctx);

    void belongsTo(AbstractChargePlan abstractChargePlan);

}

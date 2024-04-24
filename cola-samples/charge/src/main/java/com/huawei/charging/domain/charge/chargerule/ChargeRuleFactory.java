package com.huawei.charging.domain.charge.chargerule;

import com.huawei.charging.domain.ApplicationContextHelper;
import com.huawei.charging.domain.charge.chargeplan.AbstractChargePlan;
import com.huawei.charging.domain.charge.chargeplan.ChargePlanType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 计费规则工厂类
 */
public class ChargeRuleFactory {
    public static CompositeChargeRule get(List<AbstractChargePlan> abstractChargePlanList) {
        //按套餐的优先级进行排序
        Collections.sort(abstractChargePlanList);

        List<ChargeRule> chargeRules = new ArrayList<>();
        for (AbstractChargePlan abstractChargePlan : abstractChargePlanList) {
            ChargeRule chargeRule;
            if (abstractChargePlan.getType() == ChargePlanType.FAMILY) {
                chargeRule = ApplicationContextHelper.getBean(FamilyChargeRule.class);
            } else if (abstractChargePlan.getType() == ChargePlanType.FIXED_TIME) {
                chargeRule = ApplicationContextHelper.getBean(FixedTimeChargeRule.class);
            } else {
                chargeRule = ApplicationContextHelper.getBean(BasicChargeRule.class);
            }
            chargeRule.belongsTo(abstractChargePlan);
            chargeRules.add(chargeRule);
        }
        CompositeChargeRule compositeChargeRule = new CompositeChargeRule();
        compositeChargeRule.chargeRules = chargeRules;
        return compositeChargeRule;
    }
}

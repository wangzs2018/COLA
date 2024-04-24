package com.huawei.charging.ut;


import com.huawei.charging.domain.charge.chargeplan.BasicAbstractChargePlan;
import com.huawei.charging.domain.charge.chargeplan.AbstractChargePlan;
import com.huawei.charging.domain.charge.chargeplan.ChargePlanType;
import com.huawei.charging.domain.charge.chargeplan.FamilyAbstractChargePlan;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChargeRecordPlanTest {

    @Test
    public void test_priority(){
        AbstractChargePlan basicAbstractChargePlan = new BasicAbstractChargePlan();
        AbstractChargePlan familyAbstractChargePlan = new FamilyAbstractChargePlan();
        AbstractChargePlan fixedTimeAbstractChargePlan = new FamilyAbstractChargePlan();
        List<AbstractChargePlan> abstractChargePlanList =  new ArrayList<>();
        abstractChargePlanList.add(basicAbstractChargePlan);
        abstractChargePlanList.add(familyAbstractChargePlan);
        abstractChargePlanList.add(fixedTimeAbstractChargePlan);

        Collections.sort(abstractChargePlanList);

        System.out.println(abstractChargePlanList.get(0));
        Assert.assertEquals(ChargePlanType.FAMILY, abstractChargePlanList.get(0).getType());

    }
}

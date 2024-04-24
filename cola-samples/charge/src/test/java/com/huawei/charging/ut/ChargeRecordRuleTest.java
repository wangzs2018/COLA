package com.huawei.charging.ut;

import com.huawei.charging.domain.account.Account;
import com.huawei.charging.domain.charge.CallType;
import com.huawei.charging.domain.charge.ChargeContext;
import com.huawei.charging.domain.charge.Money;
import com.huawei.charging.domain.charge.chargeplan.BasicAbstractChargePlan;
import com.huawei.charging.domain.charge.chargeplan.AbstractChargePlan;
import com.huawei.charging.domain.charge.chargeplan.FamilyAbstractChargePlan;
import com.huawei.charging.domain.charge.chargeplan.FixedTimeChangePlanAbstract;
import com.huawei.charging.domain.charge.chargerule.BasicChargeRule;
import com.huawei.charging.domain.charge.chargerule.FamilyChargeRule;
import com.huawei.charging.domain.charge.chargerule.FixedTimeChargeRule;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

public class ChargeRecordRuleTest {

    @Test
    public void test_basic_charge_rule(){
        //prepare
        AbstractChargePlan abstractChargePlan = new BasicAbstractChargePlan();
        Account account = new Account(13681874561L, Money.of(200), Collections.singletonList(abstractChargePlan));
        ChargeContext ctx = new ChargeContext(CallType.CALLING, 13681874561L, 15921582125L, 20);
        ctx.account = account;
        System.out.println("Account before charge: "+ account);

        //do
        BasicChargeRule basicChargeRule = new BasicChargeRule();
        basicChargeRule.belongsTo(abstractChargePlan);
        basicChargeRule.doCharge(ctx);

        //check
        System.out.println("Account after charge: "+ account);
        Assert.assertEquals( Money.of(100), ctx.account.getRemaining());
        Assert.assertEquals( 0, ctx.getDurationToCharge());
    }

    @Test
    public void test_family_charge_rule(){
        //prepare
        FamilyAbstractChargePlan chargePlan = new FamilyAbstractChargePlan();
        Account account = new Account(13681874561L, Money.of(200), Collections.singletonList(chargePlan));
        ChargeContext ctx = new ChargeContext(CallType.CALLING, 13681874561L, 15921582125L, 20);
        ctx.account = account;
        System.out.println("Account before charge: "+ account);

        //do
        FamilyChargeRule familyChargeRule = new FamilyChargeRule();
        familyChargeRule.belongsTo(chargePlan);
        familyChargeRule.doCharge(ctx);

        //check
        System.out.println("Account after charge: "+ account);
        Assert.assertEquals( Money.of(200), ctx.account.getRemaining());
        Assert.assertEquals( 0, ctx.getDurationToCharge());
    }

    @Test
    public void test_fixed_time_charge_rule(){
        //prepare
        FixedTimeChangePlanAbstract chargePlan = new FixedTimeChangePlanAbstract();
        Account account = new Account(13681874561L, Money.of(200), Collections.singletonList(chargePlan));
        ChargeContext ctx = new ChargeContext(CallType.CALLING, 13681874561L, 15921582125L, 180);
        ctx.account = account;
        System.out.println("Account before charge: "+ account);

        //do
        FixedTimeChargeRule fixedTimeChargeRule = new FixedTimeChargeRule();
        fixedTimeChargeRule.belongsTo(chargePlan);
        fixedTimeChargeRule.doCharge(ctx);

        //check
        System.out.println("Account after charge: "+ account);
        Assert.assertEquals( true, chargePlan.getResource().isCallingTimeRemaining());
        Assert.assertEquals( 0, ctx.getDurationToCharge());

        // come a new charge
        ChargeContext ctx2 = new ChargeContext(CallType.CALLING, 13681874561L, 15921582125L, 40);
        ctx2.account = account;
        fixedTimeChargeRule.doCharge(ctx2);
        Assert.assertEquals( false, chargePlan.getResource().isCallingTimeRemaining());
        Assert.assertEquals( 20, ctx2.getDurationToCharge());

        //reset fixed time
        FixedTimeChangePlanAbstract.FreeCallTime.FREE_CALLED_TIME = 200;
        FixedTimeChangePlanAbstract.FreeCallTime.FREE_CALLING_TIME = 200;
    }
}

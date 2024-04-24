package com.huawei.charging.domain.charge.chargeplan;

/**
 * 基础套餐
 */
public class BasicAbstractChargePlan extends AbstractChargePlan<BasicAbstractChargePlan.BasicChargeFee> {

    public BasicAbstractChargePlan(){
        this.priority = 0;
    }

    @Override
    public BasicChargeFee getResource() {
        return new BasicChargeFee();
    }

    @Override
    public ChargePlanType getType() {
        return ChargePlanType.BASIC;
    }

    public static class BasicChargeFee implements Resource{
        /**
         * 主叫单价。单位是角，5表示0.5元每分钟
         */
        public final int CALLING_PRICE = 5;

        /**
         * 被叫单价。单位是角，4表示0.4元每分钟
         */
        public final int CALLED_PRICE = 4;
    }
}

package com.huawei.charging.domain.charge.chargeplan;

/**
 * 固定时长套餐
 */
public class FixedTimeChangePlan extends ChargePlan<FixedTimeChangePlan.FreeCallTime>{

    public FixedTimeChangePlan() {
        this.priority=1;
    }

    @Override
    public FreeCallTime getResource() {
        return new FreeCallTime();
    }

    @Override
    public ChargePlanType getType() {
        return ChargePlanType.FIXED_TIME;
    }

    public static class FreeCallTime implements Resource{
        public static int FREE_CALLING_TIME = 200;
        public static int FREE_CALLED_TIME = 200;

        /**
         * 判断是否还有剩余的免费通话时间
         *
         * @return 如果剩余免费通话时间大于0，则返回true；否则返回false
         */
        public boolean isCallingTimeRemaining(){
            return FREE_CALLING_TIME > 0;
        }

        /**
         * 主叫-扣减固定时长套餐的费用
         * @param duration 扣减时长
         * @return 剩余还需要扣减的时长
         */
        public int chargeFreeCallingTime(int duration){
            if(duration > FREE_CALLING_TIME){
                int durationToCharge = duration - FREE_CALLING_TIME;
                FREE_CALLING_TIME = 0;
                return durationToCharge;
            }
            else{
                FREE_CALLING_TIME = FREE_CALLING_TIME - duration;
                return 0;
            }
        }

        /**
         * 判断是否还有剩余的免费被叫时间
         *
         * @return 如果剩余免费被叫时间大于0，则返回true；否则返回false
         */
        public boolean isCalledTimeRemaining(){
            return FREE_CALLED_TIME > 0;
        }

        /**
         * 被叫-扣减固定时长套餐的费用
         * @param duration 扣减时长
         * @return 剩余还需要扣减的时长
         */
        public int chargeFreeCalledTime(int duration){
            if(duration > FREE_CALLED_TIME){
                int durationToCharge = duration - FREE_CALLED_TIME;
                FREE_CALLED_TIME = 0;
                return durationToCharge;
            }
            else{
                FREE_CALLED_TIME = FREE_CALLED_TIME - duration;
                return 0;
            }
        }
    }
}

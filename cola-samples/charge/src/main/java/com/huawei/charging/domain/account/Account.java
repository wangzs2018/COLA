package com.huawei.charging.domain.account;

import com.huawei.charging.domain.BizException;
import com.huawei.charging.domain.DomainFactory;
import com.huawei.charging.domain.Entity;
import com.huawei.charging.domain.charge.*;
import com.huawei.charging.domain.charge.chargeplan.BasicAbstractChargePlan;
import com.huawei.charging.domain.charge.chargeplan.AbstractChargePlan;
import com.huawei.charging.domain.charge.chargerule.ChargeRuleFactory;
import com.huawei.charging.domain.charge.chargerule.CompositeChargeRule;
import com.huawei.charging.domain.gateway.AccountGateway;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * 账户
 */
@Data
@Entity
@Slf4j
public class Account {
    /**
     * 用户号码
     */
    private long phoneNo;

    /**
     * 账户余额
     */
    private Money remaining;

    /**
     * 账户所拥有的套餐
     */
    private List<AbstractChargePlan> abstractChargePlanList = new ArrayList<>();;

    @Resource
    private AccountGateway accountGateway;


    public Account(){

    }

    public Account(long phoneNo, Money amount, List<AbstractChargePlan> abstractChargePlanList){
        this.phoneNo = phoneNo;
        this.remaining = amount;
        this.abstractChargePlanList = abstractChargePlanList;
    }

    public static Account valueOf(long phoneNo, Money amount) {
        Account account = DomainFactory.get(Account.class);
        account.setPhoneNo(phoneNo);
        account.setRemaining(amount);
        account.abstractChargePlanList.add(new BasicAbstractChargePlan());
        return account;
    }

    /**
     * 检查账户余额是否足够
     */
    public void checkRemaining() {
        if (remaining.isLessThan(Money.of(0))) {
            throw BizException.of(this.phoneNo + " has insufficient amount");
        }
    }

    /**
     * 对账户进行计费
     *
     * @param ctx 计费上下文
     * @return 计费记录列表
     */
    public List<ChargeRecord> charge(ChargeContext ctx) {
        CompositeChargeRule compositeChargeRule = ChargeRuleFactory.get(abstractChargePlanList);
        List<ChargeRecord> chargeRecords = compositeChargeRule.doCharge(ctx);
        log.debug("Charges: "+ chargeRecords);

        //更新账户系统
        accountGateway.sync(phoneNo, chargeRecords);
        return chargeRecords;
    }

    @Override
    public String toString() {
        return "Account{" +
                "phoneNo=" + phoneNo +
                ", remaining=" + remaining +
                ", chargePlanList=" + abstractChargePlanList +
                '}';
    }
}

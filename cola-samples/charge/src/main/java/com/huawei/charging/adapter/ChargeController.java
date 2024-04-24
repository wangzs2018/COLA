package com.huawei.charging.adapter;

import com.huawei.charging.application.ChargeServiceI;
import com.huawei.charging.application.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 通话计费服务
 */
@RestController
@Slf4j
public class ChargeController {

    @Resource
    private ChargeServiceI chargeService;

    /**
     * 开始一个通话
     *
     * @param sessionId 通话ID
     * @param callingPhoneNo 主叫号码
     * @param calledPhoneNo 被叫号码
     * @return 返回会话开始的结果
     */
    @PostMapping("session/{sessionId}/begin")
    public Response begin(@PathVariable(name = "sessionId") String sessionId,
                          @RequestParam long callingPhoneNo,
                          @RequestParam long calledPhoneNo) {
        log.debug(sessionId + " " + callingPhoneNo + " " + calledPhoneNo);
        BeginSessionRequest request = new BeginSessionRequest(sessionId, callingPhoneNo, calledPhoneNo);
        return chargeService.begin(request);
    }

    /**
     * 结束一个通话
     *
     * @param sessionId 通话ID
     * @param duration 通话时长（单位：秒）
     * @return 结束会话的响应结果
     */
    @PostMapping("session/{sessionId}/end")
    public Response end(@PathVariable(name = "sessionId") String sessionId,
                        @RequestParam int duration) {
        log.debug(sessionId + " " + duration);
        EndSessionRequest request = new EndSessionRequest(sessionId, duration);
        return chargeService.end(request);
    }

    /**
     * 对指定通话进行计费
     *
     * @param sessionId 通话ID
     * @param duration 计费时长（单位：秒）
     * @return 计费结果
     */
    @PostMapping("session/{sessionId}/charge")
    public Response charge(@PathVariable(name = "sessionId") String sessionId,
                       @RequestParam int duration) {
        log.debug(sessionId + " " + duration);
        ChargeRequest request = new ChargeRequest(sessionId, duration);
        return chargeService.charge(request);
    }

    /**
     * 根据通话ID获取计费记录
     *
     * @param sessionId 会通话ID
     * @return 计费记录列表
     */
    @GetMapping("{sessionId}/chargeRecords")
    public MultiResponse<ChargeRecordDto> getChargeRecord(@PathVariable(name = "sessionId") String sessionId) {
        return chargeService.listChargeRecords(sessionId);
    }
}

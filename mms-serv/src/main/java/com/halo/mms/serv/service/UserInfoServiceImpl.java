package com.halo.mms.serv.service;

import cn.hutool.crypto.digest.DigestUtil;
import com.halo.mms.out.result.BaseResult;
import com.halo.mms.repo.model.UserContactInfoDO;
import com.halo.mms.repo.repository.UserContactInfoRepository;
import com.halo.mms.serv.configs.PushConfig;
import com.halo.mms.serv.exception.BadRequestException;
import com.halo.mms.serv.request.UserAuthRequest;
import com.halo.mms.serv.result.UserAuthResult;
import com.halo.mms.serv.service.api.UserAuthService;
import com.halo.mms.serv.service.api.UserInfoService;
import com.zjiecode.wxpusher.client.WxPusher;
import com.zjiecode.wxpusher.client.bean.CreateQrcodeReq;
import com.zjiecode.wxpusher.client.bean.CreateQrcodeResp;
import com.zjiecode.wxpusher.client.bean.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Slf4j
@Service
public class UserInfoServiceImpl implements UserInfoService {

    private static final int QR_CODE_INVALID_TIME = 1 * 3600;

    @Autowired
    private PushConfig pushConfig;
    @Autowired(required = false)
    private UserContactInfoRepository userContactInfoRepository;
    @Autowired
    private UserAuthService userAuthService;

    @Override
    public String generateQrCode() {
        return internalGenerateQrCode().orElseThrow(() -> new RuntimeException("二维码获取失败"));
    }

    @Override
    public BaseResult<UserAuthResult> authUser(UserAuthRequest request) {
        Optional<UserContactInfoDO> optionalUserContactInfoDO =
            userContactInfoRepository.findByNickName(request.getNickName());
        UserContactInfoDO userContactInfoDO =
            optionalUserContactInfoDO.orElseThrow(() -> new BadRequestException(403, "认证失败, 请检查输入的账号或密码"));

        String pwdMd5 = DigestUtil.md5Hex(request.getPwd() + ":caibudao");

        Assert.isTrue(userAuthService.auth(userContactInfoDO.getAuthInfo(), pwdMd5), "认证失败,请检查输入的账号或密码");

        String token = userAuthService.generateToken(userContactInfoDO);

        return BaseResult.getSucc(UserAuthResult.builder().token(token).build());
    }

    private Optional<String> internalGenerateQrCode() {

        CreateQrcodeReq createQrcodeReq = new CreateQrcodeReq();
        createQrcodeReq.setAppToken(pushConfig.getPushProperties().getAppToken());
        createQrcodeReq.setValidTime(QR_CODE_INVALID_TIME);
        createQrcodeReq.setExtra(pushConfig.getAppName());

        Result<CreateQrcodeResp> qrCode = WxPusher.createAppTempQrcode(createQrcodeReq);
        return Optional.ofNullable(qrCode).filter(Result::isSuccess).map(Result::getData).map(CreateQrcodeResp::getUrl);
    }

    @Override
    public UserContactInfoDO queryUser(String uid) {
        return userContactInfoRepository.findByUid(uid).orElse(null);
    }
}

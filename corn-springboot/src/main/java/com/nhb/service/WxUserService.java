package com.nhb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nhb.dto.WxUserLoginDto;
import com.nhb.entity.WxUser;
import com.nhb.utils.Result;


/**
 * (WxUser)服务接口
 *
 * @author 大只
 * @since 2022-11-12 16:27:16
 */
public interface WxUserService extends IService<WxUser> {

    Result wxUserLogin(WxUserLoginDto wxUserLoginDto);
}

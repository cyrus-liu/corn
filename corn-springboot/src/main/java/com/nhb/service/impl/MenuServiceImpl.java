package com.nhb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhb.entity.Menu;
import com.nhb.mapper.MenuMapper;
import com.nhb.service.MenuService;
import com.nhb.utils.Result;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 菜单权限表(Menu)服务实现类
 *
 * @author 大只
 * @since 2022-11-06 18:45:37
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public Result selectMenuPage(String keywords) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        //模糊查询
        queryWrapper.like(Objects.nonNull(keywords), Menu::getMenuName, keywords);
        //排序
        queryWrapper.orderByAsc(Menu::getOrderNum);
        List<Menu> list = list(queryWrapper);
        return Result.okResult(list);
    }
}

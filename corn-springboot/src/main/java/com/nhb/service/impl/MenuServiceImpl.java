package com.nhb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nhb.entity.Menu;
import com.nhb.mapper.MenuMapper;
import com.nhb.service.MenuService;
import org.springframework.stereotype.Service;

/**
 * 菜单权限表(Menu)服务实现类
 *
 * @author 大只
 * @since 2022-11-06 18:45:37
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

}

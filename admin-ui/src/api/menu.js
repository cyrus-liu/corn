import request from "@/utils/request";
import store from '@/store';
import router, {resetRouter} from "@/router/index";
import Layout from "@/layout/index";

//查询用户菜单
//动态路由
export function getUserMenuList() {
    request.get("/user/menus").then(({data: res}) => {
        //请求成功
        if (res.code === 200) {
            let um = res.data;

            //安全校验
            if (um && um.length === 0) {
                return
            }

            //封装路由组件
            for (let i = 0; i < um.length; i++) {

                //路由名字
                um[i].name = um[i].menuName

                //判断是不是目录
                if (um[i].parentId == 0 && um[i].menuType == 'M') {
                    um[i].component = Layout;
                }

                //判断是不是单个菜单
                if (um[i].parentId == 0 && um[i].menuType == 'C') {
                    //套壳
                    let shell = {
                        children: [],
                        component: Layout,
                        icon: um[i].icon,
                        menuName: um[i].menuName,
                        path: um[i].path,
                        menuType: 'C',
                        parentId: 0
                    }
                    shell.children.push(um[i])
                    um[i] = shell
                }

                //封装子路由
                if (um[i].children && um[i].children.length > 0) {
                    um[i].children.forEach(route => {
                        route.name = route.menuName
                        route.component = loadView(route.component);
                    });
                }

            }


            // 添加侧边栏菜单
            store.commit("setUserMenuList", um);

            //判断是否添加过用户菜单
            if (store.state.userMenuList.length > 0) {
                //清空用户菜单
                resetRouter()
            }

            //添加菜单到路由
            for (let i = 0; i < um.length; i++) {
                router.addRoute(um[i]);
            }

        } else {
            router.push({path: "/login"}, () => {
            });
        }
    })
}

//路由懒加载
export const loadView = view => {
    return resolve => require([`@/views${view}.vue`], resolve);
};

//获取菜单数据
export function getMenuList(keywords) {
    return request({
        url: '/menu/list',
        method: 'get',
        params: keywords
    })
}

//根据id查询单个菜单
export function getMenuBy(id) {
    return request({
        url: '/menu',
        method: 'get',
        params: id
    })
}

//新增菜单
export function addMenu(data) {
    return request({
        url: '/menu',
        method: 'post',
        data: data
    })
}

//修改菜单
export function updateMenu(data) {
    return request({
        url: '/menu',
        method: 'put',
        data: data
    })
}

//删除菜单
export function deleteMenu(id) {
    return request({
        url: '/menu',
        method: 'delete',
        params: id
    })
}
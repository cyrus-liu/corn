import Vue from 'vue'
import VueRouter from 'vue-router'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import store from "@/store";

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Login',
        component: () => import(/* webpackChunkName: "about" */ '@/views/Login')
    },
]

const createRouter = () =>
    new VueRouter({
        // mode: "history",
        routes: routes
    });

const router = createRouter();


//进度条配置
NProgress.configure({
    easing: "ease", // 动画方式
    speed: 500, // 递增进度条的速度
    showSpinner: false, // 是否显示加载ico
    trickleSpeed: 200, // 自动递增间隔
    minimum: 0.3 // 初始化时的最小百分比
});

//路由前置守卫
router.beforeEach((to, from, next) => {
    NProgress.start();
    //访问登录页放行
    if (to.path === "/") {
        next();
        //没有登录强制跳转登录页
    } else if (!store.state.token) {
        next("/");
    } else {
        //登录成功放行
        next();
    }
});


//路由后置钩子
router.afterEach(() => {
    NProgress.done();
});


//清空用户菜单
export function resetRouter() {
    const newRouter = createRouter();
    router.matcher = newRouter.matcher;
}

export default router
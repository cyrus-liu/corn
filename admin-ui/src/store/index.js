import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        //左边菜单状态
        collapse: false,
        //用户token
        token: null,
        //用户菜单列表
        userMenuList: [],
        //tab栏
        tabList: [],
        //用户账号
        userName:null
    },
    mutations: {
        setToken(state, token) {
            state.token = token;
        },
        setUserName(state, userName) {
            state.userName = userName;
        },
        removeToken(state) {
            state.token = null;
        },
        setCollapse(state) {
            state.collapse = !state.collapse;
        },
        setUserMenuList(state, userMenuList) {
            state.userMenuList = userMenuList;
        },
        setTab(state, tab) {
            if (state.tabList.findIndex(item => item.path === tab.path) === -1) {
                state.tabList.push({ name: tab.name, path: tab.path });
            }
        },
        removeTab(state, tab) {
            let index = state.tabList.findIndex(item => item.name === tab.name);
            state.tabList.splice(index, 1);
        },
        resetTab(state) {
            state.tabList = [{ name: "首页", path: "/home" }];
        },
    },
    actions: {
    },
    modules: {
    },

    //本地数据存储插件
    plugins: [
        createPersistedState({
            storage: window.sessionStorage
        })
    ]
})

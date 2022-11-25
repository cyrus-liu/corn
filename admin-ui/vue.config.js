const path = require("path");

function resolve(dir) {
    return path.join(__dirname, dir);
}

module.exports = {

    configureWebpack: {
        resolve: {
            alias: {
                "@": resolve("src"),
            },
        },
    },

    chainWebpack(config) {
        config.plugins.delete("preload");
        config.plugins.delete("prefetch");

        // set svg-sprite-loader
        config.module.rule("svg").exclude.add(resolve("src/assets/icons")).end();
        config.module
            .rule("icons")
            .test(/\.svg$/)
            .include.add(resolve("src/assets/icons"))
            .end()
            .use("svg-sprite-loader")
            .loader("svg-sprite-loader")
            .options({
                symbolId: "icon-[name]",
            })
            .end();

        // 生产环境 config.when判断  process.env.NODE_ENV拿到当前编译模式
        config.when(process.env.NODE_ENV === "production", (config) => {
            config.entry("app").clear().add("./src/main-prod.js");
            // 忽略打包的第三方库
            /**
             * externals 对象属性解析：
             * '包名' : '在项目中引入的名字'
             * 以element-ui举例 我再main.js里是以
             * import ElementUI from 'element-ui'
             */

            config.set("externals", {
                vue: "Vue",
                vuex: "Vuex",
                "element-ui": "ELEMENT",
                lodash: "_",
            });

            // 修改固定配置项
            config.plugin("html").tap((args) => {
                // 自定义字段
                args[0].isProd = true;
                return args;
            });
        });

        // 开发环境
        config.when(process.env.NODE_ENV === "development", (config) => {
            config.entry("app").clear().add("./src/main-dev.js");

            // 修改固定配置项
            config.plugin("html").tap((args) => {
                // 自定义字段
                args[0].isProd = false;
                return args;
            });
        });

    },
};

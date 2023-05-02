import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus';
import 'element-plus/theme-chalk/index.css';
import locale from 'element-plus/lib/locale/lang/zh-cn'
import * as Icons from "@element-plus/icons-vue";

const app = createApp(App);



// 注册全局组件 icon图标
Object.keys(Icons).forEach((key) => {
    app.component(key, Icons[key]);
});


app.use(store).use(router).use(ElementPlus, { locale })
app.mount('#app');

app.config.productionTip = false
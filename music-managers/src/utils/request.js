import axios from "axios";
import {tansParams} from "@/utils/myUtil";
import cache from "@/utils/cache";
//import qs from 'qs' //字符串解析和字符串化
// import store from '@/store/index'  //处理登录验证token等会用到
const service = axios.create({
    baseURL: '/api',
    timeout: 5000,
    //设定的请求头
    headers: { 'content-type': 'application/x-www-form-urlencoded;charset=UTF-8'}
})
// request interceptor 请求拦截
service.interceptors.request.use(config => {
    // get请求映射params参数
    if (config.method === 'get' && config.params) {debugger
        let url = config.url + '?' + tansParams(config.params);
        url = url.slice(0, -1);
        config.params = {};
        config.url = url;
    }
    if (config.method === 'post' || config.method === 'put') {
        const requestObj = {
            url: config.url,
            data: typeof config.data === 'object' ? JSON.stringify(config.data) : config.data,
            time: new Date().getTime()
        }
        const sessionObj = cache.session.getJSON('sessionObj')
        if (sessionObj === undefined || sessionObj === null || sessionObj === '') {
            cache.session.setJSON('sessionObj', requestObj)
        } else {
            const s_url = sessionObj.url;                  // 请求地址
            const s_data = sessionObj.data;                // 请求数据
            const s_time = sessionObj.time;                // 请求时间
            const interval = 1000;                         // 间隔时间(ms)，小于此时间视为重复提交
            if (s_data === requestObj.data && requestObj.time - s_time < interval && s_url === requestObj.url) {
                const message = '数据正在处理，请勿重复提交';
                console.warn(`[${s_url}]: ` + message)
                return Promise.reject(new Error(message))
            } else {
                cache.session.setJSON('sessionObj', requestObj)
            }
        }
    }
    return config
}, error => {
    console.log(error)
    Promise.reject(error)
})


// response interceptor 响应拦截
service.interceptors.response.use(
    (response) => {
        const res = response.data;
        if (response.status === 200) {
            return res;
        }
        return response;
    },

    (error) => {
        //请求失败配置401 404 500 等配置
        return Promise.reject(error.response);
    }
);

export default service
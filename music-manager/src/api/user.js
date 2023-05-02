import request from "@/utils/request";


export function user() {
    return request({
        url: "/user/test",
        method: "get",

    })
}

export function getUserList() {
    return request({
        url: "/user/getUserList",
        method: "get",
    })
}


export function updateUser(data) {
    return request({
        url: '/user/updateUserById',
        method: 'put',
        data: data
    })
}

//密码重置
export function resetPWD(data) {
    return request({
        url: '/user/restPWD',
        method: 'get',
        params: data, //post请求对应data:data   而get请求对应得是params:data
    })
}
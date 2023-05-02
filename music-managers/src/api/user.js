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



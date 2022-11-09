import request from "@/utils/request";

//获取角色数据
export function getRoleList(params) {
    return request({
        url: `/role/list?pageNum=${params.pageNum}&pageSize=${params.pageSize}&keywords=${params.keywords}`,
        method: 'get',
    })
}

//新增角色
export function addRole(data) {
    return request({
        url: '/role',
        method: 'post',
        data: data
    })
}

//根据id查询单个角色
export function getRoleBy(id) {
    return request({
        url: '/role',
        method: 'get',
        params: id
    })
}

//修改角色
export function updateRole(data) {
    return request({
        url: '/role',
        method: 'put',
        data: data
    })
}

//删除角色
export function deleteRole(roleId){
    return request({
        url: `/role/${roleId}`,
        method: 'delete',
    })
}

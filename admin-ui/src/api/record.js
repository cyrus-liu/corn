import request from '@/utils/request'


//获取记录列表
export function getRecordDataList(params) {
    return request({
        url: `/record/sys/list?pageNum=${params.pageNum}&pageSize=${params.pageSize}
        &keywords=${params.keywords}&status=${params.status}`,
        method: 'get',
    })
}

//修改取样记录
export function updateRecord(data) {
    return request({
        url: '/record',
        method: 'put',
        data: data
    })
}

//删除取样记录
export function deleteRecord(recordId){
    return request({
        url: `/record/${recordId}`,
        method: 'delete',
    })
}
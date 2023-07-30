import request from '@/utils/request'

// 查询好友分组列表
export function listTag(query) {
  return request({
    url: '/opq/tag/list',
    method: 'get',
    params: query
  })
}

// 查询好友分组详细
export function getTag(tagId) {
  return request({
    url: '/opq/tag/' + tagId,
    method: 'get'
  })
}

// 新增好友分组
export function addTag(data) {
  return request({
    url: '/opq/tag',
    method: 'post',
    data: data
  })
}

// 修改好友分组
export function updateTag(data) {
  return request({
    url: '/opq/tag',
    method: 'put',
    data: data
  })
}

// 删除好友分组
export function delTag(tagId) {
  return request({
    url: '/opq/tag/' + tagId,
    method: 'delete'
  })
}

//强制刷新
export function refresh(){
  return request({
    url: 'opq/tag/refresh',
    method: 'get'
  })
}

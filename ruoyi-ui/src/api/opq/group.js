import request from '@/utils/request'

//强制刷新群组列表
export function refreshGroup(){
  return request({
    url: '/opq/group/refresh',
    method: 'get'
  })
}

// 查询群组列表
export function listGroup(query) {
  return request({
    url: '/opq/group/list',
    method: 'get',
    params: query
  })
}

//查询全部群组
export function listAllGroup(query){
  return request({
    url: '/opq/group/allList',
    method: 'get',
    params: query
  })
}

// 查询群组详细
export function getGroup(groupCode) {
  return request({
    url: '/opq/group/' + groupCode,
    method: 'get'
  })
}

// 新增群组
export function addGroup(data) {
  return request({
    url: '/opq/group',
    method: 'post',
    data: data
  })
}

// 修改群组
export function updateGroup(data) {
  return request({
    url: '/opq/group',
    method: 'put',
    data: data
  })
}

// 删除群组
export function delGroup(groupCode) {
  return request({
    url: '/opq/group/' + groupCode,
    method: 'delete'
  })
}

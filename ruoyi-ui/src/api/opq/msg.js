import request from '@/utils/request'

// 查询群组列表
export function listGroupMsg(query) {
  return request({
    url: '/opq/msg/list',
    method: 'get',
    params: query
  })
}

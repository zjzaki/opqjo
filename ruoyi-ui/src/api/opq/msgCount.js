import request from "@/utils/request";

export function getGroupMsgCount(){
  return request({
    url: '/opq/msg/group',
    method: 'get'
  })
}

//查询消息统计历史数据
export function getGroupMsgCountList(query){
  return request({
    url: '/opq/msg/group/list',
    method: 'get',
    params: query
  })
}

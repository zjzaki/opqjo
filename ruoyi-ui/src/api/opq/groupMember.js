import request from "@/utils/request";

//查询群聊列表
export function listMember(query){
  return request({
    url: '/opq/member/list',
    method: 'get',
    params: query
  })
}

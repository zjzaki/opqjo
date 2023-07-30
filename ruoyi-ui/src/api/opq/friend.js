import request from "@/utils/request";

//查询好友列表
export function listFriend(query){
  return request({
    url: '/opq/friend/list',
    method: 'get',
    params: query
  })
}

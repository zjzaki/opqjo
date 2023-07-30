import request from "@/utils/request";

//发送好友文本消息
export function getStatisticData() {

  return request({
    url: '/opq/statistic/data',
    method: 'get'
  })
}

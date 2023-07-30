import request from "@/utils/request";

//查询插件列表
export function listPlugin(){
  return request({
    url: '/opq/plugin/list',
    method: 'get'
  })
}

//刷新插件
export function refreshPlugin(){
  return request({
    url: '/opq/plugin/refresh',
    method: 'get'
  })
}

//查询群聊插件列表
export function listGroupPlugin(groupCode){
  const data = {
    groupCode
  }
  return request({
    url: '/opq/plugin/groupPluginList',
    method: 'get',
    params: data
  })
}

// 新增群聊插件
export function addGroupPlugin(data) {
  return request({
    url: '/opq/plugin',
    method: 'post',
    data: data
  })
}

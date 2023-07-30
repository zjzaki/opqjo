import request from "@/utils/request";

//发送好友文本消息
export function sendFriendText(toUin, content) {
  const data = {
    toUin,
    content
  }
  return request({
    url: '/opq/action/sendFriendText',
    method: 'post',
    data: data
  })
}

//发送群组文本消息
export function sendGroupText(toUin, content, atUinLists = []) {
  const data = {
    toUin,
    content,
    atUinLists
  }
  return request({
    url: '/opq/action/sendGroupText',
    method: 'post',
    data: data
  })
}

//发送群组图片消息,暂时只做了发一张
export function sendGroupPic(toUin, content, base64Buf ,atUinLists = []) {
  const data = {
    toUin,
    content,
    base64Buf,
    atUinLists
  }
  return request({
    url: '/opq/action/sendGroupPic',
    method: 'post',
    data: data
  })
}

export function banGroupMember(uin, uid, banTime) {
  const data = {
    uin,
    uid,
    banTime
  }
  return request({
    url: '/opq/action/banTimeGroupMember',
    method: 'post',
    data: data
  })
}



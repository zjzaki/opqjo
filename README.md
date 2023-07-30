<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">opqjo v1.0.0</h1>
<h4 align="center">基于RuoYi-Vue的OPQBot SDK</h4>

# 食用方法
## 1.建库建表
数据库建表语句在sql目录
## 2.修改opqj-core的resources中的bot.properties
此处指的是opqbot的相关配置
opqbot搭建指南: https://73s2swxb4k.apifox.cn/api-72056215
```properties
ip=xxxxxxx
port=xxxxx
botId=xxxxxxxx
```
## 3.修改application-druid.yml以及application.yml中的配置
参考若依官方文档 http://doc.ruoyi.vip
PS: 主要修改数据库连接也就是application-druid.yml
# 软件工程后端

### github仓库地址
- [https://github.com/vvvb-github/Booking-Server](https://github.com/vvvb-github/Booking-Server)

### 快速上手
- 克隆项目`git clone https://github.com/vvvb-github/Booking-Server.git`
- IDEA安装插件，`File/Settings/Plugins`搜索`Lombok`并安装
- 首次打开项目maven自动下载jar包较慢，请耐心等待，直至IDEA底部下载条完成

### 开发配置
- [JDK 1.8.2](https://pan.baidu.com/s/1PAC97tvdNvJaqDai_jgCOQ) 提取码：***ftys***

### 参考资源
- [postman](https://www.postman.com/) http请求模拟，可在线使用，也可免费注册下载
- [Navicat](https://www.yudouyudou.com/ziyuanxiazai/gongjuchajian/1474.html) 可视化数据库连接工具，破解版博客教程
- [mybatis-plus](https://baomidou.com/guide/) 请重点阅读 ***核心功能/条件构造器*** 章节，了解使用构造器进行数据库操作（我不想见到丑陋的SQL语句）
- [Tonguetip](https://github.com/vvvb-github/TongueTip-Server) 短学期项目后端，可参考图片传输、各类数据库操作相关代码
- [IDEA Git](https://www.cnblogs.com/javabg/p/8567790.html) IDEA的git操作指南

### 注意事项
- 代码生成器的使用请参考mybatis-plus官方文档 ***核心功能/代码生成器*** 章节，在`utils/CodeGenerator`中已经做好配置，只需运行即可；请注意，不同模块不可以包含同一张数据库表，因此请务必合理分配并创建你的模块包
- 每次编写代码前，请及时进行进行Pull操作；每次编写代码完毕后，也请及时进行进行Push操作，确保项目及时更新
- 每次对代码进行修改更新时，请以天为单位在`README`的日志部分记录，便于项目的维护

### 日志记录
- **2020.11.6** 创建项目
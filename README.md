# XDTIC-Project-Pool

## 西电腾讯俱乐部项目池

### 功能

* 发布自己的 “idea”（项目）
* 参与感兴趣的 “idea”（项目）

### 构建

1. 将 *fis* 文件夹复制到 `${HOME}/.m2/com/baidu` 目录下
2. 在 MySQL 中导入 *project_pool.sql*
3. 设置 MySQL 的 **root** 用户的密码为 **123456**，或者通过修改 `src/main/resources/jdbc.properties` 来改变用户名和密码
4. 使用 Maven 构建项目

### 运行

* 首先进入登录页面，因为尚无用户所以需要先注册用户，然后登录
* 用户发表的项目，管理员未审核通过之前，只对自己可见，审核之后项目会被发布到项目大厅
* 管理员的 url 是 `${host}/xdtic/admin`，默认内置的管理员的用户名和密码都是 xdtic

------

作者：

前端
* [garfileds](https://github.com/garfileds) : [tic-wechat](https://github.com/garfileds/tic-wechat)

后端
* [mizhoux](https://github.com/mizhoux)
* [wenjings](https://github.com/wenjings)

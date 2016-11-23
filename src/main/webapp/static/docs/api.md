### 项目大厅

### 个人中心

#### 登录页面

- url： /xdtic/user/login
- jsp: /page/user/login.jsp

- api: /xdtic/fn/valid/user `[POST]`

   - 说明：异步验证用户名和密码

   - request
    ```
    {
        username: "xxx",
        password: "xxx"
    }
    ```
    
    - response `[JSON]`
    ```
    {
        code: "ok"/"error"
    }
    ```


- api: /xdtic/fn/user/login

    - 登录表单提交，api验证并跳转页面至用户中心（url: /user）
    
    -request `[POST]`
    ```
    {
        username: "xxx",
        password: "xxx"
    }
    ```

#### 注册页面

   - url: /xdtic/user/register
   - jsp: /page/user/register.jsp
   
   - api: /xdtic/fn/valid/userName `[POST]`
       - request
       ```
       {
         username: "xxx"
       }
       ```
       - response `[JSON]`
       ```
       {
         code: "ok"/"error"
       }
       ```

   - api: /xdtic/fn/user/register

    - 注册表单提交，api验证并跳转页面至登录页面（url: /xdtic/user）
    
        - request `[POST]`
        ```
        {
            username: "xxx",
            password: "xxx",
            passConfirm: "xxx"
        }
        ```

#### 用户中心页面

   - url: /xdtic/user
   - jsp: /page/user/center
   - jsp页面插值所需变量
   ```
   {
    	"user": {
    		"id": "u001",
    		"username": "adoug",
    		"hasMsg": true/false
    	}
    }
   ```

#### 个人信息页面

   - url: /xdtic/user/profile
   - jsp: /page/user/profile
   - jsp页面所需变量
   ```
   {
    	"user": {
    		"id": "u001",
    		"username": "adoug",
    		"realName": "张骥",
			"sex": "boy"/"girl",
			"major": "软件工程",
			"phone": "15029679086",
			"studentNo": "1603121451",
			"ability": "ps,c++,ui设计",
			"projectDesc": "人脸识别系统"
    	}
    }
   ```
   - api: /xdtic/fn/update/profile `[POST]`
   - 说明：用户修改个人信息
   - request `[FORM]`
   ```
   {
        "user": {
    		"id": "u001",
    		"username": "adoug",
    		"email": "9424348692@qq.com",
    		"realName": "张骥",
			"sex": "boy"/"girl",
			"major": "软件工程",
			"phone": "15029679086",
			"studentNo": "1603121451",
			"ability": "ps,c++,ui设计",
			"projectDesc": "人脸识别系统"
    	}
   }
   ```
   
   - response `[JSON]`
   ```
   {
    code: "ok"/"error"
   }
   ```
   
#### 系统消息页面

   - url：/xdtic/user/msgs
   - jsp: /page/user/msgs
   
   - api: /xdtic/fn/get/msg
   - 说明：获取系统消息列表
   - request `[GET]`
   ```
   {
        page: 1/2/3...
        size: 8
   }
   ```
   - response `[JSON]`
   ```
   {
        "pageNum": 1,  /*表示第几页*/
    	"size": 8,
    	"hasMore": true, /*后面是否还有消息*/
    	
        "msgs": [{
		"type": "join"/"post"/"pass", /*分三种类型*/
		"projectName": "时间典当铺APP",
		"projectCreator": "wali",
		"date": "10分钟前",
		"id": "m001"
	}, {
		"type": "post",
		"projectName": "狗儿养成计划",
		"projectCreator": "adoug",
		"date": "1天前",
		"id": "m001"
	}, {
		"type": "pass",
		"projectName": "狗儿养成计划",
		"projectCreator": "adoug",
		"date": "1小时前",
		"id": "m001"
	}, {
		"type": "join",
		"projectName": "时间典当铺APP",
		"projectCreator": "adoug",
		"date": "10分钟前",
		"id": "m001"
	}, {
		"type": "join",
		"projectName": "时间典当铺APP",
		"projectCreator": "adoug",
		"date": "10分钟前",
		"id": "m001"
	}, {
		"type": "join",
		"projectName": "时间典当铺APP",
		"projectCreator": "adoug",
		"date": "10分钟前",
		"id": "m001"
	}, {
		"type": "join",
		"projectName": "时间典当铺APP",
		"projectCreator": "adoug",
		"date": "10分钟前",
		"id": "m001"
	}, {
		"type": "join",
		"projectName": "时间典当铺APP",
		"projectCreator": "adoug",
		"date": "10分钟前",
		"id": "m001"
	}]
   }
   ```

#### 修改密码页面

- url： /xdtic/user/resetPass
- jsp: /page/user/resetPass.jsp

- api: /xdtic/fn/user/resetPass

    - 表单提交，api验证并跳转页面至登录页面（url: /user/login）
    
    - request `[POST]`
    ```
    {
        username: "xxx",
        passOld: "xxx",
        passNew: 'xxx',
        passNewConfirm: 'xxx'
    }
    ```
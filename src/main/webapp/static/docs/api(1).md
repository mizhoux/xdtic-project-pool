### 项目大厅

#### 项目大厅页面

- url: /xdtic/hall
- jsp: /page/hall/hall.jsp

- api: /xdtic/get/project `[GET]`

   - 说明：获取项目
   - request
   ```
   {
        userid: 'u001',
        pageNum: 1,
        pageSize: 8,
        keyWords: 'web 情感'
   }
   ```
   - response `[JSON]`
   ```
   {
	"pageSize": 8,
	"pageNum": 1,
	"hasMore": true,
	"projects": [{
		"proId": "p001",
		"proname": "SSR召唤符画法项目",
		"isCollected": true,
		"username": "adoug",
		"date": "2016.02.05",
		"tags": ["Web", "情感"],
		"desc": "一万次悲伤/依然会有意义/我一直在最温暖的地方等你/似乎只能这样/停留一个方向/已不能改变"
	}, {
		"proId": "p002",
		"proname": "tic项目池",
		"isCollected": false,
		"username": "adoug",
		"date": "2016.02.05",
		"tags": ["Web", "情感"],
		"desc": "我祈祷拥有一颗透明的心灵/和会流泪的眼睛/给我再去相信的勇气/oh越过谎言去拥抱你"
	}, {
		"proId": "p003",
		"proname": "时间典当铺APP",
		"isCollected": false,
		"username": "adoug",
		"date": "2016.02.05",
		"tags": ["Web", "情感"],
		"desc": "我祈祷拥有一颗透明的心灵/和会流泪的眼睛/给我再去相信的勇气/oh越过谎言去拥抱你"
	}, {
		"proId": "p004",
		"proname": "时间典当铺APP",
		"isCollected": false,
		"username": "adoug",
		"date": "2016.02.05",
		"tags": ["Web", "情感"],
		"desc": "我祈祷拥有一颗透明的心灵/和会流泪的眼睛/给我再去相信的勇气/oh越过谎言去拥抱你"
	}, {
		"proId": "p005",
		"proname": "时间典当铺APP",
		"isCollected": false,
		"username": "adoug",
		"date": "2016.02.05",
		"tags": ["Web", "情感"],
		"desc": "我祈祷拥有一颗透明的心灵/和会流泪的眼睛/给我再去相信的勇气/oh越过谎言去拥抱你"
	}, {
		"proId": "p006",
		"proname": "时间典当铺APP",
		"isCollected": false,
		"username": "adoug",
		"date": "2016.02.05",
		"tags": ["Web", "情感"],
		"desc": "我祈祷拥有一颗透明的心灵/和会流泪的眼睛/给我再去相信的勇气/oh越过谎言去拥抱你"
	}, {
		"proId": "p007",
		"proname": "时间典当铺APP",
		"isCollected": false,
		"username": "adoug",
		"date": "2016.02.05",
		"tags": ["Web", "情感"],
		"desc": "我祈祷拥有一颗透明的心灵/和会流泪的眼睛/给我再去相信的勇气/oh越过谎言去拥抱你"
	}, {
		"proId": "p008",
		"proname": "时间典当铺APP",
		"isCollected": false,
		"username": "adoug",
		"date": "2016.02.05",
		"tags": ["Web", "情感"],
		"desc": "我祈祷拥有一颗透明的心灵/和会流泪的眼睛/给我再去相信的勇气/oh越过谎言去拥抱你"
	}]
    }
   ```
   
- api: /xdtic/fn/get/hotProject `[GET]`
   - 说明：根据keywords获取热门项目
   - request
   ```
        {
            userid: 'u001',
            hotSize: 2,
            keyWords: 'Web 情感'
        }
   ```
   - response `[JSON]`
   ```
   {
	"hotSize": 2,
	"projects": [{
		"proId": "p001",
		"proname": "SSR召唤符画法",
		"isCollected": true,
		"username": "adoug",
		"date": "2016.02.05",
		"tags": ["Web", "情感"],
		"desc": "一万次悲伤/依然会有意义/我一直在最温暖的地方等你/似乎只能这样/停留一个方向/已不能改变"
	}, {
		"proId": "p002",
		"proname": "tic项目池",
		"isCollected": false,
		"username": "adoug",
		"date": "2016.02.05",
		"tags": ["Web", "情感"],
		"desc": "我祈祷拥有一颗透明的心灵/和会流泪的眼睛/给我再去相信的勇气/oh越过谎言去拥抱你"
	}]
    }
   ```

- api: /xdtic/fn/project/collect `[GET]`
   - 说明：收藏项目
   - request
   ```
   {
        userid: 'u001',
        proId: 'p001'
   }
   ```
   - response `[JSON]`
   ```
        {
            code: 'ok'/'error'
        }
   ```

- api: /xdtic/fn/project/uncollect `[GET]`
   - 说明：取消收藏项目
   - request
   ```
   {
        userid: 'u001',
        proId: 'p001'
   }
   ```
   - response `[JSON]`
   ```
        {
            code: 'ok'/'error'
        }
   ```
   
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
    		"email": "942434869@qq.com",
    		"name": "张骥",
  			"sex": "boy"/"girl",
  			"profe": "软件工程",
  			"phone": "15029679086",
  			"stunum": "1603121451",
  			"profile": "ps,c++,ui设计",
  			"pexperice": "人脸识别系统"
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
      	"email": "942434869@qq.com",
      	"name": "张骥",
  			"sex": "boy"/"girl",
  			"profe": "软件工程",
  			"phone": "15029679086",
  			"stunum": "1603121451",
  			"profile": "ps,c++,ui设计",
  			"pexperice": "人脸识别系统"
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
		"proname": "时间典当铺APP",
		"username": "wali",
		"existDate": "10分钟前",
		"mid": "m001"
	}, {
		"type": "post",
		"proname": "狗儿养成计划",
		"username": "adoug",
		"existDate": "1天前",
		"mid": "m001"
	}, {
		"type": "pass",
		"proname": "狗儿养成计划",
		"username": "adoug",
		"existDate": "1小时前",
		"id": "m001"
	}, {
		"type": "join",
		"proname": "时间典当铺APP",
		"username": "adoug",
		"existDate": "10分钟前",
		"mid": "m001"
	}, {
		"type": "join",
		"proname": "时间典当铺APP",
		"username": "adoug",
		"date": "10分钟前",
		"mid": "m001"
	}, {
		"type": "join",
		"proname": "时间典当铺APP",
		"username": "adoug",
		"existDate": "10分钟前",
		"mid": "m001"
	}, {
		"type": "join",
		"proname": "时间典当铺APP",
		"username": "adoug",
		"existDate": "10分钟前",
		"mid": "m001"
	}, {
		"type": "join",
		"proname": "时间典当铺APP",
		"username": "adoug",
		"existDate": "10分钟前",
		"mid": "m001"
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



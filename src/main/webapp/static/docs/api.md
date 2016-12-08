
### 我的项目

#### 发布项目页面

- url: /xdtic/myProject/postProject
- jsp: /page/myProject/postProject
- jsp页面所需变量
   ```
   {
        "user": {
            "id": "u001"
        }
   }
   ```
- api: /xdtic/fn/project/post

   - 说明：发布新项目
   - request `[POST]` `[Form]`
   ```
   {
        tag: "安卓&硬件"
        title: "我的天"
        pdesc: 记录生活中的新鲜事
        prowant: "前端 2名"
        concat: "电话：15029679086",
        uid: "u001"
   }
   ```
   - response  `[JSON]`
   ```
   {
        code: "ok"/"error"
   }
   ```

#### 我的发布页面
- url: /xdtic/myProject?type=post
- jsp: /page/myProject/myProject.jsp

- jsp页面所需变量
   ```
   {
        "user": {
            "id": "u001",
            "name": "adoug"
        }
   }
   ```

- api: /xdtic/fn/get/project/myPost `[GET]`

   - 说明：获取我的发布的项目
   - request
   ```
   {
        uid: "u001",
        pageNum: 0,
        pageSize: 5
   }
   ```
   - response `[JSON]`
   ```
   {
    	"pageSize": 2,
    	"pageNum": 0,
    	"hasMore": false,
    	"projects": [{
    	    "statu": "checking"/"pass"  /*两种*/
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
    		"desc": "我祈祷拥有一颗透明的心灵/和会流泪的眼睛/给我再去相信的勇气/oh越过谎言去拥抱你",
    		"statu": "pass"
    	}]
    }
   ```

#### 我的发布-项目详情页
- url: /xdtic/myProject/myPost/detail?proId=p001
- jsp: /page/myProject/myPost/detail

- jsp页面所需变量
   ```
   {
    	"project": {
    		"proname": "时间典当铺",
    		"tag": ["Web", "情感"],
    		"isCollected": true,
    		"proId": "p001",
    		"statu": "pass",
    		"prowant": "产品经理：2名  UI设计：1名 前端：1名",
    		"promassage": "这里就是项目的详情了，文字就不限量了，感觉应该加上可以添加图片的功能，这里的文字颜
            色是#222222，字号15。",
            "concat": "邮箱：yuqingyaa@163.com 如有疑问，敬请用以上方式咨询~",
            "date": "2016.01.28"
    	},
    	
    	"projectCreator": {
    		"id": "u001",
    		"username": "adoug"
    	},
    
    	"user": {
    		"id": "u002"
    	}
    }
   ```
   
#### 编辑已发布项目页面
- url: /xdtic/myProject/myPost/editDetail?proId=p001
- jsp: /page/myProject/myPost/editDetail

- jsp页面所需变量：
   ```
   {
    	"project": {
    		"proname": "时间典当铺",
    		"tag": ["Web", "情感"],
    		"proId": "p001",
    		"prowant": "产品经理：2名  UI设计：1名  前端：1名",
    		"promassage":"天空之城在哭泣。",
            "concat":"邮箱：yuqingya@163.com 如有疑问，敬请用以上方式咨询~",
            "date": "2016.01.28"
    	},
    
    	"user": {
    		"id": "u001",
    		"username": "adoug"
    	}
    }
   ```

- api: /xdtic/fn/project/update
- request `[POST]` `[Form]`
   ```
   {
        promassage:	            
        "港岛妹妹，我送给你的西班牙馅饼。"
    							        
        prowant: 
        "产品经理：2名  UI设计：1名  前端：1名"
    						            
        concat:	
        "邮箱：yuqingyaa@163.com   如有疑问，敬请用以上方式咨询~"
    						            
        uid:u001
        proId:p001
   }
   ```

#### 报名信息汇总页面
- url: /xdtic/myProject/myPost/signInfo?proId=p001
- jsp: /page/myProject/myPost/signInfo

- jsp页面所需变量
   ```
   {
        "signInfos": [{
    		"sid": "s001",
    		"username": "小草",
    		"career": "UI设计师",
    		"date": "2015.12.31",
    		"time": "19:00"
    	}, {
    		"sid": "s002",
    		"username": "脸狐",
    		"career": "前端工程师",
    		"date": "2015.12.31",
    		"time": "21:00"
    	}],
    
    	"project": {
    		"proname": "时间典当铺",
    		"proId": "p001"
    	}
   }
   ```

#### 报名信息详情页面
- url: /xdtic/signInfo?sid=s001
- jsp: /page/myProject/myPost/signDetail

- jsp页面所需变量
```
{
    "user": {
		"id": "u001",
		"username": "adoug",
		"email": "942434869@qq.com",
		"name": "张骥",
		"sex": "boy",
		"profe": "软件工程",
		"phone": "15029679086",
		"stunum": "1603121451",
		"profile": "ps,c++,ui设计",
		"pexperice": "人脸识别系统"
	},

    "signInfo": {
        "sid": "s001",
        "username": "小草",
        "career": "UI设计师",
        "date": "2015.12.31",
        "time": "19:00"
    },

    "project": {
        "proname": "时间典当铺",
        "proId": "p001"
    }
}
```

#### 我的收藏页面
- url: /xdtic/myProject?type=collect
- jsp: /page/myProject/myProject.jsp

- jsp页面所需变量
   ```
       {
            "user": {
                "id": "u001",
                "name": "adoug"
            }
       }
   ```

- api: /xdtic/fn/get/project/myCollect `[GET]`

   - 说明：获取我的收藏的项目
   - request
   ```
       {
            uid: "u001",
            pageNum: 0,
            pageSize: 5
       }
   ```
   - response `[JSON]`
   ```
       {
        	"pageSize": 2,
        	"pageNum": 0,
        	"hasMore": false,
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
        	}]
    }
   ```

#### 项目详情页-我的收藏 & 项目详情页-我的参与 & 项目大厅点击进入的项目详情页

- url：/xdtic/project?proId=p001&uid=u001

- jsp：/page/myProject/myCollect/detail.jsp
- jsp页面所需变量：
   ```
   {
        "userIsJoined": false,
    	"project": {
    		"proname": "时间典当铺",
    		"tag": ["Web", "情感"],
    		"isCollected": true,
    		"proId": "p001",
    		"concat":         
            "邮箱：yuqingyaa@163.com 如有疑问，敬请用以上方式咨询~",
    		"prowant": "产品经理：2名 UI设计：1名 前端：1名",
    		"promassage": 
    		"这里就是项目的详情了，文字就不限	量了，感觉应该加上可以添加图片的功能，这里的文字颜色是#222222，字号15。",
            "date": "2016.01.28"
    	},
    
    	"projectCreator": {
    		"id": "u001",
    		"username": "adoug"
    	},
    
    	"user": {
    		"id": "u002"
    	}
    }
   ```

#### 我要报名页面
- url：/xdtic/project/toJoin?proId=p001&uid=u001
- jsp：/page/myProject/myCollect/toJoin.jsp

- jsp页面所需变量
   ```
   {
    	"project": {
    		"proname": "时间典当铺",
    		"tag": ["Web", "情感"],
    		"isCollected": true,
    		"proId": "p001",
            "date": "2016.01.28"
    	},
    
        "user": {
        	"id": "u001",
        	"username": "adoug",
        	"email": "942434869@qq.com",
        	"name": "张骥",
    		"sex": "boy",
    		"profe": "软件工程",
    		"phone": "15029679086",
    		"stunum": "1603121451",
    		"profile": "ps,c++,ui设计",
    		"pexperice": "人脸识别系统"
        }
    }
   ```

- api: /xdtic/fn/project/toJoin

   - request `[POST]` `[Form]`
   ```
   {
        name:张骥
        sex:boy
        apply:UI设计师
        profe:软件工程
        phone:15029679086
        stunum:1603121451
        profile:ps,c++,ui设计
        pexperice:人脸识别系统
        proId:p001
        uid:u001
   }
   ```
   - response `[JSON]`
   ```
    {
        code: 'ok'/'error'
    }
   ```

#### 我的参与页面
- url: /xdtic/myProject?type=join
- jsp: /page/myProject/myProject.jsp

- jsp页面所需变量
   ```
   {
        "user": {
            "id": "u001",
            "name": "adoug"
        }
   }
   ```

- api: /xdtic/fn/get/project/myJoin `[GET]`

   - 说明：获取我的参与的项目
   - request
   ```
   {
        uid: "u001",
        pageNum: 0,
        pageSize: 5
   }
   ```
   
   - response `[JSON]`
   ```
       {
        	"pageSize": 2,
        	"pageNum": 0,
        	"hasMore": false,
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
        	}]
    }
   ```


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
        pageNum: 0,
        pageSize: 5,
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
	}}]
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
   
   - jsp页面所需变量
   ```
   {
        "user": {
            "id": "u001"
        }
   }
   ```
   
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
    	"hasMore": false, /*后面是否还有消息*/
    	
        "msgs": [{
		"type": "join"/"post"/"pass", /*分三种类型*/
		"uid": 1,
		"mid": 1,
		"massage": "你已成功报名BU Qun的5分钟撩妹app，请等待发布者的联系，祝你顺利~",
		"existDate": "10分钟前"
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



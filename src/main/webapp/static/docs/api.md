
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
        name: "我的天"
        content: 记录生活中的新鲜事
        recruit: "前端 2名"
        contact: "电话：15029679086",
        userId: "u001"
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
            "username": "adoug"
        }
   }
   ```

- api: /xdtic/fn/get/project/myPost `[GET]`

   - 说明：获取我的发布的项目
   - request
   ```
   {
        userId: "u001",
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
    	    "status": 0/1/2,  /*分别对应审核中/审核通过/审核被拒*/
    		"id": "p001",
    		"name": "SSR召唤符画法项目",
    		"isCollected": true,
    		"username": "adoug",
    		"creationDate": "2016.02.05",
    		"tag": ["Web", "情感"],
    		"content": "一万次悲伤/依然会有意义/我一直在最温暖的地方等你/似乎只能这样/停留一个方向/已不能改变"
    	}, {
    		"id": "p002",
    		"name": "tic项目池",
    		"isCollected": false,
    		"username": "adoug",
    		"creationDate": "2016.02.05",
    		"tag": ["Web", "情感"],
    		"content": "我祈祷拥有一颗透明的心灵/和会流泪的眼睛/给我再去相信的勇气/oh越过谎言去拥抱你",
    		"status": 1
    	}]
    }
   ```

- api: /xdtic/fn/user/project/operate

    - 说明：项目发起者对项目的操作
    - request `[POST]` `[JSON]`
    ```
    {
        operation: 'delete',
        proId: 'p001'
    }
    ```
    - response `[JSON]`
    ```
    {
        code: 'ok'/'error'
    }
    ```

#### 我的发布-项目详情页
- url: /xdtic/myProject/myPost/detail?proId=p001
- jsp: /page/myProject/myPost/detail

- jsp页面所需变量
   ```
   {
    	"project": {
    		"name": "时间典当铺",
    		"tag": ["Web", "情感"],
    		"isCollected": true,
    		"id": "p001",
    		"status": 1,
    		"recruit": "产品经理：2名  UI设计：1名 前端：1名",
    		"content": "这里就是项目的详情了，文字就不限量了，感觉应该加上可以添加图片的功能，这里的文字颜
            色是#222222，字号15。",
            "contact": "邮箱：yuqingyaa@163.com 如有疑问，敬请用以上方式咨询~",
            "creationDate": "2016.01.28"
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
    		"name": "时间典当铺",
    		"tag": ["Web", "情感"],
    		"id": "p001",
    		"recruit": "产品经理：2名  UI设计：1名  前端：1名",
    		"content":"天空之城在哭泣。",
            "contact":"邮箱：yuqingya@163.com 如有疑问，敬请用以上方式咨询~",
            "creationDate": "2016.01.28"
        "status": 0/1/2
    	},
    
    	"user": {
    		"id": "u001",
    		"username": "adoug"
    	}
    }
   ```

- api: /xdtic/fn/project/upcreationDate
- request `[POST]` `[Form]`
   ```
   {
        content:	            
        "港岛妹妹，我送给你的西班牙馅饼。"
    							        
        recruit: 
        "产品经理：2名  UI设计：1名  前端：1名"
    						            
        contact:	
        "邮箱：yuqingyaa@163.com   如有疑问，敬请用以上方式咨询~"

        reject: "false/true" //是字符串格式
    						            
        userId:u001
        id:p001
   }
   ```

#### 报名信息汇总页面
- url: /xdtic/myProject/myPost/signInfo?proId=p001
- jsp: /page/myProject/myPost/signInfo

- jsp页面所需变量
   ```
   {
        "signInfos": [{
    		"id": "s001",
    		"username": "小草",
    		"apply": "UI设计师",
    		"signDate": "2015.12.31 19:00"
    	}, {
    		"id": "s002",
    		"username": "脸狐",
    		"apply": "前端工程师",
    		"signDate": "2015.12.31 19:00"
    	}],
    
    	"project": {
    		"name": "时间典当铺",
    		"id": "p001"
    	}
   }
   ```

#### 报名信息详情页面
- url: /xdtic/signInfo?sid=s001
- jsp: /page/myProject/myPost/signDetail

- jsp页面所需变量
```
{
    "signUser": {
		"id": "u001",
		"username": "adoug",
		"email": "942434869@qq.com",
		"name": "张骥",
		"gender": "boy",
		"major": "软件工程",
		"phone": "15029679086",
		"stuNum": "1603121451",
		"skill": "ps,c++,ui设计",
		"experience": "人脸识别系统"
	},

    "signInfo": {
        "id": "s001",
        "username": "小草",
        "apply": "UI设计师",
        "signDate": "2015.12.31 19:00"
    },

    "project": {
        "name": "时间典当铺",
        "id": "p001"
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
            userId: "u001",
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
        		"id": "p001",
        		"name": "SSR召唤符画法项目",
        		"isCollected": true,
        		"username": "adoug",
        		"creationDate": "2016.02.05",
        		"tag": ["Web", "情感"],
        		"content": "一万次悲伤/依然会有意义/我一直在最温暖的地方等你/似乎只能这样/停留一个方向/已不能改变"
        	}, {
        		"id": "p002",
        		"name": "tic项目池",
        		"isCollected": false,
        		"username": "adoug",
        		"creationDate": "2016.02.05",
        		"tag": ["Web", "情感"],
        		"content": "我祈祷拥有一颗透明的心灵/和会流泪的眼睛/给我再去相信的勇气/oh越过谎言去拥抱你"
        	}]
    }
   ```

#### 项目详情页-我的收藏 & 项目详情页-我的参与 & 项目大厅点击进入的项目详情页

- url：/xdtic/project?proId=p001&userId=u001

- jsp：/page/myProject/myCollect/detail.jsp
- jsp页面所需变量：
   ```
   {
        "userIsJoined": false,
    	"project": {
    		"name": "时间典当铺",
    		"tag": ["Web", "情感"],
    		"isCollected": true,
    		"id": "p001",
    		"contact":         
            "邮箱：yuqingyaa@163.com 如有疑问，敬请用以上方式咨询~",
    		"recruit": "产品经理：2名 UI设计：1名 前端：1名",
    		"content": 
    		"这里就是项目的详情了，文字就不限	量了，感觉应该加上可以添加图片的功能，这里的文字颜色是#222222，字号15。",
            "creationDate": "2016.01.28"
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
- url：/xdtic/project/toJoin?proId=p001&userId=u001
- jsp：/page/myProject/myCollect/toJoin.jsp

- jsp页面所需变量
   ```
   {
    	"project": {
    		"name": "时间典当铺",
    		"tag": ["Web", "情感"],
    		"isCollected": true,
    		"id": "p001",
            "creationDate": "2016.01.28"
    	},
    
        "user": {
        	"id": "u001",
        	"username": "adoug",
        	"email": "942434869@qq.com",
        	"name": "张骥",
    		"gender": "boy",
    		"major": "软件工程",
    		"phone": "15029679086",
    		"stuNum": "1603121451",
    		"skill": "ps,c++,ui设计",
    		"experience": "人脸识别系统"
        }
    }
   ```

- api: /xdtic/fn/project/toJoin

   - request `[POST]` `[Form]`
   ```
   {
        apply: "UI设计师"
        skill: "ps,c++,ui设计"
        experience: "人脸识别系统"
        proId: "p001"
        userId: "u001"
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
        userId: "u001",
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
        		"id": "p001",
        		"name": "SSR召唤符画法项目",
        		"isCollected": true,
        		"username": "adoug",
        		"creationDate": "2016.02.05",
        		"tag": ["Web", "情感"],
        		"content": "一万次悲伤/依然会有意义/我一直在最温暖的地方等你/似乎只能这样/停留一个方向/已不能改变"
        	}, {
        		"id": "p002",
        		"name": "tic项目池",
        		"isCollected": false,
        		"username": "adoug",
        		"creationDate": "2016.02.05",
        		"tag": ["Web", "情感"],
        		"content": "我祈祷拥有一颗透明的心灵/和会流泪的眼睛/给我再去相信的勇气/oh越过谎言去拥抱你"
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
        userId: 'u001',
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
		"id": "p001",
		"name": "SSR召唤符画法项目",
		"isCollected": true,
		"username": "adoug",
		"userId": "u001",
		"creationDate": "2016.02.05",
		"tag": ["Web", "情感"],
		"content": "一万次悲伤/依然会有意义/我一直在最温暖的地方等你/似乎只能这样/停留一个方向/已不能改变"
	}, {
		"id": "p002",
		"name": "tic项目池",
		"isCollected": false,
		"username": "adoug",
		"userId": "u001",
		"creationDate": "2016.02.05",
		"tag": ["Web", "情感"],
		"content": "我祈祷拥有一颗透明的心灵/和会流泪的眼睛/给我再去相信的勇气/oh越过谎言去拥抱你"
	}, {
		"id": "p003",
		"name": "时间典当铺APP",
		"isCollected": false,
		"username": "adoug",
		"userId": "u001",
		"creationDate": "2016.02.05",
		"tag": ["Web", "情感"],
		"content": "我祈祷拥有一颗透明的心灵/和会流泪的眼睛/给我再去相信的勇气/oh越过谎言去拥抱你"
	}, {
		"id": "p004",
		"name": "时间典当铺APP",
		"isCollected": false,
		"username": "adoug",
		"userId": "u001",
		"creationDate": "2016.02.05",
		"tag": ["Web", "情感"],
		"content": "我祈祷拥有一颗透明的心灵/和会流泪的眼睛/给我再去相信的勇气/oh越过谎言去拥抱你"
	}, {
		"id": "p005",
		"name": "时间典当铺APP",
		"isCollected": false,
		"username": "adoug",
		"userId": "u001",
		"creationDate": "2016.02.05",
		"tag": ["Web", "情感"],
		"content": "我祈祷拥有一颗透明的心灵/和会流泪的眼睛/给我再去相信的勇气/oh越过谎言去拥抱你"
	}}]
    }
   ```
   
- api: /xdtic/fn/get/hotProject `[GET]`
   - 说明：根据keywords获取热门项目
   - request
   ```
        {
            userId: 'u001',
            hotSize: 2,
            keyWords: 'Web 情感'
        }
   ```
   - response `[JSON]`
   ```
   {
	"hotSize": 2,
	"projects": [{
		"id": "p001",
		"name": "SSR召唤符画法",
		"isCollected": true,
		"username": "adoug",
		"userId": "u001",
		"creationDate": "2016.02.05",
		"tag": ["Web", "情感"],
		"content": "一万次悲伤/依然会有意义/我一直在最温暖的地方等你/似乎只能这样/停留一个方向/已不能改变"
	}, {
		"id": "p002",
		"name": "tic项目池",
		"isCollected": false,
		"username": "adoug",
		"userId": "u001",
		"creationDate": "2016.02.05",
		"tag": ["Web", "情感"],
		"content": "我祈祷拥有一颗透明的心灵/和会流泪的眼睛/给我再去相信的勇气/oh越过谎言去拥抱你"
	}]
    }
   ```

- api: /xdtic/fn/project/collect `[GET]`
   - 说明：收藏项目
   - request
   ```
   {
        userId: 'u001',
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
        userId: 'u001',
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
    		"username": "adoug"
    	}
    }
   ```

   - api: /xdtic/fn/hasMsg
     - 说明：查询用户是否有未读消息
     - request `[GET]`
     ```
     {
        userId: 'u001'
     }
     ```
     - response `[JSON]`
     ```
     {
        hasMsg: true/false
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
  			"gender": "boy"/"girl",
  			"major": "软件工程",
  			"phone": "15029679086",
  			"stuNum": "1603121451",
  			"skill": "ps,c++,ui设计",
  			"experience": "人脸识别系统"
    	}
    }
   ```
   - api: /xdtic/fn/upcreationDate/profile `[POST]`
     - 说明：用户修改个人信息
     - request `[FORM]`
     ```
     {
      "user": {
      	"id": "u001",
      	"username": "adoug",
      	"email": "942434869@qq.com",
      	"name": "张骥",
  			"gender": "boy"/"girl",
  			"major": "软件工程",
  			"phone": "15029679086",
  			"stuNum": "1603121451",
  			"skill": "ps,c++,ui设计",
  			"experience": "人脸识别系统"
      }
     }
     ```
   
     - response `[JSON]`
     ```
     {
          code: "ok"/"error"
     }
     ```
     
   - api: /xdtic/fn/valid/profile
   - 说明：验证用户资料（username、phone、email是否唯一，可为空）
     - request `[FORM]`
     ```
     {
        "id": "u001",
        "username": "adoug",
       	"email": "942434869@qq.com",
       	"name": "张骥",
 		    "gender": "boy"/"girl",
 		    "major": "软件工程",
          			"phone": "15029679086",
          			"stuNum": "1603121451",
          			"skill": "ps,c++,ui设计",
          			"experience": "人脸识别系统"
     }
     ```
     
     - response `[JSON]`
     ```
     {
        code: 'ok/error',
        comment: '用户名被占用' //各种错误提示
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
		"userId": 1,
		"id": 1,
		"content": "你已成功报名BU Qun的5分钟撩妹app，请等待发布者的联系，祝你顺利~",
		"creationDate": "10分钟前"
	}]
   }
   ```

  - api: /xdtic/fn/read/msg
   - 说明：标记消息已读
   - request `[POST]` `[JSON]`
   ```
   {
        id: [m001, m002]
   }
   ```
   - response `[JSON]`
   ```
   {
        "code": "ok"/"error"
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

### 管理员

#### 管理员登录页面

- url: /xdtic/admin/login
- jsp: /page/admin/login.jsp

- api: /xdtic/fn/admin/login

    - 管理员登录，直接提交表单，验证通过跳转到主页（/xdtic/admin）
    - request `[POST]`
    ```
    {
        username: adoug,
        password: xxxx
        
    }
    ```
    
#### 管理员主页

- url: /xdtic/admin
- jsp: /page/admin/index.jsp


#### 待审核项目页面

- url: /xdtic/admin/project/check
- jsp: /page/admin/project/uncheck.jsp

- api: /xdtic/fn/admin/get/project/uncheck

    - 获取待审核项目 & 在待审核项目中搜索
    - request `[GET]`
    ```
    {
        pageNum: 0,
        size: 8,
        keyWords: ''
    }
    ```
    - response `[JSON]`
    ```
    {
    "size": 2,
	"pageNum": 0,
	"hasMore": false,

	"projects": [{
		"id": "p001",
		"name": "SSR召唤符画法",
		"username": "adoug"
	}, {
		"id": "p002",
		"name": "tic项目池",
		"username": "adoug"
	}]
    }
    ```

- api: /fn/admin/project/operate

    - 发送项目操作结果
    - request `[POST]` `[JSON]`
    ```
    {
        proId: p001,
        operation: reject/accept/delete,
        rejectReason: ""
    }
    ```
    - response `[JSON]`
    ```
    {
        code: ok
    }
    ```

#### 管理员登录页面

- url: /xdtic/admin/login
- jsp: /page/admin/login
- 页面所需变量
```
{
    loginFail: false
}
```

- api: /xdtic/fn/admin/login

    - 管理员登录，直接提交表单，验证通过跳转到主页（/xdtic/admin）
    - request `[POST]`
    ```
    {
        username: adoug,
        password: xxxx
        
    }
    ```
    
#### 管理员项目详情页

- url: /xdtic/page/admin/project?proId=p001
- jsp: /page/admin/project/detail.jsp
- 页面所需变量
```
{
    "project": {
		"name": "时间典当铺",
		"tag": ["Web", "情感"],
		"id": "p001",
		"recruit": "产品经理：2名  UI设计：1名 前端：1名",
		"content": "这里就是项目的详情了，文字就不限量了，感觉应该加上可以添加图片的功能，这里的文字颜
        色是#222222，字号15。",
        "contact": "邮箱：yuqingyaa@163.com 如有疑问，敬请用以上方式咨询~",
        "creationDate": "2016.01.28"
	},

  "projectCreator": {
    "id": "u001",
    "username": "adoug"
  }
}
```

#### 已通过项目页面

- url: /xdtic/admin/project/accept
- jsp: /page/admin/project/accept.jsp

- api: /xdtic/fn/admin/get/project/accept

    - 获取已通过项目 & 在已通过项目中搜索
    - request `[GET]`
    ```
    {
        pageNum: 0,
        size: 8,
        keyWords: ''
    }
    ```
    - response `[JSON]`
    ```
    {
    "size": 2,
	"pageNum": 0,
	"hasMore": false,

	"projects": [{
		"id": "p001",
		"name": "SSR召唤符画法",
		"username": "adoug"
	}, {
		"id": "p002",
		"name": "tic项目池",
		"username": "adoug"
	}]
    }
    ```
    
#### 用户浏览页面
- url: /xdtic/admin/user/look
- jsp: /page/admin/user/look.jsp

- api: /xdtic/fn/admin/get/user

    - 获取用户列表 & 在用户中搜索
    - request `[GET]`
    ```
    {
        pageNum: 0,
        size: 8,
        keyWords: ''
    }
    ```
    - response `[JSON]`
    ```
    {
    "size": 2,
	"pageNum": 0,
	"hasMore": false,

	"users": [{
		"id": "u001",
		"username": "adoug",
		"name": "陈智仁",
		"email": "942434869@qq.com"
	}, {
		"id": "u002",
		"username": "garfiled",
		"name": "加菲",
		"email": "yuqingya101@163.com"
	}]
    }
    ```

- api: /fn/admin/user/delete

    - 删除用户
    - request `[POST]` `[JSON]`
    ```
    {
        userId: ["u001"] / "All"  //All表示删除所有用户
    }
    ```
    - response `[JSON]`
    ```
    {
        code: ok
    }
    ```

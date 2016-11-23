### 项目大厅

### 个人中心

#### 登录页面

- url： /user/login
- jsp: /page/user/login.jsp

- api: /fn/valid/user `[POST]`

   - 说明：异步验证用户名和密码

   - request
    ```
    {
        userName: "xxx",
        password: "xxx"
    }
    ```
    
    - response `[JSON]`
    ```
    {
        code: "ok"/"error"
    }
    ```


- api: /fn/user/login

    - 登录表单提交，api验证并跳转页面至用户中心（url: /user）
    
    -request `[POST]`
    ```
    {
        username: "xxx",
        password: "xxx"
    }
    ```

#### 注册页面

   - url: /user/register
   - jsp: /page/user/register.jsp
   
   - api: /fn/valid/userName `[POST]`
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

   - api: /fn/user/register

    - 注册表单提交，api验证并跳转页面至用户中心（url: /user）
    
        - request `[POST]`
        ```
        {
            userName: "xxx",
            password: "xxx",
            passConfirm: "xxx"
        }
        ```

#### 用户中心页面

   - url: /user
   - jsp: /page/user/center
   - jsp页面插值所需数据
   ```
   {
    	"user": {
    		"userid": "u001",
    		"userName": "adoug",
    		"hasMsg": true/false
    	}
    }
   ```
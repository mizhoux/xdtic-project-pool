;/*!/static/js/user/login.js*/
"use strict";var Promise=require("node_modules/es6-promise/dist/es6-promise").Promise;require("node_modules/whatwg-fetch/fetch");var formSerialize=require("static/js/module/formSerialize"),urlValidUser=urlPrefix+"/fn/valid/user",formLogin=new Vue({el:"#formLogin",data:{hasError:!1,username:"",password:""},methods:{validUser:function(e){fetch(urlValidUser,{method:"POST",headers:{"Content-Type":"application/x-www-form-urlencoded;charset=utf-8"},credentials:"same-origin",body:formSerialize(formLogin.$el)}).then(function(e){return e.json()}).then(function(e){"ok"===e.code?formLogin.$el.submit():formLogin.hasError=!0})["catch"](function(e){console.log("request failed",e)}),e.event.preventDefault()}}});
"use strict";window.addEventListener("load",function(){require("node_modules/es6-promise/dist/es6-promise").Promise;require("node_modules/whatwg-fetch/fetch");{var e=require("static/js/module/tools"),o=urlPrefix+"/fn/project/toJoin",r={name:!1,apply:!1,profe:!1,phone:!1,stunum:!1};new Vue({el:"#appToJoin",data:{user:userInfo,userApply:"",project:projectInfo,inputError:r,editFail:!1,editIsSucc:!1},computed:{userSex:function(){return"boy"===this.user.sex?"男":"女"},hasError:function(){var e=!1;for(var o in this.inputError)e=e||this.inputError[o];return e}},methods:{joinProject:function(){var r=this;this.hasError||fetch(o,{method:"POST",headers:{"Content-Type":"application/x-www-form-urlencoded;charset=utf-8"},credentials:"same-origin",body:e.formSerialize("#formToJoin")}).then(function(e){return e.json()}).then(function(e){"ok"===e.code?(r.editIsSucc=!0,setTimeout(function(){window.location.href=urlPrefix+"/myProject/myCollect/detail?uid="+r.user.id+"&proId="+r.project.proId},500)):r.editFail=!0})["catch"](function(e){r.editFail=!0,console.log("request failed",e)})},closeDialog:function(){this.editFail=!1},validateToJoin:function(e){var o=e.target,r=o.name,t=o.value;("name"===r||"apply"===r||"profe"===r||"phone"===r||"stunum"===r)&&(this.inputError[r]=""===t?!0:!1)}}})}});
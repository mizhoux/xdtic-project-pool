;/*!/static/js/user/msgs.js*/
"use strict";function loadMore(){if(!this.noMore){var e=urlLoadMoreMsg+"?uid="+this.user.id+"&pageNum="+pageNum+"&size="+pageSize;this.busy=!0,this.isLoading=!0,self=this,fetch(e,{method:"GET",headers:{Accept:"application/json"}}).then(function(e){return e.json()}).then(function(e){self.isLoading=!1,self.msgs=self.msgs.concat(e.msgs),e.hasMore===!1&&(self.noMore=!0),pageNum++,self.busy=!1})}}var infiniteScroll=require("node_modules/vue-infinite-scroll/vue-infinite-scroll").infiniteScroll,Promise=require("node_modules/es6-promise/dist/es6-promise").Promise;require("node_modules/whatwg-fetch/fetch");var urlLoadMoreMsg=urlPrefix+"/fn/get/msg",pageNum=0,pageSize=8;Vue.component("tic-msg",{template:"#tic-msg",props:["msg"]});var msgBox=new Vue({el:"#appBody",data:{msgs:[],busy:!1,noMore:!1,isLoading:!1,checkImmediately:!1,user:userInfo},beforeMount:function(){loadMore.call(this)},methods:{loadMore:loadMore},directives:{infiniteScroll:infiniteScroll}});
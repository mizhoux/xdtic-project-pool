;/*!/static/js/user/center.js*/
/**
 * Created by chenpeng on 2017/3/25.
 */

'use strict';

var Promise = require('node_modules/es6-promise/dist/es6-promise').Promise;
require('node_modules/whatwg-fetch/fetch');

var urlHasMsg = urlPrefix + '/fn/hasMsg';

var app = new Vue({
  el: '#app',

  data: {
    hasMsg: false,

    user: userInfo
  },

  created: function created() {
    this.queryHasMsg();
  },

  methods: {
    queryHasMsg: function queryHasMsg() {
      var self = this;
      var url = urlHasMsg + '?userId=' + self.user.id;

      fetch(url, {
        method: 'GET',
        headers: {
          'Accept': 'application/json'
        },
        credentials: 'same-origin'
      }).then(function (response) {
        return response.json();
      }).then(function (data) {
        self.hasMsg = data.hasMsg;
      })['catch'](function (error) {
        consol.log('出错了');
      });
    }
  }
});
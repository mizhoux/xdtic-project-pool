/**
 * @fileOverview for profile.jsp
 * @Author       adoug
 * @DateTime     2016-11-21
 */

'use strict';

var Promise = require('node_modules/es6-promise/dist/es6-promise').Promise;
require('node_modules/whatwg-fetch/fetch');

var tools = require('static/js/module/tools');

var urlUpdateProfile = urlPrefix + '/fn/update/profile';
var urlValidProfile = urlPrefix + '/fn/valid/profile';

var multilineContent = {
  render: function render(createElement) {
    var lines = this.content.split('\n');
    var len = lines.length;
    var linesNew = [];

    while (len--) {
      linesNew.unshift(lines[len], 'br');
    }
    linesNew.pop();

    return createElement('p', linesNew.map(function (line) {
      if (line === 'br') {
        return createElement(line);
      } else {
        return createElement('span', line);
      }
    }));
  },
  props: {
    content: {
      require: true,
      type: String
    }
  }
};

var appProfile = new Vue({
  el: '#appProfile',
  data: {
    isEditing: false,
    user: userInfo,

    editFail: false,
    editIsSucc: false,

    usernameNullErr: false,
    profileErr: false,
    errorMsg: ''
  },
  computed: {
    userSex: function userSex() {
      return this.user.sex === 'boy' ? '男' : '女';
    },

    hasError: function hasError() {
      return this.usernameNullErr || this.profileErr;
    }
  },
  methods: {
    showEditer: function showEditer(params) {
      this.isEditing = true;
    },
    editProfile: function editProfile(params) {
      var _this = this;

      var promiseValidProfile;

      promiseValidProfile = this.validProfile();

      if (this.hasError) {
        window.scroll(0, 0);
        return;
      }

      promiseValidProfile.then(function () {
        _this.updateProfile();
      })['catch'](function (error) {
        if (error.message.indexOf('Big Error') > -1) {
          window.scroll(0, 0);
          console.log('form invalid');
        }
      });
    },

    navBack: function navBack() {
      window.history.go(-1);
    },

    closeDialog: function closeDialog() {
      this.editFail = false;
    },

    updateProfile: function updateProfile() {
      return fetch(urlUpdateProfile, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8',
          'Accept': 'application/json'
        },
        credentials: 'same-origin',
        body: tools.formSerialize('#formProfile')
      }).then(function (response) {
        return response.json();
      }).then(function (data) {
        if (data.code === 'ok') {
          appProfile.editIsSucc = true;
          setTimeout(function () {
            appProfile.isEditing = false;
            appProfile.editIsSucc = false;
          }, 500);
        } else {
          appProfile.editFail = true;
        }
      })['catch'](function (error) {
        appProfile.editFail = true;
        console.log('request failed', error);
      });
    },

    validProfile: function validProfile() {
      var _this2 = this;

      if (!this.user.username) {
        this.errorMsg = '昵称不可为空哦';
        this.usernameNullErr = true;
        return false;
      } else {
        this.usernameNullErr = false;
      }

      return fetch(urlValidProfile, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8',
          'Accept': 'application/json'
        },
        credentials: 'same-origin',
        body: tools.formSerialize('#formProfile')
      }).then(function (response) {
        return response.json();
      }).then(function (data) {
        if (data.code === 'error') {
          _this2.errorMsg = data.comment;
          _this2.profileErr = true;
          throw new Error('Big Error');
        } else {
          _this2.profileErr = false;
        }
      });
    }
  },

  components: {
    'multiline-content': multilineContent
  }
});
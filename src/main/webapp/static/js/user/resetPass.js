'use strict';

var Promise = require('node_modules/es6-promise/dist/es6-promise').Promise;
require('node_modules/whatwg-fetch/fetch');

var urlValidUserName = urlPrefix + '/fn/valid/user';

var formResetPass = new Vue({
    el: '#formResetPass',
    data: {
        username: '',
        passOld: '',
        passNew: '',
        passNewConfirm: '',
        userError: false,
        passError: false
    },
    computed: {
        errorCount: function errorCount() {
            var count = 0;
            if (this.userError) count++;
            if (this.passError) count++;
            return count;
        },
        errorMsg: function errorMsg() {
            var msg = '';
            if (this.userError) {
                msg = '用户名或原始密码错误';
            } else if (this.passError) {
                msg = '两次新密码输入不一致';
            }
            return msg;
        },
        hasError: function hasError() {
            return this.userError || this.passError;
        }
    },

    methods: {
        validUser: function validUser(event) {
            fetch(urlValidUserName, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify({
                    username: formResetPass.username,
                    password: formResetPass.passOld
                })
            }).then(function (response) {
                return response.json();
            }).then(function (data) {
                if (data.code == 'ok') {
                    formResetPass.userError = false;
                } else {
                    formResetPass.userError = true;
                }
            })['catch'](function (error) {
                console.log('request failed', error);
            });
        },

        validPass: function validPass() {
            this.passError = !(this.passNew === this.passNewConfirm);
            return this.passError;
        },

        validForm: function validForm(param) {
            fetch(urlValidUserName, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    username: formResetPass.username,
                    password: formResetPass.passOld
                })
            }).then(function (response) {
                return response.json();
            }).then(function (data) {
                if (data.code == 'ok') {
                    formResetPass.userError = false;
                    formResetPass.validPass();
                } else {
                    formResetPass.userError = true;
                }

                if (!formResetPass.hasError) {
                    formResetPass.$el.submit();
                }
            })['catch'](function (error) {
                console.log('request failed', error);
            });
        },

        navBack: function navBack() {
            window.history.go(-1);
        }
    }
});
'use strict';

var Promise = require('node_modules/es6-promise/dist/es6-promise').Promise;
require('node_modules/whatwg-fetch/fetch');

var urlValidUserName = '/fn/valid/userName';

var formRegister = new Vue({
    el: '#formRegister',
    data: {
        userName: '',
        password: '',
        passConfirm: '',
        userNameError: false,
        passError: false
    },
    computed: {
        errorCount: function errorCount() {
            var count = 0;
            if (this.userNameError) count++;
            if (this.passError) count++;
            return count;
        },
        errorMsg: function errorMsg() {
            var msg = '';
            if (this.userNameError) {
                msg = '抱歉，用户名已被注册';
            } else if (this.passError) {
                msg = '两次密码输入不一致';
            }
            return msg;
        },
        hasError: function hasError() {
            return this.userNameError || this.passError;
        }
    },

    methods: {
        validName: function validName(event) {
            fetch(urlValidUserName, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    userName: formRegister.userName
                })
            }).then(function (response) {
                return response.json();
            }).then(function (data) {
                if (data.code == 'ok') {
                    formRegister.userNameError = false;
                } else {
                    formRegister.userNameError = true;
                }
            })['catch'](function (error) {
                console.log('request failed', error);
            });
        },

        validPass: function validPass() {
            this.passError = !(this.password === this.passConfirm);
            return this.passError;
        },

        validRegister: function validRegister(param) {
            fetch(urlValidUserName, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    userName: formRegister.userName
                })
            }).then(function (response) {
                return response.json();
            }).then(function (data) {
                if (data.code == 'ok') {
                    formRegister.userNameError = false;
                    formRegister.validPass();
                } else {
                    formRegister.userNameError = true;
                }

                if (!formRegister.hasError) {
                    formRegister.$el.submit();
                }
            })['catch'](function (error) {
                console.log('request failed', error);
            });
        }
    }
});
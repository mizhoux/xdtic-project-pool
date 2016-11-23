'use strict';

var Promise = require('node_modules/es6-promise/dist/es6-promise').Promise;
require('node_modules/whatwg-fetch/fetch');

var urlValidUserName = '/xdtic/fn/valid/username';

var formRegister = new Vue({
    el: '#formRegister',
    data: {
        username: '',
        password: '',
        passConfirm: '',
        usernameError: false,
        passError: false
    },
    computed: {
        errorCount: function errorCount() {
            var count = 0;
            if (this.usernameError) count++;
            if (this.passError) count++;
            return count;
        },
        errorMsg: function errorMsg() {
            var msg = '';
            if (this.usernameError) {
                msg = '抱歉，用户名已被注册';
            } else if (this.passError) {
                msg = '两次密码输入不一致';
            }
            return msg;
        },
        hasError: function hasError() {
            return this.usernameError || this.passError;
        }
    },

    methods: {
        validName: function validName(event) {
            fetch(urlValidUserName, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify({
                    username: formRegister.username
                })
            }).then(function (response) {
                return response.json();
            }).then(function (data) {
                // console.log("code: " + data.code);
                if (data.code === 'ok') {
                    formRegister.usernameError = false;
                } else {
                    formRegister.usernameError = true;
                }
            })
            ['catch'](function (error) {
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
                    username: formRegister.username
                })
            }).then(function (response) {
                return response.json();
            }).then(function (data) {
                if (data.code == 'ok') {
                    formRegister.usernameError = false;
                    formRegister.validPass();
                } else {
                    formRegister.usernameError = true;
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
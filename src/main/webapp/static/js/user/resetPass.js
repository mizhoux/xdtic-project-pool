'use strict';

var Promise = require('node_modules/es6-promise/dist/es6-promise').Promise;
require('node_modules/whatwg-fetch/fetch');

var MD5 = require('node_modules/crypto-js/md5');

var urlValidUser = urlPrefix + '/fn/valid/user';

var formResetPass = new Vue({
    el: '#formResetPass',
    data: {
        username: '',
        passOld: '',
        passNew: '',
        passNewConfirm: '',

        inputNull: false,

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
            if (this.inputNull) {
                msg = '表单没有填完哦';
            } else if (this.userError) {
                msg = '用户名或原始密码错误';
            } else if (this.passError) {
                msg = '两次新密码输入不一致';
            }
            return msg;
        },
        hasError: function hasError() {
            return this.userError || this.passError || this.inputNull;
        }
    },

    methods: {
        validUser: function validUser(event) {
            fetch(urlValidUser, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify({
                    "username": formResetPass.username,
                    "password": MD5(formResetPass.passOld).toString()
                }),
                credentials: 'same-origin'
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

        validInputNull: function validInputNull() {
            this.inputNull = !(!!this.username && !!this.passOld && !!this.passNew && !!this.passNewConfirm);
            return this.inputNull;
        },

        validForm: function validForm(param) {
            if (this.validInputNull() && this.validPass()) {
                return;
            }

            fetch(urlValidUser, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify({
                    "username": formResetPass.username,
                    "password": MD5(formResetPass.passOld).toString()
                }),
                credentials: 'same-origin'
            }).then(function (response) {
                return response.json();
            }).then(function (data) {
                if (data.code == 'ok') {
                    formResetPass.userError = false;
                    formResetPass.validInputNull();
                    formResetPass.validPass();
                } else {
                    formResetPass.userError = true;
                }

                if (!formResetPass.hasError) {
                    formResetPass.passOld = MD5(formResetPass.passOld).toString();
                    formResetPass.$nextTick(function () {
                        formResetPass.$el.submit();
                    });
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
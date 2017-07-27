;/*!/static/js/user/login.js*/
'use strict';

var Promise = require('node_modules/es6-promise/dist/es6-promise').Promise;
require('node_modules/whatwg-fetch/fetch');

var MD5 = require('node_modules/crypto-js/md5');

var formSerialize = require('static/js/module/formSerialize');
var tools = require('static/js/module/tools');

var urlValidUser = urlPrefix + '/fn/valid/user';

var formLogin = new Vue({
    el: '#formLogin',
    data: {
        hasError: false,
        username: '',
        password: ''
    },

    computed: {
        md5Pass: function md5Pass() {
            return MD5(this.password);
        }
    },

    methods: {
        validUser: function validUser(params) {
            var self = this;

            fetch(urlValidUser, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
                },
                credentials: 'same-origin',
                body: tools.obj2form({
                    username: self.username,
                    password: self.md5Pass
                })
            }).then(function (response) {
                return response.json();
            }).then(function (data) {
                if (data.code === 'ok') {
                    self.password = MD5(self.password).toString();
                    self.$nextTick(function () {
                        formLogin.$el.submit();
                    });
                } else {
                    formLogin.hasError = true;
                }
            })['catch'](function (error) {
                console.log('request failed', error);
            });

            params.event.preventDefault();
        }
    }
});
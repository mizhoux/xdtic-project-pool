'use strict';

var Promise = require('node_modules/es6-promise/dist/es6-promise').Promise;
require('node_modules/whatwg-fetch/fetch');

var formSerialize = require('static/js/module/formSerialize');

var urlValidUser = urlPrefix + '/fn/valid/user';

var formLogin = new Vue({
    el: '#formLogin',
    data: {
        hasError: false,
        username: '',
        password: ''
    },
    methods: {
        validUser: function validUser(params) {
            fetch(urlValidUser, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
                },
                credentials: 'same-origin',
                body: formSerialize(formLogin.$el)
            }).then(function (response) {
                return response.json();
            }).then(function (data) {
                if (data.code === 'ok') {
                    formLogin.$el.submit();
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
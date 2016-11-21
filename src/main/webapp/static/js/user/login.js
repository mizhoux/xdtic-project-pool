'use strict';

var Promise = require('node_modules/es6-promise/dist/es6-promise').Promise;
require('node_modules/whatwg-fetch/fetch');

var urlValidUser = '/xdtic/fn/valid/user';

var formLogin = new Vue({
    el: '#formLogin',
    data: {
        hasError: false,
        userName: '',
        password: ''
    },
    methods: {
        validUser: function validUser(params) {
            fetch(urlValidUser, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                credentials: 'same-origin',
                body: ['username=', formLogin.$el.username.value, '&password=', formLogin.$el.password.value].join('')
            }).then(function (response) {
                return response.json();
            }).then(function (data) {
                console.log("data: " + JSON.stringify(data));
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
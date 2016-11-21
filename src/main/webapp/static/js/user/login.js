'use strict';

var Promise = require('node_modules/es6-promise/dist/es6-promise').Promise;
require('node_modules/whatwg-fetch/fetch');

var urlValidUser = '/fn/valid/user';

var formRegister = new Vue({
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
                credentials: 'same-origin',
                body: new FormData(formRegister.$el)
            }).then(function (response) {
                return response.json();
            }).then(function (data) {
                if (data.code == 'ok') {
                    formRegister.$el.submit();
                } else {
                    formRegister.hasError = true;
                }
            })['catch'](function (error) {
                console.log('request failed', error);
            });

            params.event.preventDefault();
        }
    }
});
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

var appProfile = new Vue({
	el: '#appProfile',
	data: {
		isEditing: false,
		user: userInfo,

		editFail: false,
		editIsSucc: false
	},
	computed: {
		userSex: function userSex() {
			return this.user.sex === 'boy' ? '男' : '女';
		}
	},
	methods: {
		showEditer: function showEditer(params) {
			this.isEditing = true;
		},
		editProfile: function editProfile(params) {
			fetch(urlUpdateProfile, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
				},
				credentials: 'same-origin',
				body: tools.formSerialize('#formProfile')
			}).then(function (response) {
				return response.json();
			}).then(function (data) {
				if (data.code === 'ok') {
					var _self = this;
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

		navBack: function navBack() {
			window.history.go(-1);
		},

		closeDialog: function closeDialog() {
			this.editFail = false;
		}
	}
});
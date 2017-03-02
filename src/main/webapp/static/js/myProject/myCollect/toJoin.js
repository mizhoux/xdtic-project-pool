/**
 * @fileOverview for toJoin.jsp
 * @Author       adoug
 * @DateTime     2016-12-05
 */

'use strict';

window.addEventListener('load', function () {
	'use strict';

	var Promise = require('node_modules/es6-promise/dist/es6-promise').Promise;
	require('node_modules/whatwg-fetch/fetch');

	var tool = require('static/js/module/tools');

	var urlToJoin = urlPrefix + '/fn/project/toJoin';

	var inputError = {
		'name': false,
		'apply': false,
		'profe': false,
		'phone': false,
		'stunum': false
	};

	var appToJoin = new Vue({
		el: '#appToJoin',
		data: {
			user: userInfo,
			userApply: '',

			project: projectInfo,

			inputError: inputError,

			editFail: false,
			editIsSucc: false
		},
		computed: {
			userSex: function userSex() {
				return this.user.sex === 'boy' ? '男' : '女';
			},

			hasError: function hasError() {
				var result = false;

				for (var item in this.inputError) {
					result = result || this.inputError[item];
				}

				return result;
			}
		},
		methods: {
			joinProject: function joinProject(params) {
				var self = this;

				if (this.hasError) {
					return;
				}

				fetch(urlToJoin, {
					method: 'POST',
					headers: {
						'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
					},
					credentials: 'same-origin',
					body: tool.formSerialize('#formToJoin')
				}).then(function (response) {
					return response.json();
				}).then(function (data) {
					if (data.code === 'ok') {
						self.editIsSucc = true;
						setTimeout(function () {
							window.location.href = urlPrefix + '/project?uid=' + self.user.id + '&proId=' + self.project.proId;
						}, 500);
					} else {
						self.editFail = true;
					}
				})['catch'](function (error) {
					self.editFail = true;
					console.log('request failed', error);
				});
			},

			closeDialog: function closeDialog() {
				this.editFail = false;
			},

			validateToJoin: function validateToJoin(event) {
				var target = event.target,
				    name = target.name,
				    value = target.value;

				if (name === 'name' || name === 'apply' || name === 'profe' || name === 'phone' || name === 'stunum') {
					if (value === '') {
						this.inputError[name] = true;
					} else {
						this.inputError[name] = false;
					}
				}
			}
		}
	});
});
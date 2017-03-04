/**
 * @fileOverview for editDetail.jsp
 * @Author       adoug
 * @DateTime     2016-12-03
 */

'use strict';

window.addEventListener('load', function () {
	'use strict';

	var Promise = require('node_modules/es6-promise/dist/es6-promise').Promise;
	require('node_modules/whatwg-fetch/fetch');

	var tools = require('static/js/module/tools');

	var urlUpdateProject = urlPrefix + '/fn/project/update';

	var appEditDetail = new Vue({
		el: '#appEditDetail',

		data: {
			user: userInfo,
			project: projectInfo,

			editIsFail: false,
			editIsSucc: false
		},

		methods: {
			editDetail: function editDetail() {
				self = this;

				fetch(urlUpdateProject, {
					method: 'POST',
					headers: {
						'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8',
						'Accept': 'application/json'
					},
					credentials: 'same-origin',
					body: tools.formSerialize('#formEditDetail')
				}).then(function (response) {
					return response.json();
				}).then(function (data) {
					if (data.code === 'ok') {
						self.postIsSucc = true;
						setTimeout(function () {
							window.location.href = urlPrefix + '/myProject/myPost/detail?proId=' + self.project.proId + '&uid=' + self.user.id;
						}, 500);
					} else {
						self.editIsFail = true;
					}
				})['catch'](function (error) {
					self.editIsFail = true;
					console.log('request failed', error);
				});
			},

			closeDialog: function closeDialog() {
				this.editIsFail = false;
			}
		}
	});
});
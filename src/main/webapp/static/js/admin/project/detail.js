/**
 * @fileOverview for /project/check.jsp
 * @Author       adoug
 * @DateTime     2017-2-23
 */

'use strict';

var infiniteScroll = require('node_modules/vue-infinite-scroll/vue-infinite-scroll').infiniteScroll;

var Promise = require('node_modules/es6-promise/dist/es6-promise').Promise;
require('node_modules/whatwg-fetch/fetch');

var urlOperateProject = urlPrefix + '/fn/admin/project/operate';

var appBox = new Vue({
	el: '#operationArea',
	data: {
		project: projectInfo,

		isChecked: false,

		toshow: false,
		rejectReason: ''
	},

	methods: {
		accept: function accept() {
			var self = this;

			requestProcessProject(self, 'accept');
		},

		reject: function reject() {
			this.toshow = true;
		},

		deleteProject: function deleteProject() {
			var self = this;
			requestProcessProject(self, 'delete');
		},

		hideRejectDialog: function hideRejectDialog() {
			this.toshow = false;
		},

		ensureReject: function ensureReject() {
			var self = this;

			requestProcessProject(self, 'reject', this.rejectReason);
		}
	}
});

/**
 * @fileOverview 将审核操作发送给后台
 * @param        {[vue-组件]}   vProject  [project组件]
 * @param        {[String]}   operation [审核操作：pass/reject/delete]
 * @param        {[Int]}   proIndex  [vProject父组件中的索引]
 */
function requestProcessProject(vProject, operation, rejectReason) {
	var jumpUrl = operation === 'delete' ? urlPrefix + '/admin/project/look' : urlPrefix + '/admin/project/check';

	fetch(urlOperateProject, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json',
			'Accept': 'application/json'
		},
		credentials: 'same-origin',
		body: JSON.stringify({
			"operation": operation,
			"proId": vProject.project.proId,
			"rejectReason": rejectReason
		})
	}).then(function (response) {
		return response.json();
	}).then(function (data) {
		if (data.code === 'ok') {
			vProject.isChecked = true;
			vProject.$nextTick(function () {
				setTimeout(function () {
					window.location.href = jumpUrl;
				}, 1000);
			});
		} else {
			alert('网络错误，请稍后重试。');
		}
	})['catch'](function (error) {
		alert('网络错误，请稍后重试。');
	});
}
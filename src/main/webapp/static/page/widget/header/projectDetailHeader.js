window.addEventListener('load', function() {
	'use strict';

	const Promise = require('node_modules/es6-promise/dist/es6-promise').Promise;
	require('node_modules/whatwg-fetch/fetch');

	const urlCollect = `${urlPrefix}/fn/project/collect`;
	const urlUnCollect = `${urlPrefix}/fn/project/uncollect`;

	let detailPostHeader = new Vue({
		el: '#detailPostHeader',

		data: {
			project: projectInfo,
			user: userInfo,

			collectIsFail: false
		},

		methods: {
			collect: function(params) {
				//假装收藏成功，这样给用户的反馈快一点，失败再回滚
				this.project.isCollected = true;
				let self = this;

				let url = `${urlCollect}?userid=${this.user.id}&proId=${this.project.proId}`;
				fetch(url, {
					method: 'GET',
					headers: {
						'Accept': 'application/json'
					}
				})
				.then(response => response.json())
				.then(function(data) {
					if (data.code === 'error') {
						self.project.isCollected = false;
						self.collectIsFail = true;
					}
				});
			},

			uncollect: function(params) {
				//假装取消收藏，这样给用户的反馈快一点，失败再回滚
				this.project.isCollected = false;

				let self = this;

				let url = `${urlUnCollect}?userid=${this.user.id}&proId=${this.project.proId}`;
				fetch(url, {
					method: 'GET',
					headers: {
						'Accept': 'application/json'
					}
				})
				.then(response => response.json())
				.then(function(data) {
					if (data.code === 'error') {
						self.project.isCollected = true;
						self.collectIsFail = true;
					}
				});
			},

			closeDialog: function() {
				this.collectIsFail = false;
			}
		}
	});
});
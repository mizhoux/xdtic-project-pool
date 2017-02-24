'use strict';

window.addEventListener('load', function () {
	'use strict';

	var Promise = require('node_modules/es6-promise/dist/es6-promise').Promise;
	require('node_modules/whatwg-fetch/fetch');

	var urlCollect = urlPrefix + '/fn/project/collect';
	var urlUnCollect = urlPrefix + '/fn/project/uncollect';

	var detailPostHeader = new Vue({
		el: '#detailPostHeader',

		data: {
			project: projectInfo,
			user: userInfo,

			collectIsFail: false
		},

		methods: {
			collect: function collect(params) {
				//假装收藏成功，这样给用户的反馈快一点，失败再回滚
				this.project.isCollected = true;
				var self = this;

				var url = urlCollect + '?userid=' + this.user.id + '&proId=' + this.project.proId;
				fetch(url, {
					method: 'GET',
					headers: {
						'Accept': 'application/json'
					}
				}).then(function (response) {
					return response.json();
				}).then(function (data) {
					if (data.code === 'error') {
						self.project.isCollected = false;
						self.collectIsFail = true;
					}
				});
			},

			uncollect: function uncollect(params) {
				//假装取消收藏，这样给用户的反馈快一点，失败再回滚
				this.project.isCollected = false;

				var self = this;

				var url = urlUnCollect + '?userid=' + this.user.id + '&proId=' + this.project.proId;
				fetch(url, {
					method: 'GET',
					headers: {
						'Accept': 'application/json'
					}
				}).then(function (response) {
					return response.json();
				}).then(function (data) {
					if (data.code === 'error') {
						self.project.isCollected = true;
						self.collectIsFail = true;
					}
				});
			},

			closeDialog: function closeDialog() {
				this.collectIsFail = false;
			},
                        
                        navBack: function() {
                            window.history.go(-1);
                        }
		}
	});
});
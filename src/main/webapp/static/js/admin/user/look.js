/**
 * @fileOverview for /project/check.jsp
 * @Author       adoug
 * @DateTime     2017-2-23
 */

'use strict';

var infiniteScroll = require('node_modules/vue-infinite-scroll/vue-infinite-scroll').infiniteScroll;

var Promise = require('node_modules/es6-promise/dist/es6-promise').Promise;
require('node_modules/whatwg-fetch/fetch');

var urlGetUser = urlPrefix + '/fn/admin/get/user';
var urlDeleteUser = urlPrefix + '/fn/admin/user/delete';

var pageNum = 0;
var pageSize = 8;

Vue.component('tic-user', {
	template: '#tic-project-look',
	props: ['user', 'index'],

	data: function data() {
		return {
			checked: false
		};
	},

	watch: {
		checked: function checked(newChecked) {
			this.$emit('checked', newChecked, this.index);
		}
	}
});

var projectBox = new Vue({
	el: '#app',
	data: {
		users: [],
		busy: false,
		noMore: false,
		isLoading: false,
		checkImmediately: false,

		sidePush: false,

		placeholder: '你想要的用户...',
		keyWords: '',

		checkedUser: []
	},

	computed: {
		hasUser: function hasUser() {
			return this.users.length > 0;
		},

		hasChecked: function hasChecked() {
			return this.checkedUser.length > 0;
		}
	},

	beforeMount: function beforeMount() {
		loadMore.call(this);
	},

	methods: {
		loadMore: loadMore,

		push: function push() {
			this.sidePush = !this.sidePush;
		},

		search: function search() {
			pageNum = 0;

			this.busy = false;
			this.noMore = false;
			this.isLoading = false;
			this.checkImmediately = false;

			this.projects.splice(0, this.projects.length);

			loadMore.call(this);
		},

		//维护选中用户列表
		updateChecked: function updateChecked(isChecked, userIndex) {
			var uid = this.users[userIndex].id;
			if (isChecked) {
				this.checkedUser.push(uid);
			} else {
				var indexOfUid = this.checkedUser.indexOf(uid);
				this.checkedUser.splice(indexOfUid, 1);
			}
		},

		deleteUser: function deleteUser() {
			var self = this;

			if (confirm('你确定要删除指定的用户吗？')) {
				requestDeleteUser(self, this.checkedUser);
			}
		},

		deleteAll: function deleteAll() {
			var self = this;
			if (confirm('你确定要删除所有用户吗？')) {
				requestDeleteUser(self, 'All');
			}
		},

		addUser: function addUser() {}
	},

	directives: { infiniteScroll: infiniteScroll }
});

function loadMore() {
	if (this.noMore) {
		return;
	}

	var url = urlGetUser + '?pageNum=' + pageNum + '&size=' + pageSize + '&keyWords=' + this.keyWords;

	this.busy = true;
	this.isLoading = true;
	self = this;

	fetch(url, {
		method: 'GET',
		headers: {
			'Accept': 'application/json'
		}
	}).then(function (response) {
		return response.json();
	}).then(function (data) {
		self.isLoading = false;

		self.users = self.users.concat(data.users);

		if (data.hasMore === false) {
			self.noMore = true;
		}

		pageNum++;
		self.busy = false;
	});
}

/**
 * @fileOverview 向后台请求删除用户
 * @param        {[Array|String]}   uid [用户id的数组或者All表示删除所有]
 */
function requestDeleteUser(app, uid) {
	fetch(urlDeleteUser, {
		method: 'POST',
		headers: {
			'Accept': 'application/json'
		},
		body: JSON.stringify({
			uid: uid
		})
	}).then(function (response) {
		return response.json();
	}).then(function (data) {
		if (data.code === 'ok') {
			//更新app.users
			for (var index in app.checkedUser) {
				var _uid = app.checkedUser[index];

				for (var elIndex in app.users) {
					var elId = app.users[elIndex].id;
					if (elId === _uid) {
						app.users.splice(elIndex, 1);
						break;
					}
				}
			}
		} else {
			alert('网络错误，请稍后重试。');
		}
	})['catch'](function (error) {
		alert('网络错误，请稍后重试。');
	});
}
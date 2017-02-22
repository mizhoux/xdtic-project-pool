/**
 * @fileOverview for msgs.jsp
 * @Author       adoug
 * @DateTime     2016-11-23
 */

'use strict';

var infiniteScroll = require('node_modules/vue-infinite-scroll/vue-infinite-scroll').infiniteScroll;

var Promise = require('node_modules/es6-promise/dist/es6-promise').Promise;
require('node_modules/whatwg-fetch/fetch');

var urlLoadMoreMsg = urlPrefix + '/fn/get/msg';
var urlReadMsg = urlPrefix + '/fn/read/msg';

var urlProjectSign = urlPrefix + '/project';
var urlProjectPost = urlPrefix + '/myProject/myPost/detail';

var pageNum = 0;
var pageSize = 8;

Vue.component('tic-msg', {
	template: '#tic-msg',
	props: ['msg', 'index'],

	methods: {
		readMsg: function readMsg(params) {
			if (this.msg.read) {
				if (this.msg.type === 'join') {
					window.location.href = urlProjectSign + '?uid=' + this.msg.uid + '&proId=' + this.msg.proId;
				} else {
					window.location.href = urlProjectPost + '?uid=' + this.msg.uid + '&proId=' + this.msg.proId;
				}
			}

			//先假装信息已读，若通信失败回滚
			this.$emit('read', params.msgIndex);

			var self = this;

			var url = urlReadMsg + '?mid=' + this.msg.mid;
			fetch(url, {
				method: 'GET',
				headers: {
					'Accept': 'application/json'
				}
			}).then(function (response) {
				return response.json();
			}).then(function (data) {
				if (data.code === 'error') {
					self.$emit('unread', params.msgIndex);
					alert('网络出问题了，请稍后重试');
				} else {
					if (self.msg.type === 'join') {
						window.location.href = urlProjectSign + '?uid=' + self.msg.uid + '&proId=' + self.msg.proId;
					} else {
						window.location.href = urlProjectPost + '?uid=' + self.msg.uid + '&proId=' + self.msg.proId;
					}
				}
			})['catch'](function (error) {
				self.$emit('unread', params.msgIndex);
				alert('网络出问题了，请稍后重试');
			});
		}
	}
});

var msgBox = new Vue({
	el: '#appBody',
	data: {
		msgs: [],
		busy: false,
		noMore: false,
		isLoading: false,
		checkImmediately: false,
		user: userInfo
	},

	beforeMount: function beforeMount() {
		loadMore.call(this);
	},

	methods: {
		loadMore: loadMore,

		readMsg: function readMsg(msgIndex) {
			this.msgs[msgIndex].read = true;
		},

		unreadMsg: function unreadMsg(msgIndex) {
			this.msgs[msgIndex].read = false;
		}
	},

	directives: { infiniteScroll: infiniteScroll }
});

function loadMore() {
	if (this.noMore) {
		return;
	}

	var url = urlLoadMoreMsg + '?uid=' + this.user.id + '&pageNum=' + pageNum + '&size=' + pageSize;

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
		self.msgs = self.msgs.concat(data.msgs);

		if (data.hasMore === false) {
			self.noMore = true;
		}

		pageNum++;
		self.busy = false;
	});
}
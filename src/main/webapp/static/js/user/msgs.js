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

var bus = new Vue();

bus.$on('readAll', function () {
	msgBox.readAllMsg();
});

Vue.component('tic-msg', {
	template: '#tic-msg',
	props: ['msg', 'index'],

	methods: {
		readMsg: function readMsg(params) {
			var jumpUrl;

			if (this.msg.type === '') {
				jumpUrl = urlProjectSign + '?uid=' + this.msg.uid + '&proId=' + this.msg.proId;
			} else {
				jumpUrl = urlProjectPost + '?uid=' + this.msg.uid + '&proId=' + this.msg.proId;
			}

			if (this.msg.read) {
				window.location.href = jumpUrl;
			}

			//先假装信息已读，若通信失败回滚
			this.$emit('read', params.msgIndex);

			var self = this;

			fetch(urlReadMsg, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
					'Accept': 'application/json'
				},
				credentials: 'same-origin',
				body: JSON.stringify({
					mid: [self.msg.mid]
				})
			}).then(function (response) {
				return response.json();
			}).then(function (data) {
				if (data.code === 'error') {
					self.$emit('unread', params.msgIndex);
					alert('网络出问题了，请稍后重试');
				} else {
					window.location.href = jumpUrl;
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
		msgsUnread: [],

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
		},

		readAllMsg: function readAllMsg() {
			var self = this;

			fetch(urlReadMsg, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json',
					'Accept': 'application/json'
				},
				credentials: 'same-origin',
				body: JSON.stringify({
					mid: self.msgsUnread
				})
			}).then(function (response) {
				return response.json();
			}).then(function (data) {
				if (data.code === 'error') {
					alert('网络出问题了，请稍后重试');
				} else {
					self.msgs = self.msgs.map(function (msg, index) {
						msg.read = true;
						return msg;
					});
				}
			})['catch'](function (error) {
				alert('网络出问题了，请稍后重试');
			});
		}
	},

	directives: { infiniteScroll: infiniteScroll }
});

var msgHeader = new Vue({
	el: '#appHeader',

	methods: {
		readAllMsgNotice: function readAllMsgNotice() {
			if (confirm('你确定要标记所有消息为已读吗？')) {
				bus.$emit('readAll');
			}
		},

		navBack: function navBack() {
			window.history.back();
		}
	}
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
		var newUnreadMsg,
		    newMsgs = data.msgs;

		self.isLoading = false;
		self.msgs = self.msgs.concat(data.msgs);

		//记录未读消息
		newUnreadMsg = newMsgs.filter(function (msg, index) {
			return !msg.read;
		});
		newUnreadMsg = newUnreadMsg.map(function (msg) {
			return msg.mid;
		});

		self.msgsUnread = self.msgsUnread.concat(newUnreadMsg);

		if (data.hasMore === false) {
			self.noMore = true;
		}

		pageNum++;
		self.busy = false;
	});
}
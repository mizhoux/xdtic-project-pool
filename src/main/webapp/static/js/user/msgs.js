/**
 * @fileOverview for msgs.jsp
 * @Author       adoug
 * @DateTime     2016-11-23
 */

'use strict';

var infiniteScroll = require('node_modules/vue-infinite-scroll/vue-infinite-scroll').infiniteScroll;

var Promise = require('node_modules/es6-promise/dist/es6-promise').Promise;
require('node_modules/whatwg-fetch/fetch');

var urlLoadMoreMsg = '/xdtic/fn/get/msg';

var pageNum = 1;
var pageSize = 8;

Vue.component('tic-msg', {
	template: '#tic-msg',
	props: ['msg']
});

var msgBox = new Vue({
	el: '#appBody',
	data: {
		msgs: [],
		busy: false,
		noMore: false,
		isLoading: false,
		checkImmediately: false
	},

	beforeMount: function beforeMount() {
		loadMore.call(this);
	},

	methods: {
		loadMore: loadMore
	},

	directives: { infiniteScroll: infiniteScroll }
});

function loadMore() {
	if (this.noMore) {
		return;
	}

	var url = urlLoadMoreMsg + '?pageNum=' + pageNum + '&size=' + pageSize;

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
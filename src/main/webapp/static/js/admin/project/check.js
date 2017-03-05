/**
 * @fileOverview for /project/check.jsp
 * @Author       adoug
 * @DateTime     2017-2-23
 */

'use strict';

var infiniteScroll = require('node_modules/vue-infinite-scroll/vue-infinite-scroll').infiniteScroll;

var Promise = require('node_modules/es6-promise/dist/es6-promise').Promise;
require('node_modules/whatwg-fetch/fetch');

var urlGetProjectUncheck = urlPrefix + '/fn/admin/get/project/uncheck';
var urlOperateProject = urlPrefix + '/fn/admin/project/operate';

var pageNum = 0;
var pageSize = 8;

Vue.component('tic-project-check', {
	template: '#tic-project-check',
	props: ['project', 'index', 'ensurereject'],

	watch: {
		ensurereject: function ensurereject(newValue) {
			var self = this;

			newValue && requestProcessProject(self, 'reject', this.project.proIndex);
		}
	},

	methods: {
		reject: function reject(params) {
			var self = this;

			this.$emit('toreject', params.proIndex);
		},

		accept: function accept(params) {
			var self = this;

			requestProcessProject(self, 'accept', params.proIndex);
		}
	}
});

Vue.component('tic-tag', {
	template: '#tic-tag',

	props: ['projectindex', 'toshow'],

	data: function data() {
		return {
			rejectReason: ''
		};
	},

	methods: {
		hideRejectDialog: function hideRejectDialog() {
			this.$emit('hide-reject-dialog');
		},

		ensureReject: function ensureReject() {
			this.$emit('ensure-reject', this['projectindex'], this.rejectReason);
		}
	}
});

var projectBox = new Vue({
	el: '#app',
	data: {
		projects: [],
		busy: false,
		noMore: false,
		isLoading: false,
		checkImmediately: false,

		sidePush: false,

		placeholder: '你想要的项目...',
		keyWords: '',

		rejectProjectIndex: '',
		isShowReject: false
	},

	beforeMount: function beforeMount() {
		loadMore.call(this);
	},

	methods: {
		loadMore: loadMore,

		push: function push() {
			this.sidePush = !this.sidePush;
		},

		processProject: function processProject(proIndex) {
			var self = this;

			this.isShowReject = false;

			this.projects[proIndex].isProcessed = true;
			this.$nextTick(function () {
				setTimeout(function () {
					self.projects[proIndex].animationEnd = true;
				}, 500);
			});
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

		toReject: function toReject(proIndex) {
			this.isShowReject = true;
			this.rejectProjectIndex = proIndex;
		},

		hideRejectDialog: function hideRejectDialog() {
			this.isShowReject = false;
		},

		ensureReject: function ensureReject(proIndex, rejectReason) {
			this.projects[proIndex].rejectReason = rejectReason;
			this.projects[proIndex].ensureReject = true;
			this.projects[proIndex].proIndex = proIndex;
		}
	},

	directives: { infiniteScroll: infiniteScroll }
});

function loadMore() {
	if (this.noMore) {
		return;
	}

	var url = urlGetProjectUncheck + '?pageNum=' + pageNum + '&size=' + pageSize + '&keyWords=' + this.keyWords;

	this.busy = true;
	this.isLoading = true;
	self = this;

	fetch(url, {
		method: 'GET',
		headers: {
			'Accept': 'application/json'
		},
		credentials: 'same-origin'
	}).then(function (response) {
		return response.json();
	}).then(function (data) {
		self.isLoading = false;

		data.projects.map(function (el) {
			el.isProcessed = false;
			el.animationEnd = false;
			el.ensureReject = false;
			el.rejectReason = '';
			el.proIndex = '';
			return el;
		});
		self.projects = self.projects.concat(data.projects);

		if (data.hasMore === false) {
			self.noMore = true;
		}

		pageNum++;
		self.busy = false;
	});
}

/**
 * @fileOverview 将审核操作发送给后台
 * @param        {[vue-组件]}   vProject  [project组件]
 * @param        {[String]}   operation [审核操作：pass/reject]
 * @param        {[Int]}   proIndex  [vProject父组件中的索引]
 */
function requestProcessProject(vProject, operation, proIndex, rejectReason) {
	fetch(urlOperateProject, {
		method: 'POST',
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json'
		},
		credentials: 'same-origin',
		body: JSON.stringify({
			"operation": operation,
			"proId": vProject.project.proId,
			"rejectReason": vProject.project.rejectReason
		})
	}).then(function (response) {
		return response.json();
	}).then(function (data) {
		if (data.code === 'ok') {
			vProject.$emit('process', proIndex);
		} else {
			alert('网络错误，请稍后重试。');
		}
	})['catch'](function (error) {
		alert('网络错误，请稍后重试。');
	});
}
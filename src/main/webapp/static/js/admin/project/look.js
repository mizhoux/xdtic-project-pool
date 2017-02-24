/**
 * @fileOverview for /project/check.jsp
 * @Author       adoug
 * @DateTime     2017-2-23
 */

'use strict';

var infiniteScroll = require('node_modules/vue-infinite-scroll/vue-infinite-scroll').infiniteScroll;

var Promise = require('node_modules/es6-promise/dist/es6-promise').Promise;
require('node_modules/whatwg-fetch/fetch');

var urlGetProjectAccept = urlPrefix + '/fn/admin/get/project/accept';
var urlOperateProject = urlPrefix + '/fn/admin/project/operate';

var pageNum = 0;
var pageSize = 8;

Vue.component('tic-project-look', {
	template: '#tic-project-look',
	props: ['project', 'index'],

	data: function data() {
		return {
			isDeleted: false
		};
	},

	methods: {
		deleteProject: function deleteProject(params) {
			var self = this;

			if (confirm('确定要删除该项目吗？')) {
				requestProcessProject(self, 'delete', params.proIndex);
			}
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
		keyWords: ''
	},

	beforeMount: function beforeMount() {
		loadMore.call(this);
	},

	methods: {
		loadMore: loadMore,

		push: function push() {
			this.sidePush = !this.sidePush;
		},

		deleteProject: function deleteProject(proIndex) {
			this.projects.splice(proIndex, 1);
		},

		search: function search() {
			pageNum = 0;

			this.busy = false;
			this.noMore = false;
			this.isLoading = false;
			this.checkImmediately = false;

			this.projects.splice(0, this.projects.length);

			loadMore.call(this);
		}
	},

	directives: { infiniteScroll: infiniteScroll }
});

function loadMore() {
	if (this.noMore) {
		return;
	}

	var url = urlGetProjectAccept + '?pageNum=' + pageNum + '&size=' + pageSize + '&keyWords=' + this.keyWords;

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

		data.projects.map(function (el) {
			el.isProcessed = false;
			el.animationEnd = false;
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
 * @fileOverview 将删除操作发送给后台
 * @param        {[vue-组件]}   vProject  [project组件]
 * @param        {[String]}   operation [审核操作：delete]
 * @param        {[Int]}   proIndex  [vProject父组件中的索引]
 */
function requestProcessProject(vProject, operation, proIndex) {
	fetch(urlOperateProject, {
		method: 'POST',
		headers: {
			'Accept': 'application/json'
		},
		body: JSON.stringify({
			operation: operation,
			proID: vProject.project.proId
		})
	}).then(function (response) {
		return response.json();
	}).then(function (data) {
		if (data.code === 'ok') {
			vProject.isDeleted = true;
			vProject.$emit('delete', proIndex);
		} else {
			alert('网络错误，请稍后重试。');
		}
	})['catch'](function (error) {
		alert('网络错误，请稍后重试。');
	});
}
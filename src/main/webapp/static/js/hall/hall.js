/**
 * @fileOverview for hall.jsp
 * @Author       adoug
 * @DateTime     2016-11-24
 */

'use strict';

var infiniteScroll = require('node_modules/vue-infinite-scroll/vue-infinite-scroll').infiniteScroll;

var Promise = require('node_modules/es6-promise/dist/es6-promise').Promise;
require('node_modules/whatwg-fetch/fetch');

var urlSearchProject = urlPrefix + '/fn/get/project';

var urlSearchHotProject = urlPrefix + '/fn/get/hotProject';

var urlCollect = urlPrefix + '/fn/project/collect';
var urlUnCollect = urlPrefix + '/fn/project/uncollect';

//组件间通信，事件代理
var bus = new Vue();

//searchBar接收请求操作后通知projectBox
bus.$on('loadProject', function (keyWords) {
	projectBox.pageNum = 0;
	projectBox.keyWords = keyWords;

	projectBox.projects.splice(0, projectBox.projects.length);
	projectBox.hotProjects.splice(0, projectBox.hotProjects.length);

	projectBox.noMore = false;
	projectBox.busy = false;
	projectBox.isLoading = false;
	projectBox.checkImmediately = false;

	projectBox.collectIsFail = false;

	projectBox.loadProject();
	projectBox.loadHotProject();
});

Vue.component('tic-project', {
	template: '#tic-project',

	props: ['project', 'index', 'userid'],

	computed: {
		projectDetailHref: function projectDetailHref() {
			if (this.project.userid == this.userid) {
				return urlPrefix + '/myProject/myPost/detail?proId=' + this.project.proId;
			} else {
				return urlPrefix + '/project?proId=' + this.project.proId + '&uid=' + this.userid;
			}
		}
	},

	methods: {
		collect: function collect(params) {
			//假装收藏成功，这样给用户的反馈快一点，收藏失败再回滚
			this.$emit('collect', params.projectIndex);
			// this.project.isCollected = true;
			var self = this;

			var url = urlCollect + '?userid=' + params.userid + '&proId=' + this.project.proId;
			fetch(url, {
				method: 'GET',
				headers: {
					'Accept': 'application/json'
				}
			}).then(function (response) {
				return response.json();
			}).then(function (data) {
				if (data.code === 'error') {
					self.$emit('uncollect', params.projectIndex);
					// this.project.isCollected = false;
					self.$emit('collectFail');
				}
			});
		},

		uncollect: function uncollect(params) {
			//假装收藏成功，这样给用户的反馈快一点，收藏失败再回滚
			this.$emit('uncollect', params.projectIndex);
			// this.project.isCollected = false;

			var self = this;

			var url = urlUnCollect + '?userid=' + params.userid + '&proId=' + this.project.proId;
			fetch(url, {
				method: 'GET',
				headers: {
					'Accept': 'application/json'
				}
			}).then(function (response) {
				return response.json();
			}).then(function (data) {
				if (data.code === 'error') {
					self.$emit('collect', params.projectIndex);
					// this.project.isCollected = false;
					self.$emit('collectFail');
				}
			});
		}
	}
});

var projectBox = new Vue({
	el: '#appBody',
	data: {
		projects: [],
		hotProjects: [],

		busy: false,
		noMore: false,
		isLoading: false,
		checkImmediately: false,

		collectIsFail: false,

		pageNum: 0,
		pageSize: 5,
		hotSize: 2,

		keyWords: '',

		user: userInfo,
		pageSelected: 'hall'
	},

	beforeMount: function beforeMount() {
		this.loadHotProject();
		this.loadProject();
	},

	methods: {
		loadProject: loadProject,
		loadHotProject: loadHotProject,

		collect: function collect(index) {
			this.projects[index].isCollected = true;
		},

		collectHot: function collectHot(index) {
			this.hotProjects[index].isCollected = true;
		},

		uncollect: function uncollect(index) {
			this.projects[index].isCollected = false;
		},

		uncollectHot: function uncollectHot(index) {
			this.hotProjects[index].isCollected = false;
		},

		closeDialog: function closeDialog() {
			this.collectIsFail = false;
		},

		openDialog: function openDialog() {
			this.collectIsFail = true;
		}
	},

	directives: { infiniteScroll: infiniteScroll }
});

var searchBar = new Vue({
	el: '#searchBar',
	data: {
		keyWords: '',

		isFocusing: false
	},

	methods: {
		showInput: function showInput() {
			this.isFocusing = true;

			this.$nextTick(function () {
				this.$el.querySelector('#searchInput').focus();
			});
		},

		inputBlur: function inputBlur() {
			if (!this.keyWords.length) {
				this.isFocusing = false;
			}
		},

		clearInput: function clearInput() {
			this.keyWords = '';
		},

		cancelSearch: function cancelSearch() {
			this.keyWords = '';
			this.isFocusing = false;
			if (this.projects.length === 0 && this.hotProjects.length === 0) {
				window.location.reload();
			}
		},

		search: function search() {
			//通知projectBox
			bus.$emit('loadProject', this.keyWords);
		}
	}
});

/**
 * [loadProject description]
 * @fileOverview 加载普通project列表
 * @param        {[int]}   pageNum [页码]
 */
function loadProject() {
	if (this.noMore) {
		return;
	}

	var url = urlSearchProject + '?pageNum=' + this.pageNum + '&pageSize=' + this.pageSize + '&keyWords=' + this.keyWords + '&userid=' + this.user.id;

	this.busy = true;
	this.isLoading = true;
	var self = this;

	fetch(url, {
		method: 'GET',
		headers: {
			'Accept': 'application/json'
		}
	}).then(function (response) {
		return response.json();
	}).then(function (data) {
		self.isLoading = false;

		if (self.pageNum === 0) {
			self.projects = data.projects;
		} else {
			self.projects = self.projects.concat(data.projects);
		}

		if (data.hasMore === false) {
			self.noMore = true;
		}

		self.pageNum++;
		self.busy = false;
	});
}

/**
 * [loadHotProject description]
 * @fileOverview 加载Hot-project列表
 */
function loadHotProject() {
	var url = urlSearchHotProject + '?hotSize=' + this.hotSize + '&keyWords=' + this.keyWords + '&userid=' + this.user.id;

	var self = this;

	fetch(url, {
		method: 'GET',
		headers: {
			'Accept': 'application/json'
		}
	}).then(function (response) {
		return response.json();
	}).then(function (data) {
		data.projects.map(function (el, index) {
			el.isHot = true;
			return el;
		});
		self.hotProjects = data.projects;
	});
}
/**
 * @fileOverview for postProject.jsp
 * @Author       adoug
 * @DateTime     2016-11-30
 */

'use strict';

var Promise = require('node_modules/es6-promise/dist/es6-promise').Promise;
require('node_modules/whatwg-fetch/fetch');

var tools = require('static/js/module/tools');

var urlPostProject = urlPrefix + '/fn/project/post';

Vue.component('tic-tag-box', {
	template: '#tic-tag',

	data: function data() {
		return {
			tags: ['Web', '安卓', 'iOS', '硬件', '其他'],
			showTag: true,
			selected: [],
			tagAdded: '',
			isShowTagAdd: false,
			unfold: true
		};
	},

	computed: {
		tagsSelected: function tagsSelected() {
			return this.selected.join('&');
		}
	},

	methods: {
		switchTag: function switchTag() {
			this.showTag = !this.showTag;
			this.unfold = !this.unfold;
		},

		isSelected: function isSelected(tag) {
			return this.selected.indexOf(tag) > -1;
		},

		tagTap: function tagTap(params) {
			var event = params.event,
			    target = event.target;

			var tag = target.dataset.tag;

			if (this.selected.indexOf(tag) > -1) {
				var index = this.selected.indexOf(tag);

				this.selected.splice(index, 1);
			} else {
				this.selected.push(tag);
			}
		},

		showTagAdd: function showTagAdd() {
			this.isShowTagAdd = true;
		},

		hideTagAdd: function hideTagAdd() {
			this.isShowTagAdd = false;
		},

		addTag: function addTag() {
			this.tags.push(this.tagAdded);
			this.selected.push(this.tagAdded);
			this.isShowTagAdd = false;
			this.tagAdded = '';
		}
	}
});

var appPostProject = new Vue({
	el: '#appPostProject',

	data: {
		postIsFail: false,
		postIsSucc: false
	},

	methods: {
		postProject: function postProject() {
			self = this;

			fetch(urlPostProject, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
				},
				credentials: 'same-origin',
				body: tools.formSerialize('#formPostProject')
			}).then(function (response) {
				return response.json();
			}).then(function (data) {
				if (data.code === 'ok') {
					self.postIsSucc = true;
					window.location.href = '/xdtic/myProject?type=post';
				} else {
					self.postIsFail = true;
				}
			})['catch'](function (error) {
				self.postIsFail = true;
				console.log('request failed', error);
			});
		},

		navBack: function navBack() {
			window.history.go(-1);
		},

		closeDialog: function closeDialog() {
			this.postIsFail = false;
		}
	}
});
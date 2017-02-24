/**
 * @fileOverview for /project/check.jsp
 * @Author       adoug
 * @DateTime     2017-2-23
 */

'use strict';

var projectBox = new Vue({
	el: '#app',
	data: {
		sidePush: false,

		placeholder: '随心所搜...'
	},

	methods: {
		push: function push() {
			this.sidePush = !this.sidePush;
		}
	}
});
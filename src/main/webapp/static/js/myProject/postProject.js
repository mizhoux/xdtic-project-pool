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
            var tagSelected = this.selected.join('&') || '';
            this.$emit('tagchange', tagSelected);
            return tagSelected;
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
        postIsSucc: false,

        title: '',
        promassage: '',
        prowant: '',
        concat: '',
        tagSelected: '',

        validRule: {
            tagSelected: [{
                pattern: 'required',
                errorMsg: '请至少选择一个标签'
            }],
            title: [{
                pattern: /^.{2,}$/,
                errorMsg: '项目名称至少2个字'
            }],
            promassage: [{
                pattern: /^(.|\n|\t){10,}$/m,
                errorMsg: '项目详情至少10个字'
            }],
            prowant: [{
                pattern: /^(.|\n|\t){6,}$/m,
                errorMsg: '招聘详情至少6个字'
            }],
            concat: [{
                pattern: 'required',
                errorMsg: '联系方式没有填'
            }]
        }
    },

    computed: {
        errorMsg: function errorMsg() {
            var errorMsg = '';
            var result = undefined;

            // 这个bug啊，Object.entries导致this.validRule变化时不能响应
            // for (let filedRule of Object.entries(this.validRule))
            for (var filedName in this.validRule) {
                if (this.validRule.hasOwnProperty(filedName)) {
                    result = this.validRule[filedName].every(function (rule) {
                        if (rule.invalid) {
                            errorMsg = rule.errorMsg;
                            return false;
                        }

                        return true;
                    });
                }

                if (!result) break;
            }

            return errorMsg;
        },

        hasError: function hasError() {
            var result = true;
            for (var filedName in this.validRule) {
                if (this.validRule.hasOwnProperty(filedName)) {
                    result = result && this.validRule[filedName].every(function (rule) {
                        return !rule.invalid;
                    });
                }
            }
            return !result;
        }
    },

    created: function created() {
        var _this = this;

        //init validRule
        var self = this;

        var _loop = function (filedName) {
            if (_this.validRule.hasOwnProperty(filedName)) {
                _this.validRule[filedName].forEach(function (rule, index) {
                    self.$set(rule, 'filed', filedName);
                    self.$set(rule, 'index', index);
                    self.$set(rule, 'invalid', false);
                });
            }
        };

        for (var filedName in this.validRule) {
            _loop(filedName);
        }
    },

    methods: {
        postProject: function postProject() {
            var self = this;

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
                    setTimeout(function () {
                        window.location.href = urlPrefix + '/myProject?type=post';
                    }, 500);
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
        },

        updateTag: function updateTag(tagSelected) {
            this.tagSelected = tagSelected;
        },

        /**
         * syn valid this.validRule
         */
        validForm: function validForm() {
            var fileds = [];
            for (var filed in this.validRule) {
                if (this.validRule.hasOwnProperty(filed)) {
                    fileds.push(filed);
                }
            }

            var sequence = Promise.resolve();
            /*fileds.forEach(function (filed) {
                sequence = sequence.then(() => {
                    return new Promise(appPostProject._validFiledGen(filed));
                }).then(result => {
                    if (result.code === 'invalid') {
                        throw new Error('Big Error');
                    }
                });
            });
              sequence.then(() => {
                appPostProject.postProject();
            }).catch(error => {
                console.log('form invalid');
            });*/

            var _loop2 = function (i, len) {
                (function (index) {
                    if (index === len) {
                        sequence.then(function () {
                            appPostProject.postProject();
                        })['catch'](function (error) {
                            console.log('form invalid');
                        });
                    } else {
                        sequence = sequence.then(function () {
                            return new Promise(appPostProject._validFiledGen(fileds[index]));
                        }).then(function (result) {
                            if (result.code === 'invalid') {
                                throw new Error('Big Error');
                            }
                        });
                    }
                })(i);
            };

            for (var i = 0, len = fileds.length; i <= len; i++) {
                _loop2(i, len);
            }
        },

        /**
         * 生成用于field验证的Promise的构造函数，其中filed字段可以自行指定
         * @param filed [String] 表单字段：username, password...
         * @returns {Function}
         */
        _validFiledGen: function _validFiledGen(filed) {
            return function (resolve, reject) {
                var filedPromiseResolve = resolve;

                var filedRule = appPostProject.validRule[filed];

                var sequence = Promise.resolve();

                // execute rule-valid asyn
                /*filedRule.forEach(function (rule) {
                    sequence = sequence.then(() => {
                        return [rule].map(rule2Promise)[0];
                    }).then(result => {
                        let filed = result.rule.filed;
                        let index = result.rule.index;
                        if (result.code === 'invalid') {
                            appPostProject.validRule[filed][index].invalid = true;
                            throw new Error('Big Error');
                        } else if (result.code === 'valid') {
                            appPostProject.validRule[filed][index].invalid = false;
                            return;
                        }
                    });
                });
                  sequence.catch(error => {
                    return {code: 'invalid'};
                })
                    .then(result => {
                        if (result.code === 'invalid') {
                            filedPromiseResolve({code: 'invalid'});
                        } else {
                            filedPromiseResolve({code: 'valid'});
                        }
                    });*/

                var _loop3 = function (i, len) {
                    (function (index) {
                        if (index == len) {
                            sequence['catch'](function (error) {
                                return { code: 'invalid' };
                            }).then(function (result) {
                                if (result.code === 'invalid') {
                                    filedPromiseResolve({ code: 'invalid' });
                                } else {
                                    filedPromiseResolve({ code: 'valid' });
                                }
                            });
                        } else {
                            sequence = sequence.then(function () {
                                return [filedRule[index]].map(rule2Promise)[0];
                            }).then(function (result) {
                                var filed = result.rule.filed;
                                var index = result.rule.index;
                                if (result.code === 'invalid') {
                                    appPostProject.validRule[filed][index].invalid = true;
                                    throw new Error('Big Error');
                                } else if (result.code === 'valid') {
                                    appPostProject.validRule[filed][index].invalid = false;
                                    return { code: 'valid' };
                                }
                            });
                        }
                    })(i);
                };

                for (var i = 0, len = filedRule.length; i <= len; i++) {
                    _loop3(i, len);
                }
            };
        },

        _validPattern: function _validPattern(rule) {
            if (rule.pattern === 'required') {
                return this[rule.filed].length > 0;
            } else if (rule.pattern instanceof RegExp) {
                return this[rule.filed].search(rule.pattern) > -1;
            } else if (rule.pattern instanceof Function) {
                return rule.pattern.call(this);
            }
        },

        _rule2Promise: rule2Promise
    }
});

/**
 * 将rule.pattern转换为Promise
 * 该函数作为Array.prototype.map的参数
 * Unfortunately，若rule.pattern是异步的，需要在rule里标记（即属性isAsyn为true）
 * @param rule
 * @returns {Promise}
 */
function rule2Promise(rule) {
    if (rule.pattern instanceof Function && rule.isAsyn) {
        return new Promise(asynPromiseRuleConstructorGen(rule));
    } else {
        return new Promise(function (resolve) {
            if (!appPostProject._validPattern(rule)) {
                resolve({ code: 'invalid', rule: rule });
            } else {
                resolve({ code: 'valid', rule: rule });
            }
        });
    }
}

/**
 * 将rule.pattern转换为Promise的constructor, 其中rule可指定
 * @param rule
 * @returns {Function}
 */
function asynPromiseRuleConstructorGen(rule) {
    return function (resolve, reject) {
        rule.pattern.call(formRegister, resolve, reject, rule);
    };
}
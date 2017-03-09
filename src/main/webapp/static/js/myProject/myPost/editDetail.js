/**
 * @fileOverview for editDetail.jsp
 * @Author       adoug
 * @DateTime     2016-12-03
 */

'use strict';

window.addEventListener('load', function () {
    'use strict';

    var Promise = require('node_modules/es6-promise/dist/es6-promise').Promise;
    require('node_modules/whatwg-fetch/fetch');

    var tools = require('static/js/module/tools');

    var urlUpdateProject = urlPrefix + '/fn/project/update';

    var appEditDetail = new Vue({
        el: '#appEditDetail',

        data: {
            user: userInfo,
            project: projectInfo,

            promassage: projectInfo.promassage,
            prowant: projectInfo.prowant,
            concat: projectInfo.concat,
            validRule: {
                promassage: [{
                    pattern: /^.{10,}$/,
                    errorMsg: '项目详情至少10个字'
                }],
                prowant: [{
                    pattern: /^.{6,}$/,
                    errorMsg: '招聘详情至少6个字'
                }],
                concat: [{
                    pattern: 'required',
                    errorMsg: '联系方式没有填'
                }]
            },

            editIsFail: false,
            editIsSucc: false
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
            editDetail: function editDetail() {
                var self = this;

                fetch(urlUpdateProject, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8',
                        'Accept': 'application/json'
                    },
                    credentials: 'same-origin',
                    body: tools.formSerialize('#formEditDetail')
                }).then(function (response) {
                    return response.json();
                }).then(function (data) {
                    if (data.code === 'ok') {
                        self.editIsSucc = true;
                        setTimeout(function () {
                            window.location.href = urlPrefix + '/myProject/myPost/detail?proId=' + self.project.proId + '&uid=' + self.user.id;
                        }, 500);
                    } else {
                        self.editIsFail = true;
                    }
                })['catch'](function (error) {
                    self.editIsFail = true;
                    console.log('request failed', error);
                });
            },

            closeDialog: function closeDialog() {
                this.editIsFail = false;
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

                var _loop2 = function (i, len) {
                    (function (index) {
                        if (index === len) {
                            sequence.then(function () {
                                appEditDetail.editDetail();
                            })['catch'](function (error) {
                                console.log('form invalid');
                            });
                        } else {
                            sequence = sequence.then(function () {
                                return new Promise(appEditDetail._validFiledGen(fileds[index]));
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

                    var filedRule = appEditDetail.validRule[filed];

                    var sequence = Promise.resolve();

                    // execute rule-valid asyn

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
                                        appEditDetail.validRule[filed][index].invalid = true;
                                        throw new Error('Big Error');
                                    } else if (result.code === 'valid') {
                                        appEditDetail.validRule[filed][index].invalid = false;
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
                if (!appEditDetail._validPattern(rule)) {
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
});
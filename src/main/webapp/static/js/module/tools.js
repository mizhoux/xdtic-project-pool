define('static/js/module/tools', function(require, exports, module) {

  /**
   * @fileOverview tools
   * @Author       adoug
   * @DateTime     2016-11-24
   */
  
  "use strict";
  
  function serialize(form) {
      if (typeof form === 'string') {
          form = document.querySelector(form);
      }
  
      var len = form.elements.length; //表单字段长度;表单字段包括<input><select><button>等
      var field = null; //用来存储每一条表单字段
      var parts = []; //保存字符串将要创建的各个部分
      var opLen, //select中option的个数
      opValue; //select中option的值
      //遍历每一个表单字段
      var i, j;
      var option;
      for (i = 0; i < len; i++) {
          field = form.elements[i];
          switch (field.type) {
              case "select-one":
              case "select-multiple":
                  if (field.name.length) {
                      for (j = 0, opLen = filed.options.length; j < opLen; j++) {
                          option = field.options[j];
                          if (option.selected) {
                              opValue = '';
                              if (option.hasAttribute) {
                                  opValue = option.hasAttribute('value') ? option.value : option.text;
                              } else {
                                  opValue = option.hasAttribute['value'].specified ? option.value : option.text; //IE下
                              }
                              parts.push(encodeURIComponent(field.name) + '=' + encodeURIComponent(opValue));
                          }
                      }
                  }
                  break;
              case undefined:
              case "file":
              case "submit":
              case "reset":
              case "button":
                  break;
              case "radio":
              case "checkbox":
                  if (!field.checked) {
                      break;
                  }
              default:
                  if (field.name.length) {
                      opValue = field.value;
                      parts.push(encodeURIComponent(field.name) + '=' + encodeURIComponent(opValue.trim()));
                  }
                  break;
          }
      }
      return parts.join("&");
  }
  
  function SaferHTML(templateData) {
      var s = templateData[0];
      for (var i = 1; i < arguments.length; i++) {
          var arg = String(arguments[i]);
  
          // 转义占位符中的特殊字符。
          s += arg.replace(/&/g, "&").replace(/</g, "<").replace(/</g, ">");
  
          // 不转义模板中的特殊字符。
          s += templateData[i];
      }
      return s;
  }
  
  /**
   * @fileOverview 获取url中GET类型的参数
   * @param        {[String]}   url 
   * @return       {[Object]}   params
   */
  function getParams(url) {
      var urlQuery = url.split('?')[1];
      var params = {};
  
      var parts = urlQuery.split('&');
      for (var i = 0, len = parts.length; i < len; i++) {
          var part = parts[i].split('=');
  
          params[part[0]] = part[1];
      }
  
      return params;
  }
  
  /**
   * @fileOverview 页面跳转后用户点击后退按钮时留在本页（注册后跳到登录，这时候点击后退）
   * @param        {[RegExp]}   pattern
   */
  function avoidBack(pattern) {
      if (document.referrer.search(pattern) > -1) {
          for (var i = 0; i < 5; i++) {
              window.history.pushState(null, document.title);
          }
      }
  }
  
  function obj2form(obj) {
      var form = [];
  
      for (var key in obj) {
          if (obj.hasOwnProperty(key)) {
              form.push(encodeURIComponent(key) + "=" + encodeURIComponent(obj[key]));
          }
      }
  
      return form.join('&');
  }
  
  /**
   * 比较两个对象的属性差异，以origin为主，输出差异的key-value
   * @param origin: Object 原始对象
   * @param modifier: Object 变更后的对象
   * @returns diff: Object
   */
  function diffObj(origin, modifier) {
      var diff = {};
  
      for (var key in origin) {
          if (origin.hasOwnProperty(key)) {
              if (origin[key] !== modifier[key]) {
                  diff[key] = modifier[key];
              }
          }
      }
  
      return diff;
  }
  
  function isEmpty(obj) {
      for (var key in obj) {
          if (obj.hasOwnProperty(key)) {
              return false;
          }
      }
  
      return true;
  }
  
  function copyObj(obj) {
      var result = {};
  
      for (var key in obj) {
          if (obj.hasOwnProperty(key)) {
              result[key] = obj[key];
          }
      }
  
      return result;
  }
  
  module.exports = {
      formSerialize: serialize,
      SaferHTML: SaferHTML,
      getParams: getParams,
      avoidBack: avoidBack,
      obj2form: obj2form,
      diffObj: diffObj,
      isEmpty: isEmpty,
      copyObj: copyObj
  };

});

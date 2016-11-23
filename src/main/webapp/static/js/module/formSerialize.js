define('static/js/module/formSerialize', function(require, exports, module) {

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
                      parts.push(encodeURIComponent(field.name) + '=' + encodeURIComponent(opValue));
                  }
                  break;
          }
      }
      return parts.join("&");
  }
  
  module.exports = serialize;

});

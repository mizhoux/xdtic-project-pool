define("static/js/module/formSerialize",function(e,t,o){"use strict";function n(e){"string"==typeof e&&(e=document.querySelector(e));var t,o,n,i,s=e.elements.length,a=null,c=[];for(n=0;s>n;n++)switch(a=e.elements[n],a.type){case"select-one":case"select-multiple":if(a.name.length)for(i=0,t=filed.options.length;t>i;i++)option=a.options[i],option.selected&&(o="",o=option.hasAttribute?option.hasAttribute("value")?option.value:option.text:option.hasAttribute.value.specified?option.value:option.text,c.push(encodeURIComponent(a.name)+"="+encodeURIComponent(o)));break;case void 0:case"file":case"submit":case"reset":case"button":break;case"radio":case"checkbox":if(!a.checked)break;default:a.name.length&&(o=a.value,c.push(encodeURIComponent(a.name)+"="+encodeURIComponent(o)))}return c.join("&")}o.exports=n});
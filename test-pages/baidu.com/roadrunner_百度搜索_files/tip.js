define("ecom/common/api/tip06211930/control",[],function(){var t=baidu,e=t.event;t.dom.getAncestorByClass=t.dom.getAncestorByClass||function(t,e){for(t=baidu.dom.g(t),e=new RegExp("(^|\\s)"+baidu.string.trim(e)+"(\\s|$)");(t=t.parentNode)&&1===t.nodeType;)if(e.test(t.className))return t;return null};var i=e._eventFilter=e._eventFilter||{};i._crossElementBoundary=i._crossElementBoundary||function(t,e){var i=e.relatedTarget,n=e.currentTarget;if(i!==!1&&n!==i&&(!i||"xul"!==i.prefix&&!baidu.dom.contains(n,i)))return t.call(n,e)},t.fn.bind=t.fn.bind||function(t,e){var i=arguments.length>2?[].slice.call(arguments,2):null;return function(){var n=baidu.lang.isString(t)?e[t]:t,s=i?i.concat([].slice.call(arguments,0)):arguments;return n.apply(e||n,s)}},i.mouseenter=window.attachEvent?null:i.mouseenter||function(t,e,n){return{type:"mouseover",listener:baidu.fn.bind(i._crossElementBoundary,this,n)}},i.mouseleave=window.attachEvent?null:i.mouseleave||function(t,i,n){return{type:"mouseout",listener:baidu.fn.bind(e._eventFilter._crossElementBoundary,this,n)}},t.page.getScrollLeft=t.page.getScrollLeft||function(){var t=document;return window.pageXOffset||t.documentElement.scrollLeft||t.body.scrollLeft};var n=function(t,e){for(var i=-1,n=0,s=t.length;s>n;n++)if(t[n]===e){i=n;break}return i};t.array.indexOf||(t.array.indexOf=n);var s=function(){this.children=[],this._listners={},this.fire("beforeinit"),this.bindEvents(this.binds),this.init.apply(this,arguments),this.fire("afterinit")};return s.prototype={constructor:s,type:"Control",disabled:!1,setOptions:function(e){if(!e)return this.options;var i=t.object,n=this.options=i.clone(this.options),s=/^on[A-Z]/,r=this,o=i.extend;return this.srcOptions=e,i.each(e,function(t,a){if(s.test(a)&&"function"==typeof t){var h=a.charAt(2).toLowerCase()+a.substr(3);r.on(h,t),delete e[a]}else a in n&&(n[a]=i.isPlain(t)?o(n[a]||{},t):t)}),n},bindEvents:function(e){var i=this;e&&e.length&&("string"==typeof e&&(e=e.split(/\s*,\s*/)),t.each(e,function(t,e){e=t&&i[t],e&&(i[t]=function(){return e.apply(i,arguments)})}))},init:function(){throw new Error("not implement init")},render:function(){throw new Error("not implement render")},appendTo:function(t){this.main=t||this.main,this.render()},disable:function(){this.disabled=!0,this.fireEvent("disable")},enable:function(){this.disabled=!1,this.fireEvent("enable")},isDisabled:function(){return this.disabled},addChild:function(t,e){var i=this.children;e=e||t.childName,e&&(i[nane]=t),i.push(t)},removeChild:function(e){t.object.each(this.children,function(t,i){t===e&&delete this[i]})},getChild:function(t){return this.children[t]},initChildren:function(){throw new Error("not implement initChildren")},on:function(e,i){t.isString(e)||(i=e,e="*");var s=this._listners[e]||[];return n(s,i)<0&&(i.$type=e,s.push(i)),this._listners[e]=s,this},un:function(e,i){t.isString(e)||(i=e,e="*");var s=this._listners[e];if(s)if(i){var r=n(s,i);~r&&delete s[r]}else s.length=0,delete this._listners[e];return this},fire:function(e,i){var n=this._listners[e];return n&&t.array.each(n,function(t){i=i||{},i.type=e,t.call(this,i)},this),"*"!==e&&this.fire("*",i),this},dispose:function(){this.fire("dispose");for(var t;t=this.children.pop();)t.dispose();for(var e in this._listners)this.un(e)}},s}),define("ecom/common/api/tip06211930/tip",["require","./control"],function(t){var e=baidu,i=e.dom,n=e.page,s=t("./control"),r=function(t,n){var s=e.event.getTarget(t);return i.hasClass(s,n)||(s=i.getAncestorByClass(s,n))?s:null},o=function(){this.constructor.superClass.constructor.apply(this,arguments)};return o.HIDE_DELAY=500,o.prototype={type:"Tip",options:{disabled:!1,main:"",arrow:!1,hideDelay:0,mode:"over",title:null,content:"",prefix:"ecl-ui-tip",triggers:"tooltips",flag:"_ecui_tips",offset:{x:0,y:0},tpl:'<div class="{prefix}-arrow {prefix}-arrow-top"><em></em><ins></ins></div><div class="{prefix}-title"></div><div class="{prefix}-body"></div>'},binds:"onResize, onShow, onHide, hide",init:function(t){t=this.setOptions(t),t.hideDelay=t.hideDelay||o.HIDE_DELAY,this.disabled=t.disabled,this.title=t.title,this.content=t.content;var e=t.prefix,i=this.main=document.createElement("div");i.className=e,i.innerHTML=t.tpl.replace(/{prefix}/g,e),i.style.left="-2000px",this.events={over:{on:"mouseenter",un:"mouseleave"},click:{on:"click",un:"click"}}[t.mode]},render:function(){var t=this,i=this.main,n=this.options,s=this.events;if(!this.rendered){this.rendered=!0,document.body.appendChild(i),e.on(i,"click",function(e){t.fire("click",{event:e})}),e.on(i,"mouseenter",function(){t.clear()}),e.on(i,"mouseleave",function(){t.clear(),t.timer=setTimeout(t.hide,n.hideDelay)});var r=this.elements={},o=n.prefix+"-";e.each("arrow,title,body".split(","),function(t){r[t]=e.q(o+t,i)[0]}),this.addTriggers(n.triggers)}return!s&&this.triggers&&this.show(this.triggers[0]),this},addTriggers:function(t){var i=this,n=this.options,s=this.events,r=n.flag;this.triggers="string"==typeof t?e.q(n.triggers):t.length?t:[t],s&&e.each(this.triggers,function(t){e.addClass(t,r),e.on(t,s.on,i.onShow),e.on(t,s.un,i.onHide)})},clear:function(){clearTimeout(this.timer),clearTimeout(this.resizeTimer)},onResize:function(){clearTimeout(this.resizeTimer);var t=this;this.resizeTimer=setTimeout(function(){t.show(t.current)},100)},onShow:function(t){var e=r(t,this.options.flag);this.clear(),e&&this.current!==e&&(this.current=e,this.fire("beforeShow",{target:e,event:t}),this.show(e))},onHide:function(){var t=this.options;this.clear(),t.hideDelay?this.timer=setTimeout(this.hide,t.hideDelay):this.hide()},show:function(t){var i=this.options,n=this.events,s=this.elements;this.clear(),this.current=t,n&&t&&e.on(t,n.un,this.onHide),e.on(window,"resize",this.onResize),s.title.innerHTML=this.title||"",s.body.innerHTML=this.content,e[this.title?"show":"hide"](s.title),i.arrow||e.hide(s.arrow),this.computePosition(),this.fire("show",{target:t})},hide:function(){var t=this.main;this.clear();var n=this.elements.arrow;i.setStyle(t,"left",-t.offsetWidth-n.offsetWidth),this.current=null,e.un(window,"resize",this.onResize),this.fire("hide")},isVisible:function(){return!!this.current},setTitle:function(t){this.title=t||"";var i=this.elements;i.title.innerHTML=this.title,e[this.title?"show":"hide"](i.title)},setContent:function(t){this.content=t||"",this.elements.body.innerHTML=this.content},computePosition:function(){var t=this.options,e=this.current,s=this.main,r=this.elements.arrow,o=t.arrow,a=i.getPosition(e),h=t.prefix+"-arrow",l=a.top,u=a.left,c=e.offsetWidth,d=e.offsetHeight,f=u+c,p=l+d,g=u+c/2,m=l+d/2,v=s.offsetWidth,b=s.offsetHeight,y=r.firstChild.offsetWidth,w=r.firstChild.offsetHeight,C=n.getScrollTop(),T=n.getScrollLeft(),x=T+n.getViewWidth(),k=C+n.getViewHeight(),E=e.getAttribute("data-tooltips");E&&(o=/[trblc]{2}/.test(E)?E:"1");var S,_;if(o&&"1"!==o)_=o.charAt(0),S=o.charAt(1);else{var A=c>v||u-(v-c)/2>0&&x>=f+(v-c)/2?"c":u+v>x?"r":"l",L=d>b||l-(b-d)/2>0&&k>=p+(b-d)/2?"c":l+b>k?"b":"t";k>=p+w+b?(_="b",S=A):x>=f+v+y?(_="r",S=L):l-b-w>=C?(_="t",S=A):u-v-y>=T&&(_="l",S=L),o=_+S}var M={l:"left",r:"right",t:"top",b:"bottom"},N=t.offset;r.className=h+" "+h+"-"+M[_],y=r.firstChild.offsetWidth,w=r.firstChild.offsetHeight;var O=(v-y)/2,H=(b-w)/2;({t:1,b:1})[_]?(u={l:u,c:g-v/2,r:f-v}[S],l={t:l-w-b-N.y,b:p+w+N.y}[_],i.setStyle(r,"left",{c:O,l:(c-y)/2,r:v-(c-y)/2}[c>v?"c":S]),i.setStyle(r,"top","")):{l:1,r:1}[_]&&(l={t:l,c:m-b/2,b:p-b}[S],u={l:u-y-v-N.x,r:f+y+N.x}[_],i.setStyle(r,"top",{c:H,t:(d-w)/2,b:b-(d-w)/2}[d>b?"c":S]),i.setStyle(r,"left","")),i.setStyles(s,{left:u,top:l})}},e.inherits(o,s),o});
//@ sourceMappingURL=tip.js.map
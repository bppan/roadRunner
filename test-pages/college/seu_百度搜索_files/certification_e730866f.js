bds.se.certification=bds.se.certification||{};bds.se.certification.build=(function(){var e=[".certification{background:url('http://s1.bdstatic.com/r/www/cache/global/img/commonHintIcon-1.1.png') no-repeat;cursor:pointer;vertical-align:text-bottom;height:16px;width:16px;margin:0 3px;overflow:hidden;display:inline-block;}",".hint_arrow{position: absolute;top:-1px;*top:0;left:-2px;z-index:1;}",".rzcont .arrowout,.rzcont .arrowin{color:#ccc;position:absolute;top:3px;left:5px;font-size:12px;font-family:Simsun;font-weight:normal;display:block;z-index:1;}",".rzcont .arrowin{color:#fff;margin-top:1px;}",".rzcont strong{display:block;}",".rzcont .hinticon{height:22px;margin-right:6px;}",".hitcon{font-size:12px;line-height:22px;color:#575757;min-width:230px;_width:230px;position:absolute;border:1px solid #CCC;padding:3px 10px 5px;background:#fff;top:9px;left:0;box-shadow:1px 1px 2px #ccc;-moz-box-shadow:1px 1px 2px #ccc;-webkit-box-shadow:1px 1px 2px #ccc;filter:progid:DXImageTransform.Microsoft.Shadow(Strength=2,Direction=135,Color='#cccccc')\9;white-space:nowrap;}","#hitcon{position:absolute;}",".rzcont img{vertical-align:middle;}",".rzcont a{font-family:simsun;_font-family:arial;}"];
bds.util.addStyle(e.join(""));var d,g,c;function f(){var h=baidu.q("certification");for(var j=0;j<h.length;j++){h[j].onmouseover=function(){clearTimeout(c);var m=baidu.dom.getPosition(this);var l=this.getAttribute("certification_key");var k=this.getAttribute("dis_url").replace(/(^\s*)|(\s*$)/g,"");var i=bds.se.certification.data[l];if(!G("hintcont")){b(i,m,k)}else{a(i,m,k)}g=setTimeout(function(){d.style.display="inline-block";var p="";var n=i[0].templateName;for(var o=0;o<i.length;o++){p+=i[o].id;if(o!=i.length-1){p+=","}}ns_c({hintUrl:k,hintId:p,hintTpl:n})},100)};h[j].onmouseout=function(){clearTimeout(g);c=setTimeout(function(){G("hintcont").style.display="none"},100)}}}function b(i,j,h){d=document.createElement("div");d.id="hintcont";d.className="rzcont";d.style.display="none";d.style.position="absolute";d.style.zIndex="999";document.body.appendChild(d);a(i,j,h);d.onmouseover=function(){clearTimeout(c)};d.onmouseout=function(){c=setTimeout(function(){G("hintcont").style.display="none"
},100)}}function a(j,n,h){d.style.left=n.left+"px";d.style.top=n.top+17+"px";var m="";m='<div class="hint_arrow"><span class="arrowout">◆</span><em class="arrowin">◆</em></div><div class="hitcon" ><strong>'+j[0].hintData.hintLabel+"</strong>";for(var k=0;k<j.length;k++){m+="<p>";if(j[k].hintData.hintIcon){m+="<img src='"+j[k].hintData.hintIcon+"' class='hinticon' />"}m+=j[k].hintData.hintText;m+="</p>"}m+="</div>";d.innerHTML=m;var l=d.getElementsByTagName("p");baidu.each(l,function(q,p){var o=q.getElementsByTagName("A");baidu.each(o,function(r,i){baidu.on(r,"mousedown",function(){ns_c({hintUrl:h,hintId:j[p].id,hintTpl:j[p].templateName,title:r.innerHTML,pos:i})})})})}return{init:f}})();bds.se.certification.build.init();
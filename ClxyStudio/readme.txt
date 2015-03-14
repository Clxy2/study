Use standard j2ee
- jsf 2 : myfaces
- cdi : weld
- JPA : gae

on gae


1. jsf 引用css和javascript等有两种方式。
 - jsf式 h:outputStylesheet h:outputScript
	生成类似 /contextPath/faces/javax.faces.resource/your css or js path
 - 传统式 href="#{request.contextPath}/your css or js
	生成类似 /contextPath/your css or js path
当出现css中使用image时，要当心。

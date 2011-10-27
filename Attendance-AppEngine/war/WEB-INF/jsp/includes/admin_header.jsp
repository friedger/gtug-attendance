<%@ include file="include.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <META http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title><c:out value="${param.pageTitle}"/></title>

    <link rel="icon" type="image/png" href="/static/favicon.png"></link>
    <link rel="stylesheet" type="text/css" href="/static/admin.css"></link>
    <link rel="stylesheet" type="text/css" href="/static/tabber.css" ></link>
    <link rel="stylesheet" type="text/css" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/themes/ui-lightness/jquery-ui.css"></link>

    <script type="text/javascript" src="/static/core.js"></script>

    <script type="text/javascript">
      var tabberOptions = {manualStartup: true};
    </script>
    <script type="text/javascript" src="/static/tabber-minimized.js"></script>

    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.2/jquery-ui.min.js"></script>
  </head>
  <body>
    <div id="header">
      <div style="float:left; width:85px; margin-right:10px">
        <img src="/static/gtug_logo_small.jpg"/>
      </div>
      <div style="float:left; width:auto; line-height:35px">
        <ul>
          <li><b>&rarr;</b> <a href="/">About</a></li>
          <li><b>&rarr;</b> <a href="/directory.jsp">Directory</a></li>
          <li><b>&rarr;</b> <a href="/pulse.jsp">Pulse</a></li>
          <li><b>&rarr;</b> <a href="http://wiki.gtugs.org">Wiki</a></li>
          <li><b>&rarr;</b> <a href="/admin.jsp">Admin</a></li>
        </ul>
      </div>
      <div style="float:right; width:auto; line-height:35px">
        <c:choose>
          <c:when test="${model.userService.user==null}">
            <b>&rarr;</b>&nbsp;<a href="<c:out value="${model.userService.loginUrl}"/>">Sign in</a>
          </c:when>
          <c:otherwise>
            Welcome, <b><c:out value="${model.userService.user.name}"/></b> |
            <b>&rarr;</b>&nbsp;<a href="<c:out value="${model.userService.logoutUrl}"/>">Sign out</a>
          </c:otherwise>
        </c:choose>
      </div>
      <br style="clear:both"/>
    </div>
    <div id="content">
<%@ include file="include.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <META http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>Google Technology User Groups: <c:out value="${param.pageTitle}"/></title>

    <link rel="icon" type="image/png" href="/static/favicon.png"></link>
    <link rel="stylesheet" type="text/css" href="/static/core.css"></link>
    <link rel="stylesheet" type="text/css" href="/static/tabber.css" ></link>

    <script type="text/javascript" src="/static/core.js"></script>
    <script type="text/javascript" src="/static/tabber-minimized.js"></script>

    <!-- Load the Google AJAX API Loader -->
    <script type="text/javascript" src="http://www.google.com/jsapi"></script>

    <!-- Load the Google Friend Connect JavaScript library -->
    <script type="text/javascript">
      google.load('friendconnect', '0.8');
    </script>

    <!-- Initialize the Google Friend Connect OpenSocial API -->
    <script type="text/javascript">
      google.friendconnect.container.setParentUrl('/');
      google.friendconnect.container.initOpenSocialApi({
        site: '15952876702501341768',
        onload: function(token) {}
      });
    </script>
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
      <%--
      <div style="float:right; width:auto; line-height:35px">
        <c:choose>
          <c:when test="${model.state.user==null}">
            <b>&rarr;</b>&nbsp;<a href="#" onclick="google.friendconnect.requestSignIn()">Sign in</a>
          </c:when>
          <c:otherwise>
            Welcome, <b><c:out value="${model.state.user.name}"/></b> |
            <b>&rarr;</b>&nbsp;<a href="#" onclick="google.friendconnect.requestSignOut()">Sign out</a>
          </c:otherwise>
        </c:choose>
      </div>
      --%>
      <br style="clear:both"/>
    </div>
    <div id="content">
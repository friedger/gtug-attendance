<%@ include file="includes/include.jsp" %>

<jsp:include page="includes/header.jsp">
  <jsp:param name="pageTitle" value="Error" />
</jsp:include>

<style type="text/css">
  h2 {
    color: #333333;
  }
</style>

<div id="title">
  <h2>Error</h2>
</div>

<c:out value="${errorText}"/>

<p><a href="/directory.jsp">Return to the directory</a></p>

<%@ include file="includes/footer.jsp" %>
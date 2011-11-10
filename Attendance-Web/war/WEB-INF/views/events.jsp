<%@ include file="includes/include.jsp" %>

<c:import url="includes/header.jsp">
  <c:param name="pageTitle" value="Events"/>
</c:import>

<ul>
<c:forEach items="${events}" var="event">
	<li><a href="events/show/<c:out value="${event.id}"/>"><c:out value="${event.name}"/></a></li>	
</c:forEach>
</ul>

<%@ include file="includes/footer.jsp" %>
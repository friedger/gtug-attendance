<%@ include file="includes/include.jsp"%>

<c:import url="includes/header.jsp">
  <c:param name="pageTitle" value="Imported Events" />
</c:import>

<h1>Imported</h1>

<ul>
  <c:forEach items="${model.newEvents}" var="event">
    <li>
      NEW
      <br/>
      <b><c:out value="${event.name}"/></b> - (<c:out value="${event.startDate}" />)
      <br/>
      <c:out value="${event.description}" escapeXml="false" />
    </li>
  </c:forEach>

  <c:forEach items="${model.updatedEvents}" var="event">
    <li>
      UPDATED
      <br/>
      <b><c:out value="${event.name}"/></b> - (<c:out value="${event.startDate}" />)
      <br/>
      <c:out value="${event.description}" escapeXml="false" />
    </li>
  </c:forEach>
</ul>

<%@ include file="includes/footer.jsp"%>

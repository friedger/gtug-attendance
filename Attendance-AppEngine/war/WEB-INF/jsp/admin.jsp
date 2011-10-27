<%@ include file="includes/include.jsp" %>

<jsp:include page="includes/admin_header.jsp">
  <jsp:param name="pageTitle" value="Admin" />
</jsp:include>

<style type="text/css">
  a {
    text-decoration: none;
  }
  a:hover {
    text-decoration: underline;
  }
  h5 {
    margin: 0px 0px 2px 20px;
  }
  ul {
    list-style: none;
    margin: 0px;
    padding: 0px;
  }
  li {
    margin: 0px 0px 0px 30px;
  }
</style>

<div id="title">
  <h2>Administer</h2>
</div>

<p style="border-bottom:solid silver 1px; font-size:13pt; padding:0px 0px 10px 0px">
  &rarr; <a href="/admin/addCountry.jsp">Add country</a>
</p>

<p style="border-bottom:solid silver 1px; font-size:13pt; padding:0px 0px 10px 0px">
  &rarr; <a href="/admin/addChapter.jsp">Add chapter</a>
</p>

<p>Update chapter:</p>

<p>
<c:forEach items="${model.chapters}" var="country">
  <c:forEach items="${country}" var="chapter" varStatus="chapterRow">
    <c:if test="${chapterRow.index == 0}">
      <h5><c:out value="${chapter.country}"/></h5>
      <ul>
    </c:if>
    <li>
      <c:choose>
        <c:when test="${chapter.status eq 'inactive'}">
          <b>&rarr;</b> <a href="/admin/updateChapter.jsp?id=<c:out value="${chapter.id}"/>" style="color:gray"><c:out value="${chapter.name}"/></a>
          <%--<ul>
            <li><span style="font-size:9pt">&rarr; <a href="/admin/addEvent.jsp?id=<c:out value="${chapter.id}"/>" style="color:gray">add event</a></span></li>
          </ul>--%>
        </c:when>
        <c:otherwise>
          <b>&rarr;</b> <a href="/admin/updateChapter.jsp?id=<c:out value="${chapter.id}"/>"><c:out value="${chapter.name}"/></a>
          <ul>
            <li><span style="font-size:9pt">&rarr; <a href="/admin/addEvent.jsp?id=<c:out value="${chapter.id}"/>">add event</a></span></li>
          </ul>
        </c:otherwise>
      </c:choose>
    </li>
  </c:forEach>
  </ul>
</c:forEach>
</p>

<%@ include file="includes/footer.jsp" %>
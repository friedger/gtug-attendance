<%@ include file="includes/include.jsp" %>

<c:import url="includes/header.jsp">
  <c:param name="pageTitle" value="Events"/>
</c:import>

<h2><c:out value="${event.name}"/></h2>

<input type="button" value="notify all" id="notify"/>

<div id="attendees"></div>

<script id="attendee-template" type="text/x-jquery-tmpl">
	<div class="attendee">
    	<div class="account">${name}</div>
      	<div class="actions">
      		<span class="vibrate">vibrate</span>
			<span class="alert">alert</span>
			<span class="message">message</span>
      	</div>
	</div>
</script>


<script type="text/javascript">

$(function() {

	var eventId='<c:out value="${event.id}"/>';

	$.getJSON('/events/'+eventId, function(data) {
	
		var list = _.map(data.attendees, function(a){ return { name: a}; });
		console.log(list);

		$("#attendee-template").template("attendeeTemplate");	
		
		$.tmpl("attendeeTemplate", list).appendTo("#attendees");
		
	});
	
	$('#notify').click(function() {
	
	
		console.log('clicked');
		$.post('/events/notify', { eventId: eventId },
			function(data) {
				console.log(data);
			});
	
	});

});

</script>

<%@ include file="includes/footer.jsp" %>
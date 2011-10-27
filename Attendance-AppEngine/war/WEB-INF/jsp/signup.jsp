<%@ page pageEncoding="UTF-8"%>

<%@ include file="includes/include.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%--<jsp:include page="includes/header.jsp">
  <jsp:param name="pageTitle" value="<fmt:message key="signup-title"/>" />
</jsp:include>--%>

<c:import url="includes/header.jsp">
  <c:param name="pageTitle">
    Sign up
  </c:param>
</c:import>

<style type="text/css">
  a, h2 {
    color: #0566cc;
  }
  .error {
    color: red;
    font-size: 9pt;
  }
  #gtugs-signupFormTable {
    margin: 15px 0 10px 0;
    width: 100%;
  }
  #gtugs-signupFormTable th {
    font-size: 9pt;
    font-weight: normal;
    text-align: left;
  }
  #gtugs-signupFormTable td {
    padding-right: 15px;
  }
  #gtugs-signupFormTable td input {
    width: 100%;
  }
  #gtugs-signupFormTable td select {
    width: 100%;
  }
  #legend {
    background: rgba(255, 255, 255, 0.75);
    border: solid white 2px;
    position: absolute;
    left: 5px;
    bottom: 50px;
    padding: 1px;
  }
  .imageCell {
    text-align: center;
  }
  .descriptionCell {
    font-size: 14px;
  }
</style>

<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
<script type="text/javascript" src="/static/json2.js"></script>
<script type="text/javascript">
  var gtugs = {
    map: null,
    activeInfoWindow: null,
    GTUG_MARKER: '/static/gtugmarker.png',
    MARKER_SHADOW: '/static/markershadow.png',
    LOCATION_MARKER_S: '/static/red_60p.png',
    LOCATION_MARKER_M: '/static/red_80p.png',
    LOCATION_MARKER_L: '/static/red_100p.png',
    LOCATION_MARKER_SHADOW_S: '/static/red_shadow_60p.png',
    LOCATION_MARKER_SHADOW_M: '/static/red_shadow_80p.png',
    LOCATION_MARKER_SHADOW_L: '/static/red_shadow_100p.png'
  };

  window.onload = function() {
    gtugs.map = new google.maps.Map(document.getElementById("map"), {
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        center: new google.maps.LatLng(18, 0),
        zoom: 2,
        disableDefaultUI: true,
        scrollwheel: false
    });

    new core.AjaxRequest('GET', '/chapters', function(json) {
        var chapters = JSON.parse(json).chapters;
        for (var i = 0; i < chapters.length; i++) {
          var chapter = chapters[i];

          var coordinates = new google.maps.LatLng(chapter.latitude,
              chapter.longitude);
          var shadow = new google.maps.MarkerImage(gtugs.MARKER_SHADOW,
              new google.maps.Size(37, 34), new google.maps.Point(0,0),
              new google.maps.Point(9, 34));

          var marker = new google.maps.Marker({
              map: gtugs.map,
              title: chapter.name,
              position: coordinates,
              icon: gtugs.GTUG_MARKER,
              shadow: shadow
          });

          var html = [];
          html.push('<div style="border-bottom:solid black 1px; font-size:14px; font-weight:bold">');
          html.push(chapter.name);
          html.push('</div><div style="font-size:12px">Status: <em>');
          html.push(chapter.status);
          html.push('</em></div><ul><li><a href="/chapter.jsp?id=');
          html.push(chapter.id);
          html.push('">Chapter profile</a></li>');
          if (chapter.website != null) {
            html.push('<li><a href="');
            html.push(chapter.website);
            html.push('">Website</a></li>');
          }
          html.push('</ul>');
          var infoWindow = new google.maps.InfoWindow({
              content: html.join('')
          })

          google.maps.event.addListener(marker, 'click',
              gtugs.openInfoWindow(infoWindow, marker));
        }
      }).send();

    new core.AjaxRequest('GET', '/guests', function(json) {
      var guests = JSON.parse(json).guests;
      for (var i = 0; i < guests.length; i++) {
        var guest = guests[i];

        var coordinates = new google.maps.LatLng(guest.latitude,
            guest.longitude);

        var icon = null;
        var shadow = null;
        if (guest.occurrences <= 2) {
          icon = gtugs.LOCATION_MARKER_S;
          shadow = gtugs.LOCATION_MARKER_SHADOW_S;
        } else if (guest.occurrences <= 4) {
          icon = gtugs.LOCATION_MARKER_M;
          shadow = gtugs.LOCATION_MARKER_SHADOW_M;
        } else {
          icon = gtugs.LOCATION_MARKER_L;
          shadow = gtugs.LOCATION_MARKER_SHADOW_L;
        }

        var marker = new google.maps.Marker({
            map: gtugs.map,
            position: coordinates,
            icon: icon
            //shadow: shadow
        });
      }
    }).send();
  };

  gtugs.openInfoWindow = function(infoWindow, marker) {
    return function() {
      if (gtugs.activeInfoWindow != null) {
        gtugs.activeInfoWindow.close();
      }

      infoWindow.open(gtugs.map, marker);
      gtugs.activeInfoWindow = infoWindow;
    };
  };
</script>

<div id="title">
  <h2>Sign up</h2>
</div>

<p>GTUGs are user groups for people who are interested in Google's developer technology; everything from the Android and App Engine platforms, to product APIs like the YouTube API and Google Calendar API, to initiatives like OpenSocial. Check out <a href="http://code.google.com">code.google.com</a> for the full list of offerings.</p>

<p>Use the form below to indicate your interest in starting or joining a GTUG in your area!</p>

<form:form method="post" commandName="guest">
  <table id="gtugs-signupFormTable" cellpadding="0" cellspacing="0">
    <tr>
      <th>Name</th>
      <th>Email</th>
      <th>City, state, country</th>
      <th>I want to...</th>
    </tr>
    <tr>
      <td>
        <form:input path="name"/>
      </td>
      <td>
        <form:input path="email"/>
      </td>
      <td style="width:175px">
        <form:input path="location"/>
      </td>
      <td>
        <form:select path="interest">
          <form:option value="Joining">Join</form:option>
          <form:option value="Organizing">Organize</form:option>
        </form:select>
      </td>
      <td style="padding-right:0px">
        <input type="submit" value="Submit"/>
      </td>
    </tr>
    <tr>
      <td><span class="error"><form:errors path="name"/></span></td>
      <td><span class="error"><form:errors path="email"/></span></td>
      <td><span class="error"><form:errors path="location"/></span></td>
      <td><span class="error"><form:errors path="interest"/></span></td>
      <td></td>
    </tr>
  </table>
</form:form>

<div style="position:relative">
  <div id="map" style="border:solid #0566cc 1px; height:430px; width:100%"></div>
  <div id="legend">
    <table>
      <tr>
        <td class="imageCell"><img src="/static/gtugmarker.png"/></td>
        <td class="descriptionCell">GTUG</td>
      </tr>
      <tr>
        <td class="imageCell"><img src="/static/red_60p.png"/></td>
        <td class="descriptionCell">1-2 people interested</td>
      </tr>
      <tr>
        <td class="imageCell"><img src="/static/red_80p.png"/></td>
        <td class="descriptionCell">3-4 people interested</td>
      </tr>
      <tr>
        <td class="imageCell"><img src="/static/red_100p.png"/></td>
        <td class="descriptionCell">5+ people interested</td>
      </tr>
    </table>
  </div>
</div>

<%@ include file="includes/footer.jsp" %>
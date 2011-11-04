/*
 * Copyright 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brussels.gtug.attendance.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

/**
 * @author jasonacooper@google.com (Jason Cooper)
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
public class Event implements Serializable {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	private Long id;

	@Persistent
	private String name;

	@Persistent
	private String timeZone;

	@Persistent
	private Date startDate;

	@NotPersistent
	private String startTime;

	@Persistent
	private Date endDate;

	@NotPersistent
	private String endTime;

	@Persistent
	private String streetAddress;

	@Persistent
	private String room;

	@Persistent
	private String city;

	@Persistent
	private String state;

	@Persistent
	private String zipCode;

	@Persistent
	private String country;

	@Persistent
	private Double latitude;

	@Persistent
	private Double longitude;

	@Persistent
	private String url;

	@NotPersistent
	private Boolean onAndroid;

	@NotPersistent
	private Boolean onGoogleAjaxApis;

	@NotPersistent
	private Boolean onGoogleAppEngine;

	@NotPersistent
	private Boolean onGoogleChromeExtensions;

	@NotPersistent
	private Boolean onGoogleEarthApis;

	@NotPersistent
	private Boolean onGoogleMapsApis;

	@NotPersistent
	private Boolean onGoogleWaveApis;

	@NotPersistent
	private Boolean onGoogleWebToolkit;

	@NotPersistent
	private Boolean onOpenSocial;

	@NotPersistent
	private Boolean onOther;

	@NotPersistent
	private String customTopic;

	@Persistent
	private List<String> topics;

	@Persistent(defaultFetchGroup = "true")
	private Text description;

	@Persistent
	private Integer attendeeCount;

	@Persistent(defaultFetchGroup = "true")
	private Text notes;

	@Persistent
	private Long chapterId;
	
	@Persistent
	private List<String> attendees;

	public Long getId() {
		if (key != null)
			return key.getId();
		else 
			return id;
	}

	public String getName() {
		return name;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public Date getStartDate() {
		return startDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getEndTime() {
		return endTime;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public String getRoom() {
		return room;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getCountry() {
		return country;
	}

	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public String getUrl() {
		return url;
	}

	public Boolean getOnAndroid() {
		return onAndroid;
	}

	public Boolean getOnGoogleAjaxApis() {
		return onGoogleAjaxApis;
	}

	public Boolean getOnGoogleAppEngine() {
		return onGoogleAppEngine;
	}

	public Boolean getOnGoogleChromeExtensions() {
		return onGoogleChromeExtensions;
	}

	public Boolean getOnGoogleEarthApis() {
		return onGoogleEarthApis;
	}

	public Boolean getOnGoogleMapsApis() {
		return onGoogleMapsApis;
	}

	public Boolean getOnGoogleWaveApis() {
		return onGoogleWaveApis;
	}

	public Boolean getOnGoogleWebToolkit() {
		return onGoogleWebToolkit;
	}

	public Boolean getOnOpenSocial() {
		return onOpenSocial;
	}

	public Boolean getOnOther() {
		return onOther;
	}

	public String getCustomTopic() {
		return customTopic;
	}

	public List<String> getTopics() {
		return topics;
	}

	public String getDescription() {
		if (description == null) {
			return "";
		}

		return description.getValue();
	}

	public Integer getAttendeeCount() {
		return attendeeCount;
	}

	public String getNotes() {
		if (notes == null) {
			return "";
		}

		return notes.getValue();
	}

	public Long getChapterId() {
		return chapterId;
	}
	
	public void setKey(Key key) {
		this.key = key;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setOnAndroid(Boolean onAndroid) {
		this.onAndroid = onAndroid;
	}

	public void setOnGoogleAjaxApis(Boolean onGoogleAjaxApis) {
		this.onGoogleAjaxApis = onGoogleAjaxApis;
	}

	public void setOnGoogleAppEngine(Boolean onGoogleAppEngine) {
		this.onGoogleAppEngine = onGoogleAppEngine;
	}

	public void setOnGoogleChromeExtensions(Boolean onGoogleChromeExtensions) {
		this.onGoogleChromeExtensions = onGoogleChromeExtensions;
	}

	public void setOnGoogleEarthApis(Boolean onGoogleEarthApis) {
		this.onGoogleEarthApis = onGoogleEarthApis;
	}

	public void setOnGoogleMapsApis(Boolean onGoogleMapsApis) {
		this.onGoogleMapsApis = onGoogleMapsApis;
	}

	public void setOnGoogleWaveApis(Boolean onGoogleWaveApis) {
		this.onGoogleWaveApis = onGoogleWaveApis;
	}

	public void setOnGoogleWebToolkit(Boolean onGoogleWebToolkit) {
		this.onGoogleWebToolkit = onGoogleWebToolkit;
	}

	public void setOnOpenSocial(Boolean onOpenSocial) {
		this.onOpenSocial = onOpenSocial;
	}

	public void setOnOther(Boolean onOther) {
		this.onOther = onOther;
	}

	public void setCustomTopic(String customTopic) {
		this.customTopic = customTopic;
	}

	public void addTopic(String topic) {
		if (topics == null) {
			topics = new ArrayList<String>();
		}

		topics.add(topic);
	}

	public void setDescription(String description) {
		this.description = new Text(description);
	}

	public void setAttendeeCount(Integer attendeeCount) {
		this.attendeeCount = attendeeCount;
	}

	public void setNotes(String notes) {
		this.notes = new Text(notes);
	}

	public void setChapterId(Long chapterId) {
		this.chapterId = chapterId;
	}

	public List<String> getAttendees() {
		return attendees;
	}

	public void setAttendees(List<String> attendees) {
		this.attendees = attendees;
	}
}

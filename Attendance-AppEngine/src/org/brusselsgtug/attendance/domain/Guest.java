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

package org.brusselsgtug.attendance.domain;

import com.google.appengine.api.datastore.Key;

import java.util.Date;
import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * @author jasonacooper@google.com (Jason Cooper)
 */
@PersistenceCapable(identityType=IdentityType.APPLICATION, detachable="true")
public class Guest implements Serializable {

  @PrimaryKey
  @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
  private Key key;

  @Persistent
  private String name;

  @Persistent
  private String email;

  @Persistent
  private String country;

  @Persistent
  private String location;

  @NotPersistent
  private String cachedLocation;

  @Persistent
  private Double latitude;

  @Persistent
  private Double longitude;

  @Persistent
  private String interest;

  @Persistent
  private Date createdOn;

  public Guest() {
    createdOn = new Date();
  }

  public Long getId() {
    return key.getId();
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getCountry() {
    return country;
  }

  public String getLocation() {
    return location;
  }

  public String getCachedLocation() {
    return cachedLocation;
  }

  public Double getLatitude() {
    return latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public String getInterest() {
    return interest;
  }

  public Date getCreatedOn() {
    return createdOn;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public void setCachedLocation(String cachedLocation) {
    this.cachedLocation = cachedLocation;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public void setInterest(String interest) {
    this.interest = interest;
  }

  public void setCreatedOn(Date createdOn) {
    this.createdOn = createdOn;
  }
}

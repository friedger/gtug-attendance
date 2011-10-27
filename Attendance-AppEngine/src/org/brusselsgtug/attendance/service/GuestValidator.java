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

package org.brusselsgtug.attendance.service;

import org.gtugs.domain.Guest;
import org.gtugs.domain.Point;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;

/**
 * @author jasonacooper@google.com (Jason Cooper)
 */
public class GuestValidator implements Validator {

  private MapsService mapsService;

  public boolean supports(Class c) {
    return Guest.class.equals(c);
  }

  public void validate(Object obj, Errors errors) {
    Guest guest = (Guest) obj;
    boolean locationErrors = false;

    if (guest == null) {
      errors.rejectValue("name", "error.name-required", null, "Required");
    }
    else {
      if (guest.getName() == null || guest.getName().equals("")) {
        errors.rejectValue("name", "error.name-required", null, "Required");
      }
      if (guest.getEmail() == null || guest.getEmail().equals("")) {
        errors.rejectValue("email", "error.email-required", null, "Required");
      } else if (!isValidEmail(guest.getEmail())) {
        errors.rejectValue("email", "error.invalid-email", null, "Invalid");
      }
      if (guest.getLocation() == null || guest.getLocation().equals("")) {
        errors.rejectValue("location", "error.location-required", null, "Required");
        locationErrors = true;
      }
      if (guest.getInterest() == null || guest.getInterest().equals("")) {
        errors.rejectValue("interest", "error.interest-required", null, "Required");
      }

      if (!locationErrors) {
        Point point = mapsService.getCoordinates(guest.getLocation(),
            guest.getCountry());
        if (point == null) {
          errors.rejectValue("location", "error.geocoding-failed", null, "Geocoding failed");
          errors.rejectValue("country", "error.geocoding-failed", null, "Geocoding failed");
        } else {
          guest.setLatitude(point.getLatitude());
          guest.setLongitude(point.getLongitude());
        }
      }
    }
  }

  private boolean isValidEmail(String email) {
    // From http://regexlib.com/DisplayPatterns.aspx
    String regex = "^([0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\\w]" +
        "*[0-9a-zA-Z]\\.)+[a-zA-Z]{2,9})$";

    return email.matches(regex);
  }

  public void setMapsService(MapsService mapsService) {
    this.mapsService = mapsService;
  }
}

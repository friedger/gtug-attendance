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

package org.brusselsgtug.attendance.web;

import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.brusselsgtug.attendance.domain.Guest;
import org.brusselsgtug.attendance.service.GuestManager;
import org.brusselsgtug.attendance.service.SimpleLocationManager;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author jasonacooper@google.com (Jason Cooper)
 */
public class SignupFormController extends SimpleFormController { 

  private GuestManager guestManager;
  private SimpleLocationManager locationManager;

  @Override 
  protected void initBinder(HttpServletRequest request,
     ServletRequestDataBinder binder) throws Exception {
   binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
  }

  @Override
  @SuppressWarnings("unchecked")
  protected ModelAndView showForm(HttpServletRequest request,
      HttpServletResponse response, BindException errors) {
    String countryCode = request.getParameter("cc");
    if (countryCode != null) {
      if (countryCode.equals("RU")) {
        RequestContextUtils.getLocaleResolver(request).setLocale(request,
            response, new Locale("ru"));
      } else if (countryCode.equals("UA")) {
        RequestContextUtils.getLocaleResolver(request).setLocale(request,
            response, new Locale("ru"));
      } else if (countryCode.equals("BY")) {
        RequestContextUtils.getLocaleResolver(request).setLocale(request,
            response, new Locale("ru"));
      } else if (countryCode.equals("US")) {
        RequestContextUtils.getLocaleResolver(request).setLocale(request,
            response, new Locale("en"));
      }
    }

    if (countryCode != null) {
      return new ModelAndView("signupCountry", errors.getModel());
    } else {
      return new ModelAndView(getFormView(), errors.getModel());
    }
  }

  @Override
  protected void onBind(HttpServletRequest request, Object command) throws
      Exception {
    Guest guest = (Guest) command;
    guest.setCachedLocation(guest.getLocation());
  }

  @Override
  public ModelAndView onSubmit(Object command) throws ServletException {
    Guest guest = (Guest) command;

    guestManager.storeGuest(guest);
    locationManager.incrementOrStoreLocation(guest.getLatitude(),
        guest.getLongitude());

    //return new ModelAndView(
        //new RedirectView("/signUp.jsp?cc=" + guest.getCountry()));
    return new ModelAndView(new RedirectView("/signUp.jsp"));
  }

  public void setGuestManager(GuestManager guestManager) {
    this.guestManager = guestManager;
  }

  public void setLocationManager(SimpleLocationManager locationManager) {
    this.locationManager = locationManager;
  }
}

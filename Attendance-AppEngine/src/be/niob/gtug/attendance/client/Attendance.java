/*
 * Copyright 2011 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package be.niob.gtug.attendance.client;

import be.niob.gtug.attendance.server.DeviceService;
import be.niob.gtug.attendance.server.DeviceServiceAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Attendance implements EntryPoint {

	//private final DeviceServiceAsync deviceService = GWT.create(DeviceService.class);
	
  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    AttendanceWidget widget = new AttendanceWidget();
    RootPanel.get().add(widget);
  }
}

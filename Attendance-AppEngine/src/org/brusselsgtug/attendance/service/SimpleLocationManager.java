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

import org.gtugs.domain.Location;
import org.gtugs.repository.JdoLocationDao;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import java.util.List;

/**
 * @author jasonacooper@google.com (Jason Cooper)
 */
public class SimpleLocationManager {

  private static final String LOCATIONS_MEMCACHE_KEY = "locations";

  private JdoLocationDao locationDao;

  public List<Location> getLocations() {
    MemcacheService ms = MemcacheServiceFactory.getMemcacheService();

    if (!ms.contains(LOCATIONS_MEMCACHE_KEY)) {
      ms.put(LOCATIONS_MEMCACHE_KEY, locationDao.getLocations());
    }

    return (List<Location>) ms.get(LOCATIONS_MEMCACHE_KEY);
  }

  public void incrementOrStoreLocation(Double latitude, Double longitude) {
    MemcacheService ms = MemcacheServiceFactory.getMemcacheService();

    locationDao.incrementOrStoreLocation(latitude, longitude);

    ms.delete(LOCATIONS_MEMCACHE_KEY);
  }

  public void setLocationDao(JdoLocationDao locationDao) {
    this.locationDao = locationDao;
  }
}

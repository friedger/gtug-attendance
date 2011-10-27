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
import org.gtugs.repository.GuestDao;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import java.util.Date;
import java.util.List;

/**
 * @author jasonacooper@google.com (Jason Cooper)
 */
public class SimpleGuestManager implements GuestManager {

  private static final String GUESTS_MEMCACHE_KEY = "guests";

  private GuestDao guestDao;

  public List<Guest> getGuests() {
    MemcacheService ms = MemcacheServiceFactory.getMemcacheService();

    if (!ms.contains(GUESTS_MEMCACHE_KEY)) {
      ms.put(GUESTS_MEMCACHE_KEY, guestDao.getGuests());
    }

    return (List<Guest>) ms.get(GUESTS_MEMCACHE_KEY);
  }

  public List<Guest> getGuests(Date createdAfter) {
    MemcacheService ms = MemcacheServiceFactory.getMemcacheService();

    if (!ms.contains(GUESTS_MEMCACHE_KEY)) {
      ms.put(GUESTS_MEMCACHE_KEY, guestDao.getGuests(createdAfter));
    }

    return (List<Guest>) ms.get(GUESTS_MEMCACHE_KEY);
  }

  public void storeGuest(Guest guest) {
    MemcacheService ms = MemcacheServiceFactory.getMemcacheService();

    guestDao.storeGuest(guest);

    ms.delete(GUESTS_MEMCACHE_KEY);
  }

  public void setGuestDao(GuestDao guestDao) {
    this.guestDao = guestDao;
  }
}

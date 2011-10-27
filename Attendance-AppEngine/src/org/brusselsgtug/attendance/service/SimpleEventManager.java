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

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import org.gtugs.domain.Event;
import org.gtugs.repository.EventDao;

import java.util.Date;
import java.util.List;

/**
 * @author jasonacooper@google.com (Jason Cooper)
 */
public class SimpleEventManager implements EventManager {

  private static final String EVENTS_MEMCACHE_KEY = "events";

  private EventDao eventDao;

  public Event getEvent(Long id) {
    return eventDao.getEvent(id);
  }

  public List<Event> getEvents() {
    MemcacheService ms = MemcacheServiceFactory.getMemcacheService();

    if (!ms.contains(EVENTS_MEMCACHE_KEY)) {
      ms.put(EVENTS_MEMCACHE_KEY, getEvents(false));
    }

    return (List<Event>) ms.get(EVENTS_MEMCACHE_KEY);
  }

  public List<Event> getEvents(boolean futureOnly) {
    return eventDao.getEvents(futureOnly);
  }

  public List<Event> getEvents(Date startRange, Date endRange) {
    return eventDao.getEvents(startRange, endRange);
  }

  public List<Event> getEventsForChapter(Long chapterId) {
    return getEventsForChapter(chapterId, false);
  }

  public List<Event> getEventsForChapter(Long chapterId, boolean futureOnly) {
    return eventDao.getEventsForChapter(chapterId, futureOnly);
  }

  public List<Event> getPastEvents(Long chapterId) {
    return eventDao.getPastEvents(chapterId);
  }

  public void storeEvent(Event event) {
    MemcacheService ms = MemcacheServiceFactory.getMemcacheService();

    eventDao.storeEvent(event);

    ms.delete(EVENTS_MEMCACHE_KEY);
  }

  public void setEventDao(EventDao eventDao) {
    this.eventDao = eventDao;
  }
}

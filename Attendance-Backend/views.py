import os
import json

from google.appengine.api import users

from google.appengine.ext import db
from google.appengine.ext.db import djangoforms

import django
from django import http
from django import shortcuts
from django.utils import simplejson

import c2dm
from c2dm import C2DM
from c2dm import ClientLoginTokenFactory

class Meeting(db.Model):
  name = db.StringProperty(required=True)
  attendees = db.ListProperty(users.User)
  description = db.TextProperty()
  url = db.URLProperty()
  date = db.DateTimeProperty()
  creator = db.UserProperty()
  created = db.DateTimeProperty(auto_now_add=True)
  modified = db.DateTimeProperty(auto_now=True)

class MeetingForm(djangoforms.ModelForm):
  class Meta:
    model = Meeting
    exclude = ['attendees', 'creator', 'created', 'modified']

def respond(request, user, template, params=None):
  """Helper to render a response, passing standard stuff to the response.

  Args:
    request: The request object.
    user: The User object representing the current user; or None if nobody
      is logged in.
    template: The template name; '.html' is appended automatically.
    params: A dict giving the template parameters; modified in-place.

  Returns:
    Whatever render_to_response(template, params) returns.

  Raises:
    Whatever render_to_response(template, params) raises.
  """
  if params is None:
    params = {}
  if user:
    params['user'] = user
    params['sign_out'] = users.create_logout_url('/')
    params['is_admin'] = (users.is_current_user_admin() and
                          'Dev' in os.getenv('SERVER_SOFTWARE'))
  else:
    params['sign_in'] = users.create_login_url(request.path)
  if not template.endswith('.html'):
    template += '.html'
  return shortcuts.render_to_response(template, params)


def index(request):
  """Request / -- show all meetings."""
  user = users.get_current_user()
  meetings = db.GqlQuery('SELECT * FROM Meeting ORDER BY created DESC')
  if (request.META['CONTENT_TYPE'] == 'application/json'):
    return http.HttpResponse(json.encode(meetings), mimetype='application/json')
  else:
    return respond(request, user, 'list', {'meetings': meetings})
  
def edit(request, meeting_id):
  """Create or edit a meeting.  GET shows a blank form, POST processes it."""
  user = users.get_current_user()
  if user is None:
    return http.HttpResponseForbidden('You must be signed in to add or edit a meeting')

  meeting = None
  if meeting_id:
    meeting = Meeting.get(db.Key.from_path(Meeting.kind(), int(meeting_id)))
    if meeting is None:
      return http.HttpResponseNotFound('No meeting exists with that key (%r)' %
                                       meeting_id)

  form = MeetingForm(data=request.POST or None, instance=meeting)

  if not request.POST:
    return respond(request, user, 'meeting', {'form': form, 'meeting': meeting})

  errors = form.errors
  if not errors:
    try:
      meeting = form.save(commit=False)
    except ValueError, err:
      errors['__all__'] = unicode(err)
  if errors:
    return respond(request, user, 'meeting', {'form': form, 'meeting': meeting})

  if not meeting.creator:
    meeting.creator = user
  meeting.put()

  return http.HttpResponseRedirect('/')

def new(request):
  """Create a meeting.  GET shows a blank form, POST processes it."""
  return edit(request, None)

def show(request, meeting_id):

  user = users.get_current_user()
  if user is None:
    return http.HttpResponseForbidden('You must be signed in to add or edit a meeting')

  meeting = None
  if meeting_id:
    meeting = Meeting.get(db.Key.from_path(Meeting.kind(), int(meeting_id)))
    if meeting is None:
      return http.HttpResponseNotFound('No meeting exists with that key (%r)' %
                                       meeting_id)

  return respond(request, user, 'show', {'meeting': meeting})

class Registration(db.Model):
  accountName=db.StringProperty()
  registrationId=db.StringProperty()
  
class RegistrationForm(djangoforms.ModelForm):
  class Meta:
    model = Registration
    
def all(request):
  user = users.get_current_user()
  regs = db.GqlQuery('SELECT * FROM Registration')
  return respond(request, user, 'regs', {'regs': regs})

def register(request):

  accountName = request.POST['accountName']
  registrationId = request.POST['registrationId']
  
  query = db.GqlQuery('SELECT * FROM Registration WHERE accountName=:1', accountName)
  items = query.fetch(1)
  if len(items)>0:
    registration=items[0]
    if registrationId=="":  # unregistration
       registration.delete()
    else:
       registration.registrationId=registrationId
       registration.put()
  else:
    registration=Registration()
    registration.accountName=accountName
    registration.registrationId=registrationId
    registration.put()

  return http.HttpResponse("Successful registered")

def unregister(request):
  registrationId = request.POST['registrationId']
  
  query = db.GqlQuery('SELECT * FROM Registration WHERE accountName=:1', accountName)
  items = query.fetch(1)
  if len(items)>0:
    registration=items[0]
    registration.delete()
    
  return http.HttpResponse("Successful unregistered")  
  
def send(request):
  user = users.get_current_user()
  if user is None:
    return http.HttpResponseForbidden('You must be signed in to send messages')

  sender = C2DM()
  sender.registrationId = 'APA91bHU00Qz-Dkr4Pfd2hlxXlSzkXZqhMfnB6m1yl2jTZPo2-l9-ZFU4iFGOOJn_LeuodNgk6KQR8CMcQwC1ot5AUa2XuCNjMukTX41U6p_w3Zwt3fgctaMck3SlR8T8Uwb_RTPTAzAhQIQceyfA83IJKpfqtITng'
  sender.collapseKey = 1
  sender.data = {'message' : 'Hello there', 'sender' : 'wortel'}
  response = sender.sendMessage()
  
  return respond(request, user, 'send', None)
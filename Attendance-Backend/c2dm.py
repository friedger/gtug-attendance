import urllib, urllib2

class ClientLoginTokenFactory():
  _token = None 

  def __init__(self):
    self.url = 'https://www.google.com/accounts/ClientLogin'
    self.accountType = 'GOOGLE'
    self.email = 'versluyssander.c2dm@gmail.com'
    self.password = '12rt65az12cv'
    self.source = 'Attendance-GTUG'
    self.service = 'ac2dm'

  def getToken(self):
    if self._token is None:

      # Build payload
      values = {'accountType' : self.accountType,
            'Email' : self.email,
            'Passwd' : self.password,
            'source' : self.source,
            'service' : self.service}

      # Build request
      data = urllib.urlencode(values)
      request = urllib2.Request(self.url, data)

      # Post
      response = urllib2.urlopen(request)
      responseAsString = response.read()

      # Format response
      responseAsList = responseAsString.split('\n')

      self._token = responseAsList[2].split('=')[1]

    return self._token
    
class C2DM():

  def __init__(self):
    self.url = 'https://android.apis.google.com/c2dm/send'
    self.clientAuth = None
    self.registrationId = None
    self.collapseKey = None
    self.data = {}

  def sendMessage(self):
    if self.registrationId == None or self.collapseKey == None:
      return False

    clientAuthFactory = ClientLoginTokenFactory()
    self.clientAuth = clientAuthFactory.getToken()
    
    # Build payload
    values = {'registration_id' : self.registrationId,
          'collapse_key' : self.collapseKey}   
    
    # Loop over any data we want to send
    for k, v in self.data.iteritems():
      values['data.' + k] = v

    # Build request
    headers = {'Authorization': 'GoogleLogin auth=' + self.clientAuth}
    data = urllib.urlencode(values)
    request = urllib2.Request(self.url, data, headers)

    # Post
    try:
      response = urllib2.urlopen(request)
      responseAsString = response.read()

      return responseAsString
    except urllib2.HTTPError, e:
      print 'HTTPError ' + str(e)
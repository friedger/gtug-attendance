#!/usr/bin/python
from suds.client import Client
url = 'http://gpns.dev.reference.be/notification-ws/notification.wsdl'
client = Client(url)

print client

result = client.service.notification(message='boe',
                                     category='test',
                                     userName='test',
                                     application='test')
                                     
print result
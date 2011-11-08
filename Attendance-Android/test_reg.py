#!/usr/bin/python
from suds.client import Client
url = 'http://gpns.dev.reference.be/registration-ws/registration.wsdl'
client = Client(url)

print client

result = client.service.registration(DeviceToken='APA91bH-RjvxfpmYWxEKifrrVMMe3OE55Dvx_0hiCVO1mSo7FyJzipoGPPOWUp9TIYX0lze5cOYNboZ2PtjuUznCJ6tGQVCCnz6DXQ1CS0WroTLZOO4cC1OPE2PrgR5OA9nFsCAJg7HsNBE9LMLMZLPgLRHEfXMWMQ',
                                     Username='test',
                                     ApplicationName='GPNStest',
                                     ApplicationPackageName='be.reference.gpns.test',
                                     ApplicationVersion='1',
                                     Os='Android',
                                     OsVersion='2',
                                     Category=['test'])
                                     
print result
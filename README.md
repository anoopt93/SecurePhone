# SecurePhone
Android app to Secure your phone
In this android application users should set  3 mobile numbers and a password.
In case user lost the phone or forget it somewhere, 
user can find the phone's location by sending the password(which is already saved  in the app) as sms to phone number from any of the 3 saved numbers.
Then the application automatically pick the phones location and send it as sms to the 3 numbers.
When anyone try to change the phone's sim card then app read phone's current loccation, new sim phone number and send it as sms to the 3 saved numbers.
Things to note is that, Phone's location service should always be on, or it should always have aworking internet connection and account balance to send sms.
Android BroadcastReceiver is used in this application for background services.

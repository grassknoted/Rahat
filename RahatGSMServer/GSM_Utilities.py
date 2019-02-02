from time import sleep
import serial
from curses import ascii

ser = serial.Serial()

# Set port connection to USB port GSM modem 
ser.port = "/dev/serial0"

# Set older phones to a baudrate of 9600
ser.baudrate = 9600

ser.timeout = 1
ser.open()
ser.write('AT+CMGF=1\r\n')

# Prefered message storage area to modem memory
ser.write('AT+CPMS="ME","SM","ME"\r\n')

def sendsms(number,text):
    ser.write('AT+CMGF=1\r\n')
    sleep(4)
    ser.write('AT+CMGS="%s"\r\n' % number)
    sleep(4)
    ser.write('%s' % text)
    sleep(4)
    ser.write(ascii.ctrl('z'))
    print "Text: {0} has been sent to: {1}".format(text, number)

def read_all_sms():
    ser.write('AT+CMGF=1\r\n')
    sleep(2)
    ser.write('AT+CMGL="ALL"\r\n')
    sleep(2)
    a = ser.readlines()
    z=[]
    y=[]
    for x in a:
        if x.startswith('+CMGL:'):
            r=a.index(x)
            t=r+1
            z.append(r)
            z.append(t)
    for x in z:
        y.append(a[x])

    # Change modem back to PDU mode
    ser.write('AT+CMGF=0\r\n')
    return y   

# Read unread messages
def read_unread_sms():
    print "No new messages"
    ser.write('AT+CMGF=1\r\n')
    sleep(1)
    ser.write('AT+CMGL="REC UNREAD"\r\n')
    sleep(1)
    a = ser.readlines()
    z=[]
    y=[]
    for x in a:
        if x.startswith('+CMGL:'):
            print "Message found!"
            r=a.index(x)
            t=r+1
            z.append(r)
            z.append(t)
            print x
    for x in z:
        y.append(a[x])

    ##Following line changed modem back to PDU mode
    #ser.write('AT+CMGF=0\r\n')
    return y    

def read_read_sms():
    ##returns all unread SMSs on your SIM card
    ser.write('AT+CMGS=1\r\n')
    ser.read(100)
    ser.write('AT+CMGL="REC READ"\r\n')
    ser.read(1)
    a = ser.readlines()
    for x in a:
        print(x)

# Delete all messages
def delete_all_sms():
    ser.write('AT+CMGF=0\r\n')
    sleep(3)
    ser.write('AT+CMGD=0,4\r\n')
    sleep(3)
    ser.write('AT+CMGF=1\r\n')

# Delete read messages
def delete_read_sms():
    ser.write('AT+CMGF=0\r\n')
    sleep(5)
    ser.write('AT+CMGD=0,1\r\n')
    sleep(5)
    ser.write('AT+CMGF=1\r\n')

# Find USSD codes of network operator
def check_ussd_support():
    ser.write('AT+CMGF=0\r\n')
    ser.write('AT+CUSD=?\r\n')
    ser.write('AT+CMGF=1\r\n')
    
# Get SIM balance using USSD codes
def get_balance():
    # Set the modem to PDU mode, then pass the USSD command(CUSD)=1, USSD code eg:*141# 
    # Error may read +CUSD: 0,"The service you requested is currently not available.",15
    # default value for <dcs> is 15
    ser.write('AT+CMGF=0\r\n')
    ser.write('AT+CUSD=1,*141#,15\r\n')
    ser.read(1)
    a = ser.readlines()
    print(a)
    ser.write('AT+CMGF=1\r\n')

# Check USSD messages
def ussd_sms_check():
    ser.write('AT+CMGF=0\r\n')
    ser.write('AT+CUSD=1,*141*1#,15\r\n')
    ser.read(100)
    a = ser.readlines()
    print(a)
    ser.write('AT+CMGF=1\r\n')

while True:
	read_unread_sms()
	sleep(1)
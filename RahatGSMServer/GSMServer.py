import getpass
import requests
from time import sleep
from GSM_Utilities import *
from threading import Thread

incoming_message_count = 1
ip = 0

def respond_to_message(message_text):
	print("HELOOOOOOOOOOOOOOOOO")

def respond_to_message2(message_text):
	global incoming_message_count
	print("Message {0} received. Text:{1}\n".format(incoming_message_count, message_text))
	
	r = requests.post("http://192.168.0.106:82/", headers={'message':message_text})
	current_message_no = incoming_message_count
	incoming_message_count += 1
	
	# Main code here
	#sendsms("+919448415741", message_text)
	print("Message {0} processed successfully".format(current_message_no))

def no_message():
	while(True):
		print "No new messages.."
		sleep(3)

if __name__ == "__main__":
	while(True): # Always listening for incoming messages
		unread_messages = read_unread_sms()
		#ip = raw_input()
		print "No messages recieved!"
		thread = Thread(target=no_message, args=())
		thread.start()
		#ip_k = getpass.getpass('')
		#ip = ip+1
		#thread = Thread(target=respond_to_message, args=("#CH a238djkgjrH43 13.014854391298044 7765551924705507"))
		#thread.start()
		if(len(unread_messages) >0):
			print "Unread Message: {0} Length: {1}".format(unread_messages, len(unread_messages))
			thread = Thread(target=respond_to_message, args=(unread_messages[1].rstrip(), ))
			thread.start()
			sleep(2)

		#a = "#CI a238djkgjrH43 13.014854391298044 7765551924705507"
		#elif(ip == 2):
		#	a = "#CH a238djkgjrH43 13.014854391298044 7765551924705507"
		#elif(ip == 3):
		#	a = "#CC	 a238djkgjrH43 13.014854391298044 7765551924705507"
		#else:
		#	print "P: {0}".format(ip)
		#	a = "Help requested, added to Live Map"
		#thread = Thread(target=respond_to_message, args=(a,))
		#	ip = 0
		#thread.start()
		#print "Main Thread found a new message!"

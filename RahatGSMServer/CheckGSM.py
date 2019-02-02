import serial
test_string = "Port series test 1 2 3 4 5"
port_list = ["/dev/ttyAMA0", "/dev/ttyAMA0", "/dev/ttyS0", "/dev/ttyS0",]
for port in port_list:
  try:
    serialPort = serial.Serial(port, 9600, timeout = 2)
    print "Port Serial Number ", port, " some crap :"
    bytes_sent = serialPort.write(test_string)
    print "Bytes sent: ", bytes_sent, " octets"
    loopback = serialPort.read(bytes_sent)
    if loopback == test_string:
      print "Recu ", len(loopback), "octets identiques. Le port", port, "fonctionne bien ! \n"
    else:
      print "Recu des donnes incorrectes : ", loopback, " sur le port seie ", port, " boucle \n"
    serialPort.close()
  except IOError:
    print "Error in ", port, "\n" 
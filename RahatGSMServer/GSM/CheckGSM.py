import serial
import os, time

# Enable Serial Communication
port = serial.Serial("/dev/serial0", baudrate=9600, timeout=1)

# Transmitting AT Commands to the Modem
# '\r\n' indicates the Enter Key

port.write('AT'+'\r\n')
rcv = port.read(10)
print rcv
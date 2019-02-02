import requests
import datetime

# URL = '13.67.110.42:8082'

# Victim
# data = {'type':'victim',
# 	'number':'9036112331',
# 	'lat_val':'77.549320',
# 	'long_val':'19.65839',
# 	'food':'1',
# 	'water':'0',
# 	'med':'0',
# 	'shelter':'1',
# 	'info':'testing adding a victim',
# 	'status':'0'
# }

# Help
# data = {'type':'help',
# 	'number':'9036112331',
# 	'lat_val':'77.549320',
# 	'long_val':'19.65839',
# 	'info':'testing adding a help'
# }

# Shelter
data = {'type':'shelter',
	'lat_val':'77.549320',
	'long_val':'19.65839',
  'people':'19',
	'info':'testing adding a shelter'
}

# Tweet
# data = {'type':'tweet',
# 	'tweet_text': "This in not a test tweet.",
# 	'sentiment': "0.6"
# }

# sending post request and saving response as response object 
r = requests.post('http://13.67.110.42:8082/add', json = data) 
  
# extracting response text  
response = r.text 
print("The response is:", response) 

from flask import Flask, render_template, request
from werkzeug import secure_filename
import rahat_sql
import time
from flask import jsonify, json, Response
app = Flask(__name__)

@app.route('/add', methods = ['GET', 'POST'])
def upload_file():
	if request.method == 'POST':
		print('POST Request detected', request)
		resp = request.get_json()

		if(request.get_json()['type'] == 'victim'):
			print("Adding a new victim")
			rahat_sql.add_new_victim('1', resp['number'], resp['lat_val'], resp['long_val'], resp['food'], resp['water'], resp['med'], resp['shelter'], resp['info'], resp['status'])

		elif(request.get_json()['type'] == 'help'):
			print("Adding a new help")
			rahat_sql.add_new_help('1', resp['number'], resp['lat_val'], resp['long_val'], resp['info'])

		elif(request.get_json()['type'] == 'shelter'):
			rahat_sql.add_new_shelters('1', resp['lat_val'], resp['long_val'], resp['people'], resp['info'])
			print("Adding a new shelter")

		elif(request.get_json()['type'] == 'tweet'):
			rahat_sql.add_new_tweet('1', resp['tweet_text'], resp['sentiment'])
			print("Adding a new tweet")

		response = app.response_class(response="Doing POST right!", status=200, mimetype="application/json")
		return response

	elif request.method == 'GET':
		print('GET Request detected', request)
		response = app.response_class(response="Doing GET right!", status=200, mimetype="application/json")
	return response

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8082)
    app.run(debug = True)

import logging
logging.basicConfig(level=logging.DEBUG)
import asyncio
import simpleobsws


def stream(server, key):
	parameters = simpleobsws.IdentificationParameters(ignoreNonFatalRequestChecks = False) # Create an IdentificationParameters object (optional for connecting)

	ws = simpleobsws.WebSocketClient(url = 'ws://localhost:4455', password = 'NaIf6BF33LEjtpgY', identification_parameters = parameters) # Every possible argument has been passed, but none are required. See lib code for defaults.

	async def make_request():
		await ws.connect() # Make the connection to obs-websocket
		await ws.wait_until_identified() # Wait for the identification handshake to complete
        #set stream url and key
		rdata = {
			"streamServiceType" : "rtmp_custom",
			"streamServiceSettings" : {
				"server" : str(server),
				"key" : str(key),
			}
		}
		request = simpleobsws.Request(requestType='SetStreamServiceSettings', requestData=rdata) # Build a Request object

		ret = await ws.call(request) # Perform the request
		if ret.ok(): # Check if the request succeeded
			print("Request succeeded! Response data: {}".format(ret.responseData))
		#start stream
		request = simpleobsws.Request(requestType='StartStream') # Build a Request object
		ret = await ws.call(request) # Perform the request
		if ret.ok(): # Check if the request succeeded
			print("Request succeeded! Response data: {}".format(ret.responseData))
		await ws.disconnect() # Disconnect from the websocket server cleanly

	loop = asyncio.get_event_loop()
	loop.run_until_complete(make_request())

stream("rtmps://1958e2d97d88.global-contribute.live-video.net:443/app/", "sk_us-east-1_tD4nNlTH1VCn_sqAmVP50G6GaafLWIrB4zfl8tvaFnv")

# TravelMate

---

# Endpoints:


## Get Hotel(s) based on Lat/Lon

	# POST /hotels

	# Request body	{ "lat lon,..." : "Hello World" }


#### Response 200 (application/json)
	
	{ "message" : "Hello World" }
	


## Book a Hotel

#### Request body (application/json)

	# POST /hotels
	
	# Request body	{ "lat lon,..." : "Hello World" }

#### Response 200 (application/json)

	{ "message" : "Hello World" }
	
	
## Hotel preferences

#### Request body (application/json)

	# POST /hotel_id/preferences
	
	# Request body	{ "lat lon,..." : "Hello World" }

#### Response 200 (application/json)

	{ "message" : "Hello World" }
	
	

# TravelMate REST API documentation
This document describes the REST API and resources provided by TravelMate.

## Resources
### /api/hotels
#### POST
Creates a new hotel.

*acceptable request representations:*

* application/json

##### request query parameters

| parameter        | value           | example  |
| ------------- |:-------------:| -----:|
| name      | String | Hotel Melia Düsseldorf |
| apiUrl      | String | http://192.168.243.230:80 |
| stars      | int | 4 |
| address      | Object | { "street": "Inselstraße 2", "city": "Düsseldorf", "zip": "40479", "country": "Germany" } |
| breakfastTypes      | Array | [ "european", "continental", "vegetarian" ] |
| loc      | [GeoJSON Point](http://geojson.org/geojson-spec.html#point) | { "type": "Point", "coordinates": [ 6.778368, 51.233145] } |
| suitableFor      | Object | { "Partygänger": true, "Geschäftsleute": true, "Singles": true, "Familien": true, "Hochzeitsreisende": false } |
| features      | Object | { "pool": true, "parking": true, "sauna": true } |
| distancesInKm      | Object | { "aiprort": 7, "cityCentre": 0.5 } |
| rooms      | Array | - |


##### example request
```
{
  "name": "Hotel Melia Düsseldorf",
  "apiUrl": "http://192.168.243.230:80",
  "stars": 4,
  "address": {
    "street": "Inselstraße 2",
    "city": "Düsseldorf",
    "zip": "40479",
    "country": "Germany"
  },
  "breakfastTypes": [
    "european",
    "continental",
    "vegetarian"
  ],
  "loc": {
    "type": "Point",
    "coordinates": [ 6.778368, 51.233145]
  },
  "suitableFor": {
    "Partygänger": true,
    "Geschäftsleute": true,
    "Singles": true,
    "Familien": true,
    "Hochzeitsreisende": false
  },
  "features": {
    "pool": true,
    "parking": true,
    "sauna": true
  },
  "distancesInKm": {
    "aiprort": 7,
    "cityCentre": 0.5
  },
  "rooms": [
    {
      "id": "room1",
      "numberOfAdults": 2,
      "costPerNightInEuro": 98.10,
      "roomNumber": 23,
      "floor": 10,
      "features": {
        "wifi": true,
        "minibar": true
      },
      "availability": {
        "from": "2015-06-27T18:25:43.511Z",
        "to": "2015-07-04T18:25:43.511Z"
      },
      "booked": true,
      "bookingDates": [
        {
          "from": "2015-06-27T18:25:43.511Z",
          "to": "2015-07-04T18:25:43.511Z"
        },
        {
          "from": "2015-06-27T18:25:43.511Z",
          "to": "2015-07-04T18:25:43.511Z"
        }
      ]
    },
    {
      "id": "room2",
      "numberOfAdults": 1,
      "costPerNightInEuro": 49.05,
      "roomNumber": 267,
      "floor": 10,
      "features": {
        "wifi": true,
        "minibar": false
      },
      "availability": {
        "from": "2015-06-27T18:25:43.511Z",
        "to": "2015-07-04T18:25:43.511Z"
      },
      "booked": false,
      "bookingDates": [
        {
          "from": "2015-06-27T18:25:43.511Z",
          "to": "2015-07-04T18:25:43.511Z"
        },
        {
          "from": "2015-06-27T18:25:43.511Z",
          "to": "2015-07-04T18:25:43.511Z"
        }
      ]
    }
  ]
}
```

#### GET
Returns all hotels.

### /api/hotels/search
#### POST
Finds the nearest hotel based on coordinates.

*acceptable request representations:*

* application/json

##### request query parameters

| parameter        | value           | example  |
| ------------- |:-------------:| -----:|
| loc      | [GeoJSON Point](http://geojson.org/geojson-spec.html#point) | { "type": "Point", "coordinates": [ 6.778368, 51.233145] } |
| filter.stars      | int | 4 |
| filter.features      | Object | { "pool": true, "parking": true, "sauna": true } |
| filter.distancesInKm      | Object | { "aiprort": 7, "cityCentre": 0.5 } |
| filter.ratings      | Object | { "average": { "min": 5, "max": 10 } } |
| filter.suitableFor      | Array | ["Partygänger", "Singles"] |

##### example request
```
{
  "loc": {
    "type": "Point",
    "coordinates": [ 6.778368, 51.233145]
  },
  "filter": {
    "stars": 4,
    "features": {
      "pool": true,
      "parking": true,
      "sauna": true
    },
    "distancesInKm": {
      "aiprort": 7,
      "cityCentre": 0.5
    },
    "ratings": {
      "average": {
        "min": 5,
        "max": 10
      }
    },
    "suitableFor": ["Partygänger", "Singles"]
  }
}
```

### /api/hotels/:hotel_id/book
#### POST
Book a room in a hotel.

*acceptable request representations:*

* application/json

##### request query parameters

| parameter        | value           | example  |
| ------------- |:-------------:| -----:|
| room_id      | String | room2 |
| guest      | Object | "name": { "nameFirst": "Hans", "nameLast": "Huber" } |

##### example request
```
{
  "room_id": "room2",
  "guest": {
    "name": {
      "nameFirst": "Hans",
      "nameLast": "Huber"
    }
  }
}
```

### /api/hotels/:hotel_id/service
#### POST
Lets the user send room service requests.

*acceptable request representations:*

* application/json

##### request query parameters

| parameter        | value           | example  |
| ------------- |:-------------:| -----:|
| room_id      | String | room2 |
| sendDate      | String | 2015-06-28T08:00:00.511Z |
| name      | String | wake-up service |

##### example request
```
{
  "room_id": "room2",
  "sendDate": "2015-06-28T08:00:00.511Z",
  "name": "wake-up service"
}
```

### /api/hotels/:hotel_id/reset/:service_id
#### POST
The hotel backend can remove service requests from the database.

*acceptable request representations:*

* application/json

##### request query parameters

| parameter        | value           | example  |
| ------------- |:-------------:| -----:|
| room_id      | String | room2 |

##### example request
```
{
    "room_id": "room2"
}
```

### /api/hotels/:hotel_id/reset
#### POST
Resets all booked rooms to free.
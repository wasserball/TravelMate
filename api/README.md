# TravelMate REST API documentation
This document describes the REST API and resources provided by TravelMate.

## Resources
### /api/hotels
#### POST
Creates a new hotel.

*acceptable request representations:*

* application/json

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

### /api/hotels/book
Book a room in a hotel.

*acceptable request representations:*

* application/json

##### request query parameters

| parameter        | value           | example  |
| ------------- |:-------------:| -----:|
| hotelId      | String | room2 |
| room_id      | String | 4 |
| guest      | Object | "name": { "nameFirst": "Hans", "nameLast": "Huber" } |

##### example request
```
{
  "hotelId": "558eba3aa3b86e043d40e2f2",
  "room_id": "room2",
  "guest": {
    "name": {
      "nameFirst": "Hans",
      "nameLast": "Huber"
    }
  }
}
```
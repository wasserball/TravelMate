var mongoose     = require('mongoose');
var Schema       = mongoose.Schema;

var HotelSchema   = new Schema({
    "name": String,
    "apiUrl": String,
    "stars": Number,
    "address": {
        "street": String,
        "city": String,
        "zip": String,
        "country": String
    },
    "breakfastTypes": Array,
    "loc": {
        "type": { type: String, default: "Point" },
        "coordinates": Array
    },
    "suitableFor": {
        "Partygänger": Boolean,
        "Geschäftsleute": Boolean,
        "Singles": Boolean,
        "Familien": Boolean,
        "Hochzeitsreisende": Boolean
    },
    "features": {
        "pool": Boolean,
        "parking": Boolean,
        "sauna": Boolean
    },
    "distancesInKm": {
        "aiprort": Number,
        "cityCentre": Number
    },
    "rooms": [
        {
            "id": String,
            "numberOfAdults": Number,
            "costPerNightInEuro": Number,
            "roomNumber": Number,
            "floor": Number,
            "guest": {
                "name": {
                    "nameFirst": String,
                    "nameLast": String
                },
                "stay": {
                    "from": { type: Date, default: Date.now },
                    "to": { type: Date, default: Date.now }
                },
                "tasks": Array
            },
            "features": {
                "wifi": Boolean,
                "minibar": Boolean
            },
            "availability": {
                "from": { type: Date, default: Date.now },
                "to": { type: Date, default: Date.now }
            },
            "booked": Boolean,
            "bookingDates": [
                {
                    "from": { type: Date, default: Date.now },
                    "to": { type: Date, default: Date.now }
                },
                {
                    "from": { type: Date, default: Date.now },
                    "to": { type: Date, default: Date.now }
                }
            ]
        }
    ]
});

module.exports = mongoose.model('Hotel', HotelSchema);
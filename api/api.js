var express = require("express");
var app = express();
var bodyParser = require("body-parser");
var mongoose   = require("mongoose");
var cors = require('cors');
var request = require('request');
mongoose.connect("localhost/travelmate");

var Hotel = require("./models/hotel");

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use(cors());

var port = process.env.PORT || 8080;
var router = express.Router();

router.get("/", function(req, res) {
    res.json({ message: "Welcome to the TravelMate API!" });
});

router.route("/hotels")

    .post(function(req, res) {
        var hotel = new Hotel(req.body);

        hotel.save(function(err) {
            if (err)
                res.send(err);

            res.json({ message: "hotel " + hotel.name + " created!" });
        });
    })

    .get(function(req, res) {
        Hotel.find(function(err, hotels) {
            if (err)
                res.send(err);

            res.json(hotels);
        });
    });

router.post("/hotels/search", function(req, res) {
        var guestLocation = req.body.loc;

        Hotel.findOne(
            {
                "loc": {
                    "$near": {
                        "$geometry": guestLocation,
                        "$maxDistance": 100
                    }
                },
                "rooms": {
                    "$elemMatch": { "booked": false }
                }
            }, {"name": 1, "rooms.$": 1 },
            function (err, hotel) {
                if (err)
                    res.send(err);

                res.json(hotel);
            }
        );
    });

function updateHotel(hotelId) {
    Hotel.findOne(
        {
            "_id": hotelId
        },
        function (err, hotel) {
            if (err)
                console.log(err);

            var url = hotel.apiUrl + "/newdata";

            request.post(url, function (error, response, body) {
                if (error) {
                    res.send(error);
                } else {
                    console.log("hotel " + hotel.name +  " updated");
                }
            });
        }
    );
}

router.post("/hotels/:hotel_id/book", function(req, res) {

    var hotelId = req.params.hotel_id;

    Hotel.update(
        {
            "_id": req.params.hotel_id,
            "rooms.id": req.body.room_id
        },
        { $set: { "rooms.$.booked": true } },
        function (err, raw) {
            if (err)
                res.send(err);

            updateHotel(hotelId);

            var responseMessage = "Room " + req.body.room_id + " in hotel with id " + req.body.hotelId + " booked.";
            res.json({ message: responseMessage });
        }
    );

});

router.post("/hotels/:hotel_id/reset", function (req, res) {
    Hotel.update(
        {
            "_id": req.params.hotel_id,
            "rooms": { "$elemMatch": { booked: true } }
        },
        { $set: { "rooms.$.booked": false } },
        function (err, raw) {
            if (err)
                res.send(err);

            res.json(raw);
        }
    );
});

router.post("/hotels/:hotel_id/service", function (req, res) {
    // res.json(req.params.hotel_id);

    Hotel.findOne(
        {
            "_id": req.params.hotel_id
        },
        function (err, hotel) {
            if (err)
                res.send(err);

            res.json(hotel.apiUrl);
        }
    );

    //Hotel.update(
    //    {
    //        "_id": req.body.hotelId,
    //        "rooms": { "$elemMatch": { booked: true } }
    //    },
    //    { $set: { "rooms.$.booked": false } },
    //    function (err, raw) {
    //        if (err)
    //            res.send(err);
    //
    //        res.json(raw);
    //    }
    //);
});

app.use("/api", router);

app.listen(port);
console.log("TravelMate API started on " + port);
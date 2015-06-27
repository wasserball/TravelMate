var express = require("express");
var app = express();
var bodyParser = require("body-parser");
var mongoose   = require("mongoose");
mongoose.connect("localhost/travelmate");

var Hotel = require("./models/hotel");

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

var port = process.env.PORT || 8080;
var router = express.Router();

router.get("/", function(req, res) {
    res.json({ message: "Welcome to the TravelMate API!" });
});

router.route('/hotels')

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

router.post('/hotels/search', function(req, res) {
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

router.post('/hotels/book', function(req, res) {
    Hotel.update(
        {
            "_id": req.body.hotelId,
            "rooms.id": req.body.room_id
        },
        { $set: { "rooms.$.booked": true } },
        function (err, raw) {
            if (err)
                res.send(err);

            res.json(raw);
        }
    );

});

app.use("/api", router);

app.listen(port);
console.log("TravelMate API started on " + port);
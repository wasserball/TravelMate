(function () {
    "use strict";

    var express = require("express");
    var app = express();
    var bodyParser = require("body-parser");
    var mongoose = require("mongoose");
    var cors = require("cors");
    var request = require("request");
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
                if (err) { return res.send(err); }
                console.log(hotel.name + " created!\n");
                res.json({ message: hotel.name + " created!" });
            });
        })

        .get(function(req, res) {
            Hotel.find(function(err, hotels) {
                if (err) { return res.send(err); }

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
                if (err) { return res.send(err); }
                console.log("Nearest hotel found was: " + hotel.name + "\n");
                res.json(hotel);
            }
        );
    });

    function updateHotel(hotelId, path) {
        Hotel.findOne(
            {
                "_id": hotelId
            },
            function (err, hotel) {
                if (err) { return res.send(err); }

                var url = hotel.apiUrl + path;

                request.post(url, function (error, response, body) {
                    if (error) {
                        return res.send(error);
                    } else {
                        console.log("hotel " + hotel.name + " updated" + "\n");
                    }
                });
            }
        );
    }

    router.post("/hotels/:hotel_id/book", function(req, res) {

        var hotelId = req.params.hotel_id;

        Hotel.update(
            {
                "_id": hotelId,
                "rooms.id": req.body.room_id
            },
            { $set: { "rooms.$.booked": true, "rooms.$.guest.name": req.body.guest.name } },
            function (err) {
                if (err) { return res.send(err); }

                updateHotel(hotelId, "/add");

                var responseMessage = "Room " + req.body.room_id + " in hotel with the id " + hotelId + " was booked.\n";
                console.log(responseMessage);

                res.json({ message: responseMessage });
            }
        );

    });

    router.post("/hotels/:hotel_id/service", function (req, res) {

        var hotelId = req.params.hotel_id;

        var service = {
            "name": req.body.name,
            "sendDate": req.body.sendDate,
            "id": mongoose.Types.ObjectId()
        };

        Hotel.update(
            {
                "_id": hotelId,
                "rooms.id": req.body.room_id
            },
            { "$push": { "rooms.$.guest.tasks": service } },
            function (err, raw) {
                if (err) { return res.send(err); }

                updateHotel(hotelId, "/update");

                var responseMessage = "Room " + req.body.room_id + " in hotel with id " + hotelId + " has a new service registered.\n";
                console.log(responseMessage);

                res.json({ message: responseMessage });
            }
        );

    });

    router.post("/hotels/:hotel_id/reset/:service_id", function (req, res) {

        var hotelId = req.params.hotel_id;
        var serviceId = req.params.service_id;
        var roomId = req.body.room_id;

        Hotel.findOne(hotelId,
            function (err, hotel) {
                if (err) { return res.send(err); }

                var rooms = hotel.rooms;

                for (var i = 0; i < rooms.length; i++) {
                    if (hotel.rooms[i].id == roomId) {
                        var tasks = hotel.rooms[i].guest.tasks;

                        for (var j = 0; j < tasks.length; j++) {
                            if (tasks[j].id == serviceId) {
                                hotel.rooms[i].guest.tasks.splice(j, 1);
                            }
                        }
                    }
                }

                hotel.save(function(err) {
                    if (err) { return res.send(err); }

                    updateHotel(hotelId, "/remove");

                    var resetMessage = hotel.name + " cleared service with the id: " + serviceId + "!\n";
                    console.log(resetMessage);
                    res.json({ message: resetMessage });
                });
            }
        );
    });

    router.post("/hotels/:hotel_id/reset", function (req, res) {

        var hotelId = req.params.hotel_id;
        var roomId = req.body.room_id;

        Hotel.update(
            {
                "_id": hotelId,
                "rooms": { "$elemMatch": { id: roomId } }
            },
            { $set: { "rooms.$.booked": false, "rooms.$.guest.tasks": [] } },
            function (err, raw) {
                if (err) { return res.send(err); }

                updateHotel(hotelId, "/remove");

                var resetMessage = "Bookings for hotel with id " + hotelId + " were reset!\n";
                console.log(resetMessage);

                res.json(raw);
            }
        );
    });

    app.use("/api", router);

    app.listen(port);
    console.log("TravelMate API started on " + port);
}());


# TravelMate - WWW

---

the Frontend-Service runs on ```Port 80 ``` . To start she Server, type ``` sudo node index.js ```. The Frontend should be available under ``` http://localhost/ ```

### Technology(s)

- [node.js](https://nodejs.org/)
- [WebSockets - socker.io](http://socket.io/)
- [Handlebars](http://handlebarsjs.com/)
- [Material design](http://materializecss.com/)

# Endpoints:

Pushes the Hotel-Update from the API-Service (api-Folder) to the Frontend via WebSockets.

## load new Hotel Data if they have changed

	# POST /update

	# io.sockets.emit("update")	


## add new Booking

	# POST /add

	# io.sockets.emit("add")	


	
## remove Task / Booking

	# POST /remove

	# io.sockets.emit("remove")	

	

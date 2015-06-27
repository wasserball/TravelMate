/*{{formatDate this.guest.stay.from day="numeric" month="long" year="numeric"}} - 
            {{formatDate this.guest.stay.to day="numeric" month="long" year="numeric"}} */

$( document ).ready(function() {


	var socket = io();
	var allHotelsUrl = 'http://192.168.241.250:8080/api/hotels';
	HandlebarsIntl.registerWith(Handlebars);

	socket.on('update', function(){

		// get 

		Materialize.toast('<i class="material-icons yellow">trending_flat</i> <span> &nbsp;&nbsp; new Task added', 3000);

	  	getRooms();
	});



	socket.on('remove', function(){
		Materialize.toast('<i class="material-icons red">delete</i> <span> &nbsp;&nbsp; cancle Booking', 3000);
		getRooms();
	});

	socket.on('add', function(){
		Materialize.toast('<i class="material-icons green">add</i> <span> &nbsp;&nbsp; new Booking', 3000);
		getRooms();
	});

	

	function initCollapsible () {
		$('.collapsible').collapsible({
      accordion : false // A setting that changes the collapsible behavior to expandable instead of the default accordion style
    });
	}

	$( "#main" ).on( "change", "input", function() {

		var hotelId = $(this).closest('.hotel-id').attr('id');
		var taskId = $( this ).attr('id');

		$.ajax({
      type: "POST",
      url: allHotelsUrl,
      data: {"hotelId":hotelId, "taskId": taskId},
      contentType: "application/json",
      success: function( response ){

      },
      error: function( error ){
          // Log any error.
          console.log( "ERROR:", error );
      }
	  });


	});
		
	function getRooms () {

		$.ajax({
      type: "GET",
      url: allHotelsUrl,
      contentType: "application/json",
      success: function( response ){
      	console.log(response);
      },
      error: function( error ){
          // Log any error.
          console.log( "ERROR:", error );
      }
	  });

	}

	function renderRooms (hotel) {

		console.log(hotel.rooms);

		var source   = $("#hotel-template").html();
		var template = Handlebars.compile(source);
		var context = {url: "img/hotels/melia.jpeg", name: hotel.name, location: hotel.address};
		var html    = template(context);
		$('#hotel').html(html);

		source   = $("#hotelrooms-template").html();
		template = Handlebars.compile(source);
		var total = hotel.rooms.length;
		var booked = 0;
		var free = 0;
		for(var i = 0; i < hotel.rooms.length; i++){
			if(hotel.rooms[i].booked == true){
				booked++;
			}else {
				free++;
			}
		}
		context = {total: total, booked: booked, free: free };
		html    = template(context);
		$('#hotelrooms').html(html);




		source   = $("#hotel-entry-template").html();
		template = Handlebars.compile(source);
		context = {rooms: hotel.rooms};
		html    = template(context);
		$('#hotel-entry').html(html);

		initCollapsible();
	}

	
	getRooms();


	

});
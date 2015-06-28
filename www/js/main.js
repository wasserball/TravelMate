$(document).ready(function () {

	var socket = io();
	var allHotelsUrl = 'http://192.168.241.250:8080/api/hotels';
	HandlebarsIntl.registerWith(Handlebars);

	socket.on('update', function(){
		$.playSound('sounds/reception_bell');
		Materialize.toast('<i class="material-icons medium font-white">recent_actors</i> <span> &nbsp;&nbsp; New Task added', 30000);
	  	getRooms();
	});

	socket.on('remove', function(){
		$.playSound('sounds/recycle_bin');
		Materialize.toast('<i class="material-icons medium font-white">delete</i> <span> &nbsp;&nbsp; Status Updated', 30000);
		getRooms();
	});

	socket.on('add', function(){
		$.playSound('sounds/cash_register');
		Materialize.toast('<i class="material-icons medium font-white">payment</i> <span> &nbsp;&nbsp; New Booking', 30000);
		getRooms();
	});

	function initCollapsible () {
		$('.collapsible').collapsible({
      accordion : false
    });
	}

	$( "#main" ).on( "change", "input", function() {
		var hotelId = $(this).closest('.hotel-id').attr('id');
		var roomId = $(this).closest('.room-id').attr('id'); 
		var taskId = $( this ).attr('id');
		var url = allHotelsUrl + '/' + hotelId + '/reset/' + taskId;
		var data = JSON.stringify({"room_id": roomId});

		$.ajax({
      type: "POST",
      url: url,
      data: data,
      contentType: "application/json",
      success: function( response ){
      	var counter = $('#'+taskId).closest('.room-id').find('.secondary-content');
      	counter.html(parseInt(counter.html()) - 1);
      	if(response){
      		$('#'+taskId).closest('li').slideUp( 1000, function() {
				    $('#'+taskId).closest('li').remove();
				  });
      	}
      },
      error: function( error ){
          // Log any error.
          console.log( "ERROR:", error );
      }
	  });
	});
	
	function getRooms () {
		var currentOpenId = $('.room-id.active').attr('id');
		$.ajax({
      type: "GET",
      url: allHotelsUrl,
      contentType: "application/json",
      success: function( response ){
      	renderRooms(response[0], currentOpenId);
      },
      error: function( error ){
          // Log any error.
          console.log( "ERROR:", error );
      }
	  });

	}

	function renderRooms (hotel, id) {
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
		hotel.rooms = hotel.rooms.sort(function(a, b){
	    var keyA = new Date(a.roomNumber),
		    keyB = new Date(b.roomNumber);
		    // Compare the 2 dates
		    if(keyA < keyB) return -1;
		    if(keyA > keyB) return 1;
		    return 0;
		});

		console.log(hotel.rooms);
		context = {total: total, booked: booked, free: free };
		html    = template(context);
		$('#hotelrooms').html(html);
		source   = $("#hotel-entry-template").html();
		template = Handlebars.compile(source);
		context = {rooms: hotel.rooms, hotelid: hotel._id};
		html    = template(context);
		$('#hotel-entry').html(html);
		initCollapsible();

		if(id !== undefined){
			var open = $('#'+id);
			open.addClass('active');
			open.find('.collapsible-header').addClass('active');
			open.find('.collapsible-body').show();
		}
	}

	getRooms();

});
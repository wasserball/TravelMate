var socket = io();

socket.on('hotel message', function(user){

	var user = $('#'+user.id);

	var userToDos = user.find('.secondary-content');
	var currentValue = parseInt(userToDos.html());
	userToDos.html(currentValue + 1);

  Materialize.toast('<span>12 - Max Mustermann <br>' + user.type + '</span>', 30000)

  //$('#messages').append($('<li>').text(msg));
});
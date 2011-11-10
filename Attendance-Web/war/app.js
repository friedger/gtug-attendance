$(function() {

	window.Event = Backbone.Model.extend({
		
	});
	
	window.EventList = Backbone.Model.extend({
		model: Event,
		
	});
	
	window.Events = new EventList;
	
	$.get('events', function(data){
		
		console.log(data);
		
	});
	
});
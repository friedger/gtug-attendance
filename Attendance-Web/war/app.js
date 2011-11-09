$(function() {

	window.Event = Backbone.Model.extend({
		
	});
	
	window.EventList = Backbone.Model.extend({
		model: Event,
		
		
	});
	
	$.get('events', function(data){
		
		console.log(data);
		
	});
	
});
$(document).ready(function(){
    
    // start with all boxes hidden
    $('.box').hide();
        
    // make each gray bar open a certain box 
	$('#b1').on('click', function() {
		$('.one').slideToggle('fast');
	});
	$('#b2').on('click', function() {
		$('.two').slideToggle('fast');
	});
	$('#b3').on('click', function() {
		$('.three').slideToggle('fast');
	});
	$('#b4').on('click', function() {
		$('.four').slideToggle('fast');
	});    
    
    
});

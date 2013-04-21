<script src="https://maps.googleapis.com/maps/api/js?sensor=true&language=es" type="text/javascript"></script>
<script type="text/javascript">

var drawingManager;
var map;
var stgo;
var marker = null;
var infoWindow = null;

$(document).ready(function() {
	
});

function initialize() {
	stgo = new google.maps.LatLng(-33.252484126565165, -70.65305117187495);
	var mapOptions = {
		//center: stgo,
		zoom: 15,
		mapTypeId: google.maps.MapTypeId.ROADMAP,
		scaleControl: true,
		scrollwheel	: false
	};
	map = new google.maps.Map(document.getElementById('map_canvas'), mapOptions);
	
	// Try HTML5 geolocation
	if(navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(
			function(position) {
				var pos = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
				map.setCenter(pos);
				addMarker(pos);
			},
			function() {
				handleNoGeolocation(true);
			}
		);
	} else {
		handleNoGeolocation(false);
	}
}

function handleNoGeolocation(errorFlag) {		
	if (errorFlag === true) {
		//enable geo
	} else {
		// browser has no geo
	}
	addMarker(stgo);
}

function addMarker(point) {
	if (typeof point != "undefined") {
		point = map.getCenter();	
	}

	marker = new google.maps.Marker({
		map: map,
		position: point,
		draggable: true
	});

	google.maps.event.addListener(marker, 'dragstart', markerDragStart);
	google.maps.event.addListener(marker, 'dragend', markerDragEnd);
	
	var infoWindow = new google.maps.InfoWindow({
		position: marker.getPosition(),
		content: '<div class="centered"><button class="btn btn-large btn-primary" onclick="searchImages();">Show me the sky!</button></div>',
	});
	
	infoWindow.open(map, marker);
	
	google.maps.event.addListener(marker, 'click', function(event) {
        infoWindow.open(map, marker);
	});
}

function markerDragStart(event) {
	 infoWindow.close();
}

function markerDragEnd(event) {
	infoWindow.open(map, marker);
}

google.maps.event.addDomListener(window, 'load', initialize);

function searchImages() {
	markerPosition = marker.getPosition();
	var postUrl = "<?=base_url();?>api/getIt?lat=" + markerPosition.lat() + "&lng=" + markerPosition.lng() + "&d=1";
	$.ajax({
		async: true,
		beforeSend: function (XMLHttpRequest) {
			$("#image_container").addClass("loading");
			$("#space_image").hide();
		},
		complete: function (XMLHttpRequest) {
			$("#image_container").removeClass("loading");
			$("#space_image").show();
		},
		buffer: false,
		data: $("#report_add").closest("form").serialize(),
		inline: true,
		success: function (data, textStatus) {
			if (data != "") {
				if (!$.isEmptyObject(data) && data.status == 1) {
					$("#space_image").attr("src", "http://skyview.gsfc.nasa.gov/tempspace/fits/" + data.image + ".jpg");
				}
			}
		},
		type: "POST",
		url: postUrl
	});
}

</script>


/**
 * Use code of bgImageTween by Toni Anzlovar See
 * http://soxer.mutsu.org/offshots/bgImageTween
 */
$(document).ready(function() {

	var imageCount = 17;
	var staytime = 3000;
	var fadetime = 1000;

	var images = [];
	for ( var i = 2; i <= imageCount; i++) {
		images.push('/image/bg' + i + '.jpg');
	}

	var fader = $("#backImageFader");
	var setBgImage = function() {

		// Show fader.
		fader.queue(function() {
			var image = images.shift();
			images.push(image);
			fader.css('backgroundImage', 'url(' + image + ")");
			$(this).dequeue();
		});

		fader.animate({
			opacity : 1
		}, fadetime);
		fader.delay(staytime);

		// Show parent.
		fader.queue(function() {
			var image = images.shift();
			images.push(image);
			fader.parent().css('backgroundImage', 'url(' + image + ")");
			$(this).dequeue();
		});

		fader.animate({
			opacity : 0
		}, fadetime);
		fader.delay(staytime);

		// Loop.
		setBgImage();
	};
	setBgImage();
});

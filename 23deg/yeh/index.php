<?php
error_reporting(E_ALL);
$goto = !empty($_GET['goto']) ? $_GET['goto'] : '';
$action = 'home';
switch ($goto) {
	case 'play': {
		$action = 'play';
		break;
	}
	case 'nono': {
		$action = 'nono';
		break;
	}
	case 'wat': {
		$action = 'wat';
		break;
	}
}
?>
<html>
	<head>		
	</head>
	<body>
		<h1><?php echo $action; ?></h1>
	</body>
</html>
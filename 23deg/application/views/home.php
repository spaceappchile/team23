<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>23° - Astral Finder</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
	<link href="<?=base_url();?>css/bootstrap.min.css" rel="stylesheet">
    <link href="<?=base_url();?>css/bootstrap-responsive.min.css" rel="stylesheet">
    <link href="<?=base_url();?>css/23deg.css" rel="stylesheet">

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="../assets/js/html5shiv.js"></script>
    <![endif]-->
	
	

	<script type="text/javascript" src="<?=base_url();?>js/jquery.min.js"></script>
	<script type="text/javascript" src="<?=base_url();?>js/bootstrap.min.js"></script>
	<script type="text/javascript" src="<?=base_url();?>js/holder.js"></script>

    <!-- Fav and touch icons -->
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="../assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="../assets/ico/apple-touch-icon-114-precomposed.png">
      <link rel="apple-touch-icon-precomposed" sizes="72x72" href="../assets/ico/apple-touch-icon-72-precomposed.png">
                    <link rel="apple-touch-icon-precomposed" href="../assets/ico/apple-touch-icon-57-precomposed.png">
                                   <link rel="shortcut icon" href="../assets/ico/favicon.png">
  </head>

  <body>
	<div class="band band-header">
		<div id="header" class="container">
			<ul id="main_nav">
				<li class="active"><a href="#">Inicio</a></li>
                <li><a href="#get_the_app">App móvil</a></li>
                <li><a href="#this_is_the_api">API</a></li>
                <li><a href="#this_is_the_team">Equipo</a></li>
			</ul>
			<img id="logo" src="<?=base_url();?>img/23deg_logo.png" alt="23 degree">
		</div>
	</div>

	<div class="container marketing">
		
		<ul class="nav nav-tabs">
			<li class="active"><a href="#tab1" data-toggle="tab">Mapa</a></li>
			<li><a href="#tab2" data-toggle="tab">Usuarios avanzados</a></li>
		</ul>
		<div class="tab-content">
			<div class="tab-pane active" id="tab1">
				<?php $this->load->view('map'); ?>
			</div>
			<div class="tab-pane active" id="tab2">
				hola
			</div>
		</div>
		
	
	
	<!-- START THE FEATURETTES -->

      <hr class="featurette-divider">

      <div id="get_the_app" class="featurette">
        <img class="featurette-image pull-right" src="../assets/img/examples/browser-icon-chrome.png">
        <h2 class="featurette-heading">App móvil. <span class="muted">El espacio en tu celular!</span></h2>
        <p class="lead">Donec ullamcorper nulla non metus auctor fringilla. Vestibulum id ligula porta felis euismod semper. Praesent commodo cursus magna, vel scelerisque nisl consectetur. Fusce dapibus, tellus ac cursus commodo.</p>
      </div>

      <hr class="featurette-divider">

      <div id="this_is_the_api" class="featurette">
        <img class="featurette-image pull-right" src="../assets/img/examples/browser-icon-chrome.png">
        <h2 class="featurette-heading">API. <span class="muted">Construye aplicaciones en base a SkyMorph!</span></h2>
        <p class="lead">Donec ullamcorper nulla non metus auctor fringilla. Vestibulum id ligula porta felis euismod semper. Praesent commodo cursus magna, vel scelerisque nisl consectetur. Fusce dapibus, tellus ac cursus commodo.</p>
      </div>

      <hr class="featurette-divider">

      <div id="this_is_the_team" class="featurette">
        <img class="featurette-image pull-left" src="../assets/img/examples/browser-icon-firefox.png">
        <h2 class="featurette-heading">Equipo. <span class="muted">See for yourself.</span></h2>
        <p class="lead">Donec ullamcorper nulla non metus auctor fringilla. Vestibulum id ligula porta felis euismod semper. Praesent commodo cursus magna, vel scelerisque nisl consectetur. Fusce dapibus, tellus ac cursus commodo.</p>
      </div>
	
		<hr class="featurette-divider">
      <!-- FOOTER -->
      <footer>
        <p class="pull-right"><a href="#">Back to top</a></p>
        <p>&copy; 2013 El número 23 &middot; <a href="#">Privacy</a> &middot; <a href="#">Terms</a></p>
      </footer>

    </div><!-- /.container -->
	
	<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-header">
	  <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	  <h3 id="myModalLabel">Imagen</h3>
	</div>
	<div class="modal-body">
	  
	</div>
	<div class="modal-footer">
	  <button class="btn" data-dismiss="modal" aria-hidden="true">Cerrar</button>
	</div>
  </div>
  </body>
</html>

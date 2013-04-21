<?php if(! defined('BASEPATH')) exit('No direct script access allowed');

class Lex extends CI_Controller {
	
	function __construct() {
        parent::__construct();
		$this->load->helper(array('html', 'url'));
    }

	public function index()	{
		$this->load->view('home');
	}
	
	public function image()	{
		$data = array('image' => '');
		if (!empty($_GET['lat']) && !empty($_GET['lng'])) {
			$parameters = array('lat' => $_GET['lat'], 'lng' => $_GET['lng']);
			if (!empty($parameters)) {
				$imageUrl = $this->getImagePageUrl($parameters);
				$skyResponse = $this->queryServer($imageUrl);
	
				if (($image = $this->extractSingleImage($skyResponse)) !== '') {
					$data['image'] = $image;
				}
			}
		}
		$this->load->view('image', $data);
	}

	public function api_news() {
		$lat = -26.745624338061198;
		echo ' lat: ' . $lat .' => ' . $this->convertToRA($lat) . '<br />';
		$lng = -210.93747499999992;
		echo ' lng: ' . $lng .' => ' . $this->convertToDEC($lng) . '<br />';
		
		$this->load->view('home');
	}

	public function api_getIt() {
		$response = array('status' => '', 'message' => '');
		if (!empty($_GET['lat']) && !empty($_GET['lng'])) {
			$parameters = array('lat' => $_GET['lat'], 'lng' => $_GET['lng']);
		} elseif (!empty($_GET['ra']) && !empty($_GET['dec'])) {
			$parameters = array('ra' => $_GET['ra'], 'dec' => $_GET['dec']);			
		}
		if (!empty($parameters)) {
			$imageUrl = $this->getImagePageUrl($parameters);
			$skyResponse = $this->queryServer($imageUrl);

			if (($image = $this->extractSingleImage($skyResponse)) !== '') {
				$response['image'] = $image;
				$response['status'] = 1;
				$response['message'] = 'Imagen encontrada.';
			} else {
				$response['status'] = 0;
				$response['message'] = 'Imagen no encontrada.';				
			}
		} else {
			$response['status'] = 0;
			$response['message'] = 'Parametros no inicializados.';			
		}
		$this->output
			->set_content_type('application/json')
			->set_output(json_encode($response)
		);
	}

	private function queryServer($requestUrl, $postValues = null) {
		$request = curl_init($requestUrl);
		curl_setopt($request, CURLOPT_HEADER, 0);
		curl_setopt($request, CURLOPT_RETURNTRANSFER, 1);
		curl_setopt($request, CURLOPT_SSL_VERIFYPEER, FALSE);

		if ($postValues) {
			$valueString = "";
			foreach($postValues as $key => $value) {
				$valueString .= "$key=" . urlencode( $value ) . "&";
			}
			$valueString = rtrim( $valueString, "& " );
			curl_setopt($request, CURLOPT_POSTFIELDS, $valueString); // use HTTP POST to send form data
			curl_setopt($request, CURLOPT_POST, 1);
		}

		$response = curl_exec($request);
		curl_close ($request);
		return $response;
	}
	
	private function getImagePageUrl($parameters) {
		if (!empty($parameters['lat']) && !empty($parameters['lng'])) {
			$rightAscension = str_replace(' ', '+', $this->convertToRA($parameters['lat']));
			$declination = str_replace(' ', '+', $this->convertToDEC($parameters['lng']));			
		} elseif (!empty($parameters['ra']) && !empty($parameters['dec'])) {
			$rightAscension = str_replace(' ', '+', $parameters['ra']);
			$declination = str_replace(' ', '+', $parameters['dec']);
		}

		return 'http://skyview.gsfc.nasa.gov/cgi-bin/runquery.pl?Interface=quick&Position=' . $rightAscension . '+' . $declination . '&SURVEY=Digitized+Sky+Survey';
		
	}
	
	private function extractSingleImage($response) {	
		$matches = array();
		preg_match('/fits\/(.*)\.(jpg|fits)/', $response, $matches);
		if (!empty($matches[1])) {
			return $matches[1];
		} else {
			return '';
		}
		
	}
	
	private function convertToRA($lng) {
		if ($lng > 180) {
			$lng -= 360;
		}
		$result = '';		
		$parts = $this->getNumberComponents($lng / 15);
		$result .= sprintf("%02d", abs($parts[0]));
		$parts = $this->getNumberComponents($parts[1] * 60);		
		$result .= ' ' . sprintf("%02d", abs($parts[0]));
		$parts = $this->getNumberComponents($parts[1] * 60);
		
		$result .= ' ' . sprintf("%02d", abs($parts[0]));
		
		return $result;
	}
	
	private function convertToDEC($lat) {
		$result = '';		
		$parts = $this->getNumberComponents($lat);		
		$result .= sprintf("%02d", $parts[0]);
		$parts = $this->getNumberComponents(($parts[1] * 60) / 15);
		$result .= ' ' . sprintf("%02d", abs($parts[0]));
		$parts = $this->getNumberComponents(($parts[1] * 60) / 15);
		$result .= ' ' . sprintf("%02d", abs($parts[0]));
		return $result;
	}
	
	private function getNumberComponents($number) {
		$number = number_format($number, 8);
		$intpart = intval($number);		
		return array($intpart, $number - $intpart);
    }
	
	private function getFloat($number) {
		$number = number_format($number, 1);
		$intpart = floor($number);
		
		return $number - $intpart;
    }
	
	private function loco($number, &$here = array()) {
		$number = number_format($number, 1);
		$intpart = floor($number);
		$fraction = $number - $intpart;
		echo $intpart . '-' . $fraction . '<br />';
		
		if (!empty($fraction)) {
			$here[] = $this->loco($fraction, $here);			
		} else {
			return null;
		}
	}
	
	private function getNumbersComponents($number) {
		$number = number_format($number, 1);
		$intpart = floor($number);
		
		$fraction = $number - $intpart;
		
		return array($intpart, $number - $intpart);
    }
}
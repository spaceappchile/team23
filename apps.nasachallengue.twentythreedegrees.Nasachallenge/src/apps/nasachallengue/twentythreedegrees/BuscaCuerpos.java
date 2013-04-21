package apps.nasachallengue.twentythreedegrees;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class BuscaCuerpos extends Activity implements OnItemSelectedListener, OnItemClickListener {
	
	Spinner spnType;
	ListView lstVistas;
	ImageView imgTitulo;
	Bitmap bmTitulo;
	String [] space_objects = new String []{"Asteroids", "Comets", "Planetary Satellites", "Planets"};
	String [] asteroides = new String []{"Asclepius", "1988 XB"};
	String [] cometas = new String []{"Orpheus", "1982 XB"};
	String [] satelites = new String []{"Moon", "Io", "Europa", "Ganymede", "Triton", "Ariel"};
	String [] planetas = new String []{"Mercury", "Venus", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune"};
	ArrayAdapter asteroideAdapter, cometaAdapter, satelitesAdapter, planetasAdapter;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscar_cuerpo);
        spnType = (Spinner) findViewById(R.id.spnTipo);
        lstVistas = (ListView) findViewById(R.id.lstVista);
        imgTitulo = (ImageView) findViewById(R.id.imgTituloBuscar);
        
        Display display = getWindowManager().getDefaultDisplay();
		
        bmTitulo = BitmapFactory.decodeResource(getResources(), R.drawable.selecciona);

		int ancho = display.getWidth();
		int alto = display.getHeight();
		
		bmTitulo = Bitmap.createScaledBitmap(bmTitulo, (ancho*100)/100, (alto*10)/100, true);
		
		imgTitulo.setImageBitmap(bmTitulo);
        
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, space_objects);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		asteroideAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, asteroides);
		cometaAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, cometas);
		satelitesAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, satelites);
		planetasAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, planetas);
		
		spnType.setAdapter(spinnerArrayAdapter);
		
		spnType.setOnItemSelectedListener(this);
		lstVistas.setOnItemClickListener(this);
    }


	public void onItemSelected(AdapterView<?> parentView, View selectedItem, int position, long id) 
	{//Toast.makeText(this, position+"", Toast.LENGTH_SHORT).show();
		switch (position)
		{
			case 0:
				lstVistas.setAdapter(asteroideAdapter);
			break;
			case 1:
				lstVistas.setAdapter(cometaAdapter);
			break;
			case 2:
				lstVistas.setAdapter(satelitesAdapter);
			break;
			case 3:
				lstVistas.setAdapter(planetasAdapter);
			break;
		}
		
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onItemClick(AdapterView<?> parentView, View selectedItem, int position, long id) 
	{
		//Toast.makeText(this, arg0.getItemAtPosition(arg2)+"", Toast.LENGTH_SHORT).show();
		try
		{
			Intent i = new Intent("apps.nasachallengue.twentythreedegrees.ImagenCuerpoBuscado");
			i.putExtra("CuerpoCeleste", parentView.getItemAtPosition(position)+"");
			startActivity(i);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Toast.makeText(this, "Error al buscar asteriode", Toast.LENGTH_SHORT).show();
		}
		
	}
    
}

package alexis.bat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.database.Cursor;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Main2 extends Activity{
	String[] _rutas={"Puerto Armuelles","Cerro Punta","Alanje","Gualaca","Dolega","Frontera Paso Canoas","San Andrés",""};
	BDAdapter db = new BDAdapter(this);
	Creditos soli = new Creditos();
	String tag = "evento", _lsRuta, rutas; 
	Button map;
	private ListView lista;
	TextView rutaSeleccionada; 
	private ArrayAdapter<String>listaAdapter;
	long id;
	String[] _idiomaBase;
	String[] _idiomaEs= {"Lenguaje","Creditos","Salir"};
	String[] _idiomaEn= {"Language","credits","Exit"};
	CharSequence[] items = {"Español", "Ingles"};
	boolean[] itemsChecked = new boolean [items.length];
	boolean[] itemsRutas = new boolean [_rutas.length];
	private static final int MENU1 = Menu.FIRST +1;
	private static final int MENU2 = Menu.FIRST +2;
	private static final int MENU3 = Menu.FIRST +3;
	Toast msg;
	
	 public void DisplayRutas(Cursor c, int indice)
     {
		 
		 _rutas[indice]=c.getString(1).toString();
     	//Toast.makeText(this, "id: " + c.getString(0) + "\n" +"Name: " + c.getString(1) + "\n" , Toast.LENGTH_LONG).show();
     	
     }
	 public void InsertarListaDeRutas()
	 {
		 for (int i =0;i<itemsRutas.length;i++)
	        {
	        	String rut = _rutas[i];
	        	Log.d(tag,"insertando ruta: "+ rut);
	        	 id= db.insertRutas(rut);
	        }
	 }
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.main2);
       db.open();

        lista = (ListView)findViewById(R.id.listView1);
        
       
        Log.d(tag,"id ruta: "+ id );
         Cursor c = db.getAllRutas();
        if (c.moveToFirst())
        {int a=0;
        	do 
        	{
        		
        		DisplayRutas(c, a);
        		a++;
        	} while (c.moveToNext());
        }
        db.close();
        _idiomaBase= _idiomaEs;
       /* ListView ls = getListView();
        ls.setChoiceMode(2);
        ls.setTextFilterEnabled(true);
        
         setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, _rutas));
         */ArrayList<String> rutaLista = new ArrayList<String>();
        rutaLista.addAll(Arrays.asList(_rutas));
        
        listaAdapter = new ArrayAdapter<String>(this, R.layout.listas, rutaLista);
        rutaSeleccionada = (TextView)findViewById(R.id.textView1);
        lista.setTextFilterEnabled(true);
        lista.setOnItemClickListener(new OnItemClickListener(){
        	public void onItemClick(AdapterView<?> parent, View view, int  position, long id)
        	{
        		//Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
        		_lsRuta = _rutas[position];
        		Bundle extras = new Bundle();
        		extras.putString("ruta", _lsRuta.toString());
        		
        		//Toast.makeText(this, _lsRuta, Toast.LENGTH_SHORT).show();
        		Intent a = new Intent(Main2.this, Main3.class);
        		a.putExtras(extras);
        		
        		startActivity(a);
        	}
        });
        //listaAdapter.add("hola");
        lista.setAdapter(listaAdapter);
        

    }


	 @Override
     public boolean onPrepareOptionsMenu(Menu menu)
     {
		 menu.clear();
		 Log.d(tag, "Menus Options");
          menu.add(Menu.NONE, MENU1, Menu.NONE, _idiomaBase[0]).setIcon(R.drawable.ic_launcher);
          menu.add(Menu.NONE, MENU2, Menu.NONE, _idiomaBase[1]).setIcon(R.drawable.ic_launcher);
          menu.add(Menu.NONE, MENU3, Menu.NONE, _idiomaBase[2]).setIcon(R.drawable.ic_launcher);
          return (super.onCreateOptionsMenu(menu));
     }

     @Override
     public boolean onOptionsItemSelected(MenuItem item)
     {
    	 Log.d(tag, "options items");
    	 switch (item.getItemId())
    	 {
    	 	case MENU1:
    	 		//lenguaje();
    	 		showDialog(0);
    	 		break;

    	 	case MENU2:	
    	 		/*msg = Toast.makeText(Main2.this, "Menu Item 2 Clicked", Toast.LENGTH_LONG);
    	 		msg.show();*/
    	 		Intent credit = new Intent(Main2.this, Creditos.class);
    	 		startActivity(credit);
    	 		break;
    	 	case MENU3:
    	 		finish();                
    	 }
    	 return (super.onOptionsItemSelected(item));
     }

   @Override
    protected  Dialog onCreateDialog(int id)
    {
    	Log.d(tag,"valor id="+id);
    	switch (id)
    	{
    		case 0:
    			return new AlertDialog.Builder(this)
    			.setIcon(R.drawable.ic_launcher)
    			.setTitle(_idiomaBase[0])
    			//.setSingleChoiceItems(items, 0,null)
    			.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						String resultado;
						resultado =items[which].toString();
						//Toast.makeText(getBaseContext(), items[which], Toast.LENGTH_SHORT).show();
						if(resultado.equalsIgnoreCase("Español"))
						{
							_idiomaBase= _idiomaEs;
						}
						if(resultado.equalsIgnoreCase("Ingles"))
						{
							_idiomaBase= _idiomaEn;
						}
					}
				})
    		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Toast.makeText(getBaseContext(), "OK Clicked", Toast.LENGTH_SHORT).show();
						
					}
				}).create();	
    	}
    	return null;
    }

    @Override
	 public void onBackPressed() {
	  // TODO Auto-generated method stub
	  //super.onBackPressed();
	  openQuitDialog();
	 }
	 public void openQuitDialog(){
		  AlertDialog.Builder quitDialog 
		   = new AlertDialog.Builder(this);
		  quitDialog.setTitle("Confirm to Quit?");
		  
		  quitDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
					
				}
			});
		  
		  quitDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
				
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					//Toast.makeText(getBaseContext(), "OK Clicked", Toast.LENGTH_SHORT).show();
					
				}
			}).create().show();
		 }
    
	 
	
}

package alexis.bat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
public class Main1 extends Activity {
	protected boolean _activo=true;
	protected int _time = 3000;
	Button entrar, agregar;
	TextView ruta, descrip, foto, cord;
	BDAdapter db = new BDAdapter(this);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main1);
        
       
        entrar = (Button)findViewById(R.id.button1);
        entrar.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent a = new Intent(Main1.this, Main2.class);
				startActivity(a);
				finish();
			}
		});
        
        agregar = (Button)findViewById(R.id.button2);
        ruta = (TextView)findViewById(R.id.editText1);
        descrip = (TextView)findViewById(R.id.editText2);
        foto = (TextView)findViewById(R.id.editText3);
        cord= (TextView)findViewById(R.id.editText4);
        agregar.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				db.open();
				Long ingresado = db.insertarAll(ruta.getText().toString(), descrip.getText().toString(), foto.getText().toString());
				Toast.makeText(Main1.this, ""+ruta.getText().toString()+ descrip.getText().toString()+ foto.getText().toString(), Toast.LENGTH_LONG).show();
				db.close();
				if(ingresado != -1)
				{
					Toast.makeText(Main1.this, "ingresado"+ingresado.toString(), Toast.LENGTH_LONG).show();
				}
			}
		});
        
        
  
    }


}

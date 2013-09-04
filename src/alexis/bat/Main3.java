package alexis.bat;


import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Main3 extends Activity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main3);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Log.d(tag, "evento creado");
        
        Button mapa = (Button)findViewById(R.id.button1);
       
        final TextView tv = (TextView)findViewById(R.id.textView1);
        Bundle extras = getIntent().getExtras();
		if (extras != null){
			
			tv.setText(extras.getString("ruta"));
			
			//Log.d("Msg", extras.getString("Name"));
		}
		 mapa.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String nombre = tv.getText().toString();
					Intent i = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="+nombre+"+Chiriquí+Panamá&z=14"));
					startActivity(i);
				}
			});
        
    }
	
	
}

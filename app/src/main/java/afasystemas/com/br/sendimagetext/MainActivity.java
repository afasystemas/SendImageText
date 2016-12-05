package afasystemas.com.br.sendimagetext;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //FragmentManager manager = getSupportFragmentManager();
        //FragmentTransaction transaction = manager.beginTransaction();

        //TextView textView = (TextView)findViewById(R.id.texto);

        Intent intent = getIntent();
        String textoRecebido = intent.getStringExtra(Intent.EXTRA_TEXT);
        String imageReceived = intent.getAction();
        String type = intent.getType();

        if (textoRecebido != null) {
            RelativeLayout rl = (RelativeLayout)findViewById(R.id.activity_main);
            TextView tv = new TextView(this);
            tv.setText(textoRecebido);
            RelativeLayout.LayoutParams lp  = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

            tv.setLayoutParams(lp);
            rl.addView(tv);


            //textView.setText(textoRecebido);

        }
        else if(type!=null ){
            ImageView imageView ;
            RelativeLayout rl ;
            Uri selectedImage = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            imageView = new ImageView(this);
            rl = (RelativeLayout)findViewById(R.id.activity_main);
            imageView.setImageURI(selectedImage);
            RelativeLayout.LayoutParams lp  = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);


            imageView.setLayoutParams(lp);
            rl.addView(imageView);

        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        boolean resp = false;
        switch (item.getItemId()){
            case  R.id.home:

                Intent intenthome = new Intent(this.getApplication(),MainActivity.class);
                startActivity(intenthome);
                this.finish();
                resp = true;
                break;

            case  R.id.enviarText:

                Intent intentText = new Intent(this.getApplication(),SendText.class);
                startActivity(intentText);
                this.finish();
                resp = true;
                break;

            case  R.id.enviarImage:

                Intent intentImage = new Intent(this.getApplication(),SendImage.class);
                startActivity(intentImage);
                this.finish();
                resp = true;
                break;


        }


        return resp;
    }
}

package afasystemas.com.br.sendimagetext;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SendImage extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;
    ImageView imageView;
    RelativeLayout rl ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_image);

        Button buscar = (Button)findViewById(R.id.enviarImage);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pick photo in galery
                Intent i = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        final Uri selectedImage = data.getData();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };

        Cursor cursor = getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();

        imageView =new ImageView(this);
        rl = (RelativeLayout)findViewById(R.id.activity_send_image);
        imageView.setImageURI(selectedImage);
        RelativeLayout.LayoutParams lp  = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);


        imageView.setLayoutParams(lp);
        rl.addView(imageView);

        AlertDialog.Builder alerta =  new AlertDialog.Builder(SendImage.this);
        alerta.setTitle("Compartilhar Foto?");
        alerta
                .setMessage("Deseja compartilhar essa Foto?")
                .setCancelable(false)
                .setNegativeButton("Nao", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(SendImage.this, "Compartilhamento Cacelado", Toast.LENGTH_SHORT).show();
                        rl.removeView(imageView);
                    }
                })
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_STREAM, selectedImage);
                        intent.setType("image/*");
                        startActivity(Intent.createChooser(intent,"Escolha onde deseja abrir o seu texto!" ));
                        finish();
                    }
                });
        AlertDialog alertDialog = alerta.create();
        alertDialog.show();



    }
}

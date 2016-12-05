package afasystemas.com.br.sendimagetext;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendText extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_text);

        final EditText valor = (EditText)findViewById(R.id.editTexto);
        Button send = (Button)findViewById(R.id.enviarTexto);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String texto = valor.getText().toString();


                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, texto);
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent,"Escolha onde deseja abrir o seu texto!" ));
                finish();

            }
        });
    }
}

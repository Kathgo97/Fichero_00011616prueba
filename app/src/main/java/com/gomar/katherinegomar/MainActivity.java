package com.gomar.katherinegomar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private TextView editText_git;
    private TextView editText_insta;
    private TextView editText_pint;
    private TextView editText_tel;
    private TextView editText_nombre;
    private TextView editText_carrera;

    private ImageView image_profile_photo;

    private Button button_mostrar_info;
    private Button button_mostrar_imagen;

    private String git_var;
    private String insta_var;
    private String pint_var;
    private String tel_var;
    private String nombre_var;
    private String carrera_var;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getAllViews();


        button_mostrar_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insta_var = editText_insta.getText().toString();
                git_var = editText_git.getText().toString();
                pint_var = editText_pint.getText().toString();
                tel_var = editText_tel.getText().toString();
                nombre_var = editText_nombre.getText().toString();
                carrera_var = editText_carrera.getText().toString();

                sendInfo(contentToString(insta_var,git_var,pint_var,tel_var, nombre_var, carrera_var));

            }
        });

        button_mostrar_imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareDrawable(R.drawable.kathy, "picture_space");
            }
        });
    }

    private void getAllViews(){

        editText_git = findViewById(R.id.git);
        editText_insta = findViewById(R.id.insta);
        editText_pint = findViewById(R.id.pint);
        editText_tel= findViewById(R.id.tel);
        editText_carrera = findViewById(R.id.carrera);
        editText_nombre = findViewById(R.id.nombre);

        image_profile_photo = findViewById(R.id.image);

        button_mostrar_info = findViewById(R.id.btn_info);
        button_mostrar_imagen = findViewById(R.id.btn_imagen);

    }

    private String contentToString(String insta, String git, String pinterest, String telefono, String nombre, String carrera){
        String message = "ABOUT ME\nNombre: "+nombre+"\nCarrera: "+carrera+"\nGithub: "+
                git+"\nPinterest: "+pinterest+"\nInstagram: "+insta+"\nTélefono: "+telefono;

        return message;
    }

    private void sendInfo(String msg){
        Intent sendIntent = new Intent();

        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, msg);
        sendIntent.setType("text/plain");

        startActivity(Intent.createChooser(sendIntent,"About me share..."));
    }

    public void shareDrawable(int resourceId, String fileName) {
        try {
            //Convertir el recurso en bitmap
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);

            //Guardar el bitmap en cache
            File outputFile = new File(getCacheDir(), fileName + ".png");
            FileOutputStream outPutStream = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outPutStream);
            outPutStream.flush();
            outPutStream.close();
            outputFile.setReadable(true, false);

            //share file
            Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(outputFile));
            shareIntent.setType("image/png");
            startActivity(shareIntent);
        }
        catch (Exception e) { Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
        }

    }
}

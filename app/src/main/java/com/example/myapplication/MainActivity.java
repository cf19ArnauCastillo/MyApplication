package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText escribir;
    Button enviar;
    String escribir1;
    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference myRef2;

    ArrayList<mensaje> mensajes;
    ArrayList<usuario> usuarios;

    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        DatabaseReference myRef2 = database.getReference("UsuariosXat");

        escribir=(EditText)findViewById(R.id.escribir);
        enviar=(Button) findViewById(R.id.enviar);
        escribir1="";
        mensajes = new ArrayList<mensaje>();
        usuarios = new ArrayList<usuario>();

        enviar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                mensaje mensaje = new mensaje(escribir.getText().toString());
                mensajes.add(mensaje);

                myRef.setValue(mensajes);

                //usuarios.add(usuario);
                //myRef2.setValue(usuarios);
            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("logTest " ,""+dataSnapshot.getChildrenCount());

                mensajes.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    mensaje mensaje = postSnapshot.getValue(mensaje.class);
                    mensajes.add(mensaje);
                    Log.i("logTest",mensaje.getmensaje());
                }
                RecyclerView recycler= findViewById(R.id.bocadilloschat);
                recycler.setAdapter(new RecyclerBocadillosChat(mensajes));
                recycler.setLayoutManager(new LinearLayoutManager((context)));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.i("logTest", "Failed to read value.", error.toException());
            }
        });/*
        public class recogerUsuario(String usuario) {
            user = usuario;
        }*/
    }
}
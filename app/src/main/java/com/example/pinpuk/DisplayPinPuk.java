package com.example.pinpuk;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigInteger;
import java.sql.Blob;

public class DisplayPinPuk extends AppCompatActivity {
    int from_Where_I_Am_Coming = 0;
    private DBHelper mydb ;
    public static int Edit_Record = 1000016;
    TextView nr_karty ;
    TextView pin;
    TextView puk;
    TextView nr_tel;
    CheckBox mgpo;

    CheckBox konwoj;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_pin_puk);
        nr_karty = (TextView) findViewById(R.id.editNr_Karty);
        pin = (TextView) findViewById(R.id.editPIN);
        puk = (TextView) findViewById(R.id.editPUK);
        nr_tel = (TextView) findViewById(R.id.editNrTel);
        mgpo = (CheckBox) findViewById(R.id.editmgpo);
        konwoj = (CheckBox) findViewById(R.id.editkonwoj);

        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");

            if(Value>0){
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                byte[] Nr_karty = rs.getBlob(rs.getColumnIndexOrThrow(DBHelper.PINPUK_COLUMN_NR_KARTY));
                String Pin = rs.getString(rs.getColumnIndexOrThrow(DBHelper.PINPUK_COLUMN_PIN));
                byte[] Puk = rs.getBlob(rs.getColumnIndexOrThrow(DBHelper.PINPUK_COLUMN_PUK));
                String Nr_tel = rs.getString(rs.getColumnIndexOrThrow(DBHelper.PINPUK_COLUMN_NR_TEL));
                String MGPO = rs.getString(rs.getColumnIndexOrThrow(DBHelper.PINPUK_COLUMN_MGPO));
                String KONWOJ = rs.getString(rs.getColumnIndexOrThrow(DBHelper.PINPUK_COLUMN_KONWOJ));

                if (!rs.isClosed())  {
                    rs.close();
                }
                Button b = (Button)findViewById(R.id.button1);
                b.setVisibility(View.INVISIBLE);

                nr_karty.setText(nr_karty.getText().toString());
                nr_karty.setFocusable(false);
                nr_karty.setClickable(false);

                pin.setText(pin.getText().toString());
                pin.setFocusable(false);
                pin.setClickable(false);

                puk.setText(puk.getText().toString());
                puk.setFocusable(false);
                puk.setClickable(false);

                nr_tel.setText(nr_tel.getText().toString());
                nr_tel.setFocusable(false);
                nr_tel.setClickable(false);

                mgpo.setChecked(false);
                mgpo.setFocusable(false);
                mgpo.setClickable(false);

                konwoj.setChecked(false);
                konwoj.setFocusable(false);
                konwoj.setClickable(false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Bundle extras = getIntent().getExtras();

        if(extras !=null) {
            int Value = extras.getInt("id");
            if(Value>0){
                getMenuInflater().inflate(R.menu.display_pinpuk, menu);
            } else{
                getMenuInflater().inflate(R.menu.main_menu, menu);
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int itemId = item.getItemId();
        if (itemId == R.id.Edit_Record) {
            //ImageView img = (ImageView) findViewById(R.id.imageView);
            //img.setImageResource(R.drawable.okrasa);
            Button b = (Button) findViewById(R.id.button1);
            b.setVisibility(View.VISIBLE);

            nr_karty.setText(nr_tel.getText().toString());
            nr_karty.setEnabled(true);
            nr_karty.setFocusableInTouchMode(true);
            nr_karty.setClickable(true);

            pin.setText(pin.getText().toString());
            pin.setEnabled(true);
            pin.setFocusableInTouchMode(true);
            pin.setClickable(true);

            puk.setText(puk.getText().toString());
            puk.setEnabled(true);
            puk.setFocusableInTouchMode(true);
            puk.setClickable(true);

            nr_tel.setText(nr_tel.getText().toString());
            nr_tel.setEnabled(true);
            nr_tel.setFocusableInTouchMode(true);
            nr_tel.setClickable(true);

            mgpo.setChecked(false);
            mgpo.setEnabled(true);
            mgpo.setFocusableInTouchMode(true);
            mgpo.setClickable(true);

            konwoj.setChecked(false);
            konwoj.setEnabled(true);
            konwoj.setFocusableInTouchMode(true);
            konwoj.setClickable(true);

            return true;
        } else if (itemId == R.id.Delete_Record) {
            ImageView img = (ImageView) findViewById(R.id.imageView);
            img.setImageResource(R.drawable.misiek);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.delete)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mydb.deleteContact(id_To_Update);
                            Toast.makeText(getApplicationContext(), "Deleted Successfully",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });

            AlertDialog d = builder.create();
            d.setTitle("Are you sure");
            d.show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void run(View view) {
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");
            if(Value>0){
                if(mydb.updateContact(id_To_Update,new BigInteger(nr_karty.getText().toString()), Integer.parseInt(pin.getText().toString()),
                        new BigInteger(puk.getText().toString()), nr_tel.getText().toString(),
                        mgpo.isChecked(), konwoj.isChecked())){
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                }
            } else{
                if(mydb.insertCard(new BigInteger(nr_karty.getText().toString()), Integer.parseInt(pin.getText().toString()),
                        new BigInteger(puk.getText().toString()), nr_tel.getText().toString(),
                        mgpo.isChecked(), konwoj.isChecked())){
                    Toast.makeText(getApplicationContext(), "done",
                            Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getApplicationContext(), "not done",
                            Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        }
    }
}

package com.app.tradebookie;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Addrecords extends AppCompatActivity {
    EditText symbol,Date,quntity,Price;
    Button Addit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrecords);

        symbol=findViewById(R.id.symbolname);
        Date=findViewById(R.id.date);
        quntity=findViewById(R.id.qunt);
        Price=findViewById(R.id.price);

        Addit=findViewById(R.id.addbutton);

        Addit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=symbol.getText().toString();
                String date=Date.getText().toString();
                String qut=quntity.getText().toString();
                String pri=Price.getText().toString();




                try { adddata(name,date,qut,pri); }
                catch (IOException e) {e.printStackTrace();}

            }
        });
    }




    public ArrayList getdata() throws IOException {
        ArrayList<String> arr = new ArrayList();

        FileInputStream f1 = openFileInput("ticker.txt");
        InputStreamReader i1 = new InputStreamReader(f1);
        BufferedReader b1 = new BufferedReader(i1);
        String ticker;
        while ((ticker = b1.readLine()) != null) {
            arr.add(ticker);
        }
        System.out.println(arr);
        f1.close();
        i1.close();

        return arr;
    }






    public ArrayList<String> allStockName() throws IOException {
        ArrayList<String> stocks=new ArrayList<>();

        FileInputStream f1 = null;
        f1 = openFileInput("ticker.txt");
        InputStreamReader i1 = new InputStreamReader(f1);
        BufferedReader b1 = new BufferedReader(i1);
        String ticker;

        int index=0;
        while ((ticker = b1.readLine()) != null) {
            if (index%4==0){stocks.add(ticker);}
            index++;
        }

        return stocks;
    }





    public boolean isexist(ArrayList arr,String item){
        if (arr.contains(item)){return true;}
        else{return false;}
    }
    public void adddata(  String symbol, String date , String qut,String price) throws IOException {


        boolean errorflag=false;

        if (symbol.equals("")){errorflag=false;Toast.makeText(Addrecords.this, "Empty Record not accepted", Toast.LENGTH_SHORT).show(); }
        else if (price.equals("")){price="0";errorflag=true;Toast.makeText(Addrecords.this, "Blank value set to 0", Toast.LENGTH_SHORT).show();}
        else if (qut.equals("")){qut="0";errorflag=true;Toast.makeText(Addrecords.this, "Blank value set to 0", Toast.LENGTH_SHORT).show();}
        else{errorflag=true;}


        ArrayList stockPresent= allStockName();

        if (errorflag) {
            if (stockPresent.contains(symbol)) {
                String symbolname = symbol;
                boolean flag = true;
                int times = 1;
                while (flag) {
                    symbolname = symbol + "(" + Integer.toString(times) + ")";
                    if (isexist(stockPresent, symbolname)) {
                        times++;
                    } else {
                        flag = false;

                        FileOutputStream fos = openFileOutput("ticker.txt", MODE_APPEND);
                        OutputStreamWriter osw = new OutputStreamWriter(fos);
                        osw.write(symbolname + "\n");
                        osw.write(date + "\n");
                        osw.write(qut + "\n");
                        osw.write(price + "\n");
                        Toast.makeText(Addrecords.this, "Data Added", Toast.LENGTH_SHORT).show();


                        osw.close();
                        fos.close();

                    }

                }

                Toast.makeText(Addrecords.this, "Data Alredy Exists", Toast.LENGTH_SHORT).show();
            } else {


                FileOutputStream fos = openFileOutput("ticker.txt", MODE_APPEND);
                OutputStreamWriter osw = new OutputStreamWriter(fos);
                osw.write(symbol + "\n");
                osw.write(date + "\n");
                osw.write(qut + "\n");
                osw.write(price + "\n");
                Toast.makeText(Addrecords.this, "Data Added", Toast.LENGTH_SHORT).show();


                osw.close();
                fos.close();
            }
            System.out.println(getdata());
        }
    }

}
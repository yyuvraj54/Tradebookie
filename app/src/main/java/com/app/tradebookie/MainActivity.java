package com.app.tradebookie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lst;
    Button buton;
    Button removebuton;
    TextView amountlab;
    ImageButton aboutpage;


    @Override
    protected void onResume() {

        super.onResume();
        lst=findViewById(R.id.list);

        try {ArrayList<ArrayList> alldata=getdataforadapter();
            ArrayList<String> sname =alldata.get(0);
            ArrayList<String> sdate=alldata.get(1);
            ArrayList<String> sprice=alldata.get(3);
            ArrayList<String> squt=alldata.get(2);
            myadapter adapter=new myadapter(this,sname,sdate,sprice,squt);
            lst.setAdapter(adapter);
        }
        catch (IOException e) {e.printStackTrace(); }


        try {amountlab.setText( "₹"+Float.toString(getAmountTotal()));}
        catch (IOException e) {e.printStackTrace();}}

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        aboutpage=findViewById(R.id.aboutPageBut);
        buton=findViewById(R.id.add);
        amountlab=findViewById(R.id.Amount);
        removebuton=findViewById(R.id.remove);







        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Addrecords.class);
                startActivity(intent);
            }
        });

        removebuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,deleteitems.class);
                startActivity(intent);
            }
        });


        aboutpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AboutPage.class);
                startActivity(intent);
            }
        });





    }


    public ArrayList getdataforadapter() throws IOException {
        ArrayList<ArrayList> arr=new ArrayList();

        ArrayList<String> na=new ArrayList();
        ArrayList<String> da=new ArrayList();
        ArrayList<String> pr=new ArrayList();
        ArrayList<String> qu=new ArrayList();


        FileInputStream f1 = openFileInput("ticker.txt");
        InputStreamReader i1 = new InputStreamReader(f1);
        BufferedReader b1 = new BufferedReader(i1);
        String ticker;

        int index=0;
        while ((ticker = b1.readLine()) != null) {
            if (index==0){na.add(ticker);index++;}
            else if (index==1){da.add(ticker);index++;}
            else if (index==2){pr.add("qty:"+ticker);index++;}
            else if (index==3){qu.add("₹"+ticker);index++;}

            if(index>3){index=0;}
        }

        arr.add(na);
        arr.add(da);
        arr.add(pr);
        arr.add(qu);


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
    public float getAmountTotal() throws IOException {

        float Amount= 0.0F;

        FileInputStream f1 = null;
        f1 = openFileInput("ticker.txt");
        InputStreamReader i1 = new InputStreamReader(f1);
        BufferedReader b1 = new BufferedReader(i1);
        String ticker;


        int index=1;
        int qunt_index=2;
        int Qunt=0;
        while ((ticker = b1.readLine()) != null) {
            if (qunt_index%4==0){

                try {Qunt=Integer.parseInt(ticker);}
                catch (NumberFormatException err){Qunt=0;}

            }
            if (index%4==0){

                try{
                    float f=Float.parseFloat(ticker);
                    f=f*Qunt;
                    Amount=Amount+f;
                    System.out.println(Amount+"AMount");}
                catch (NumberFormatException errr){ticker="0";float f=Float.parseFloat(ticker);}



            }

            index++;
            qunt_index++;
        }

        return Amount;
    }

    class myadapter extends ArrayAdapter<String> {
        Context context;
        ArrayList<String> stockname;
        ArrayList<String> Date;
        ArrayList<String> price;
        ArrayList<String> quantity;

        myadapter (Context c, ArrayList<String> stockname,ArrayList<String> Date ,ArrayList<String> price, ArrayList<String> quantity){
            super(c,R.layout.single_row,R.id.price,price);
            context=c;
            this.stockname=stockname;
            this.Date=Date;
            this.price=price;
            this.quantity=quantity;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater inflater=(LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=inflater.inflate(R.layout.single_row,parent,false);

            TextView stkname=row.findViewById(R.id.stockname);
            TextView stdate=row.findViewById(R.id.stockdate);
            TextView stqut=row.findViewById(R.id.stockqunt);
            TextView stprice=row.findViewById(R.id.stockprice);

            stkname.setText(stockname.get(position));
            stdate.setText(Date.get(position));
            stqut.setText(quantity.get(position));
            stprice.setText(price.get(position));

            return row;
        }
    }




}


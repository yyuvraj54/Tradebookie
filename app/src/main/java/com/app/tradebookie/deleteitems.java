package com.app.tradebookie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class deleteitems extends AppCompatActivity {
    Button clearbut;
    ListView listitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleteitems);

        clearbut=findViewById(R.id.clear);
        listitem=findViewById(R.id.item);


        ArrayList<String> arr=new ArrayList();
        try { arr=allStockName();}

        catch (FileNotFoundException ex){ex.printStackTrace();Toast.makeText(deleteitems.this, "Loading Data Failed ", Toast.LENGTH_LONG).show(); }
        catch (IOException ioException) { ioException.printStackTrace();}


        ArrayAdapter adapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,arr);
        listitem.setAdapter(adapter);




        clearbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try { clearAllData();}
                catch (IOException e) {e.printStackTrace();}

                adapter.clear();
                adapter.notifyDataSetChanged();


            }
        });

        listitem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemValue=(String) listitem.getItemAtPosition(position);

                try { removeItem(itemValue); }
                catch (IOException e) {e.printStackTrace();}

                adapter.remove(itemValue);
                adapter.notifyDataSetChanged();


                Toast.makeText(deleteitems.this, itemValue+" Removed", Toast.LENGTH_SHORT).show();

            }
        });



    }



    public void clearAllData() throws IOException {

        FileOutputStream fos= openFileOutput( "ticker.txt", MODE_PRIVATE);
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        osw.write("");
        fos.close();
        osw.close();


        Toast.makeText(deleteitems.this, "All Data Cleared", Toast.LENGTH_SHORT).show();

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

    public ArrayList getdata() throws IOException {
        ArrayList<String> arr = new ArrayList();


        FileInputStream f1 = openFileInput("ticker.txt");
        InputStreamReader i1 = new InputStreamReader(f1);
        BufferedReader b1 = new BufferedReader(i1);
        String ticker;
        while ((ticker = b1.readLine()) != null) {
            arr.add(ticker);
        }
        f1.close();
        i1.close();
        return arr;
    }

        public void removeItem(String Item) throws IOException {


        ArrayList<String> data=getdata();

        int index=data.indexOf(Item);
        data.remove(index);
        data.remove(index);
        data.remove(index);
        data.remove(index);







        FileOutputStream fos= openFileOutput( "ticker.txt", MODE_PRIVATE);
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        osw.write("");

        osw.close();
        fos.close();



        FileOutputStream fos1= openFileOutput( "ticker.txt", MODE_APPEND);
        OutputStreamWriter osw1 = new OutputStreamWriter(fos1);

        for(String eachitem:data){
            osw1.write(eachitem+"\n");
        }

        osw1.close();
        fos1.close();

        }





}
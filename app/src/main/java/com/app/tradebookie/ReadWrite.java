package com.app.tradebookie;

import static android.os.ParcelFileDescriptor.MODE_APPEND;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class ReadWrite extends AppCompatActivity{
    public void adddata(  String symbol, String date , String qut,String price) throws IOException {
        FileOutputStream fos = null;
        fos= openFileOutput( "ticker.txt", MODE_APPEND);
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        osw.append(symbol);
        osw.append(date);
        osw.append(qut);
        osw.append(price);

        fos.close();

    }

    public ArrayList getdata() throws IOException {
        ArrayList<String> arr=new ArrayList();

        FileInputStream f1 = null;
        f1 = openFileInput("ticker.txt");
        InputStreamReader i1 = new InputStreamReader(f1);
        BufferedReader b1 = new BufferedReader(i1);
        String ticker;
        while ((ticker = b1.readLine()) != null) {
            arr.add(ticker);
        }
        System.out.println(arr);

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


}


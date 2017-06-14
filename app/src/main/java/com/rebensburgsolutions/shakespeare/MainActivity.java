package com.rebensburgsolutions.shakespeare;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etWord;
    TextView tvQuote;
    String dataString="";
    ArrayList<String[]> dataList;
    String[] cacheArray;
    String[] dataArray;


    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public static String getStringFromFile (String filePath) throws Exception {
        File fl = new File(filePath);
        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWord = (EditText) findViewById(R.id.et_word);
        tvQuote= (TextView) findViewById(R.id.tv_quote);
        try {
            dataString=getStringFromFile("");
        } catch (Exception e) {
            e.printStackTrace();
        }

        dataList = new ArrayList<>();

        cacheArray = dataString.split("#");

        for(int i = 0; i < cacheArray.length; i++){

            dataArray = new String[6];
            for(int j=0; j<=5; j++) {
                dataArray[j]=cacheArray[i];
            }

            dataList.add(dataArray);
        }

    }

    public void btnQuoteClicked(View view){
        String requestedWord = "";
        requestedWord = etWord.getText().toString();

        for(String[] item: dataList){
            if(item[5].contains(requestedWord)){
                Toast.makeText(getBaseContext(),"return:"+item[5], Toast.LENGTH_SHORT).show();
            }
        }
        tvQuote.setText(etWord.getText());
    }
}

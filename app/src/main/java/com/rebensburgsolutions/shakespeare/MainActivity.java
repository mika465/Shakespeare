package com.rebensburgsolutions.shakespeare;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etWord;
    TextView tvQuote;
    LinearLayout contInfo;
    TextView tvTitle;
    TextView tvYear;
    TextView tvCharacter;

    String dataString="";
    ArrayList<String[]> dataList;
    String[] cacheArray;
    String[] dataArray;
    String[] foundArray;
    String requestedWord="";

    public String readTextFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWord = (EditText) findViewById(R.id.et_word);
        tvQuote= (TextView) findViewById(R.id.tv_quote);

        InputStream XmlFileInputStream = getResources().openRawResource(R.raw.dat_a);



        dataString= readTextFile(XmlFileInputStream);



        dataList = new ArrayList<>();

        cacheArray = dataString.split("#");

        for(int i = 0; i < cacheArray.length-1; i*=1){

            dataArray = new String[6];
            for(int j=0; j<=5; j++) {
                dataArray[j]=cacheArray[i];
                i++;

            }

            dataList.add(dataArray);
        }



    }

    public void btnQuoteClicked(View view){
        requestedWord = etWord.getText().toString();

        new SearchTask().execute();

//        for(String[] item: dataList){
//
//            Log.d("List:", ""+item[0]+ item[1]+ item[2]+ item[3]+ item[4]+ item[5]);
//            if(item[5].contains(requestedWord)){
//                Toast.makeText(getBaseContext(),"return:"+item[5], Toast.LENGTH_SHORT).show();
//
//                Log.d("Returned: ",""+item[5]);
//                break;
//            }
//        }
        tvQuote.setText(etWord.getText());
    }

    public void btnInfoClicked(View view){
        contInfo = (LinearLayout) findViewById(R.id.cont_info);

        Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_down);
        Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_up);

        if(contInfo.getVisibility()==View.INVISIBLE){
            contInfo.setVisibility(View.VISIBLE);
            contInfo.startAnimation(slide_down);

        }
        else{
            contInfo.startAnimation(slide_up);
            contInfo.setVisibility(View.INVISIBLE);
        }

    }

    class SearchTask extends AsyncTask<URL, String[], String[]> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String[] doInBackground(URL... params) {
            for(String[] item: dataList){

                Log.d("List:", ""+item[0]+ item[1]+ item[2]+ item[3]+ item[4]+ item[5]);
                if(item[5].contains(requestedWord)){

                    Log.d("Returned: ",""+item[5]);
                    return item;
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String[]... values) {
        }

        @Override
        protected void onPostExecute(String[] quoteArray) {
            tvTitle = (TextView) findViewById(R.id.tv_title);
            tvYear = (TextView) findViewById(R.id.tv_year);
            tvCharacter = (TextView) findViewById(R.id.tv_character);

            if(quoteArray!=null) {
                String formatedQuote = quoteArray[5];
                formatedQuote=formatedQuote.replaceAll("\\[p\\]","\n");

                tvTitle.setText("Play: "+quoteArray[0]);
                tvYear.setText("Year: "+quoteArray[2]);
                tvCharacter.setText("Character speaking: "+quoteArray[3]);
                tvQuote.setText(formatedQuote);
            }
            else{
                tvTitle.setText("Nothing found :/");
                tvYear.setText("");
                tvCharacter.setText("");
                tvQuote.setText("");
            }
        }
    }
}



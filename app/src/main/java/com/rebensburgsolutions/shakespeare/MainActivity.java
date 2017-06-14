package com.rebensburgsolutions.shakespeare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etWord;
    TextView tvQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWord = (EditText) findViewById(R.id.et_word);
        tvQuote= (TextView) findViewById(R.id.tv_quote);
    }

    public void btnQuoteClicked(View view){
        tvQuote.setText(etWord.getText());
    }
}

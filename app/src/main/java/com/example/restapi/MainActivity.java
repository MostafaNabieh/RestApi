package com.example.restapi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity   {
    private EditText mBookInput;
    private TextView mTitleText;
    private TextView mAuthorText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBookInput = (EditText)findViewById(R.id.bookInput);
        mTitleText = (TextView)findViewById(R.id.titleText);
        mAuthorText = (TextView)findViewById(R.id.authorText);
    }

    public void searchBooks(View view) {
        String query=mBookInput.getText().toString();
        InputMethodManager inputMethodManager=(InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager != null )
        {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

        }
        ConnectivityManager connectivityManager =(ConnectivityManager)
        getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=null;
        if(connectivityManager != null)
        {
            networkInfo=connectivityManager.getActiveNetworkInfo();

        }
        if(networkInfo != null && networkInfo.isConnected() && query.length() != 0)
        {

            new FetchBook(mTitleText,mAuthorText).execute(query);
            mTitleText.setText("Loading");
            mAuthorText.setText("");

        }else {
            if(query.length() == 0)
            {
                mAuthorText.setText("");
                mTitleText.setText("no search term");
            }
            else
            {
                mAuthorText.setText("");
                mTitleText.setText("NO Network");
            }
        }

    }


}

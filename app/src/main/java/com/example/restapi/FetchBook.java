package com.example.restapi;

import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class FetchBook extends AsyncTask<String, Void, String> {

    private WeakReference<TextView>texttitle,textauthor;

    public FetchBook(TextView tvtitle,TextView tvauthor) {
        texttitle=new WeakReference<>(tvtitle);
        textauthor=new WeakReference<>(tvauthor);

    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getBookInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String aVoid) {
        super.onPostExecute(aVoid);
        try {
            JSONObject jsonObject=new JSONObject(aVoid);
            JSONArray itemsArray=jsonObject.getJSONArray("items");
            int i=0;
            String title=null;
            String authors=null;
            while (i<itemsArray.length()&&(title==null&&authors==null))
            {
                    JSONObject book=itemsArray.getJSONObject(i);
                    JSONObject volumeInfo=book.getJSONObject("volumeInfo");
                    try {
                        title=volumeInfo.getString("title");
                        authors=volumeInfo.getString("authors");
                    }catch (Exception e)
                    {

                    }
                    i++;
            }
            if(title != null && authors != null)
            {
                texttitle.get().setText(title);
                textauthor.get().setText(authors);
            }
            else
            {
                texttitle.get().setText("No Results Found");
                textauthor.get().setText("No Results Found");
            }
        } catch (JSONException e) {
            texttitle.get().setText("No Results Found");
            textauthor.get().setText("No Results Found");
            e.printStackTrace();
        }
    }
}

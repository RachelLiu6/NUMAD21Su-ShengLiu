package edu.neu.cs5520.numad21su_shengliu;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;





public class WebServiceWeek7 extends AppCompatActivity {
    private static final String TAG ="WebServiceActivity";

    private ListView listView;
    private EditText mWebDestEditText;
    private Button enter;
    private TextView progressText;
    private ProgressBar progressBar;
    private ArrayList<String> arrayList;

    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";
    private static final String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service_week7);


        mWebDestEditText = (EditText) findViewById(R.id.editText);
        enter = (Button) findViewById(R.id.loadButton);
        listView = (ListView) findViewById(R.id.listView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        progressText = (TextView) findViewById(R.id.textView2);

        arrayList = new ArrayList<String>();
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList));
        initializeData(savedInstanceState);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callWebserviceButtonHandler(view);
            }
        });
    }

    public void callWebserviceButtonHandler(View view) {
        PingWebServiceTask task = new PingWebServiceTask();
        task.execute(mWebDestEditText.getText().toString());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        int size = arrayList == null ? 0 : arrayList.size();
        outState.putInt(NUMBER_OF_ITEMS, size);

        for (int i = 0; i < size; i++) {
            // put domains into instance
            outState.putString(KEY_OF_INSTANCE + i + "0", arrayList.get(i));
        }
        super.onSaveInstanceState(outState);

    }

    private void initializeData(Bundle savedInstanceState) {
        // Not the first time to open this Activity
        if (savedInstanceState != null && savedInstanceState.containsKey(NUMBER_OF_ITEMS)) {
            if(arrayList == null || arrayList.size() == 0) {
                int size = savedInstanceState.getInt(NUMBER_OF_ITEMS);

                // Retrieve keys we stored in the instance
                for (int i = 0; i < size; i++) {
                    String domain = savedInstanceState.getString(KEY_OF_INSTANCE + i + "0");
                    arrayList.add(domain);
                }
            }
        }
    }



    private class PingWebServiceTask extends AsyncTask<String, String, String> {
        private ArrayAdapter<String> adapter;


        @Override
        protected void onPreExecute() {
            progressText.setText("Loading...");
            progressBar.setVisibility(View.VISIBLE);
            adapter = (ArrayAdapter<String>) listView.getAdapter();
            adapter.clear();
        }

        @Override
        protected void onPostExecute(String str) {
            // pass the string from doInBackground()
            progressText.setText("Searching Done");
            progressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onProgressUpdate(String... strings) {
            adapter.add(strings[0]);

        }

        @Override
        protected String doInBackground(String... strings) {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                URL url = new URL("https://api.domainsdb.info/v1/domains/search?domain=" + strings[0]);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);

                connection.connect();

                // Get response
                InputStream inputStream = connection.getInputStream();
                final String resp = convertStreamToString(inputStream);
                JSONObject jsonObject = new JSONObject(resp);
                JSONArray jsonArray = (JSONArray) jsonObject.get("domains");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject domain = jsonArray.getJSONObject(i);
                    String res = domain.getString("domain");
                    publishProgress(res);
                }

            } catch (MalformedURLException e) {
                Log.e(TAG, "MalformedURLException");
                e.printStackTrace();;
            } catch (ProtocolException e) {
                Log.e(TAG, "ProtocolException");
            } catch (IOException e) {
                Log.e(TAG, "IOException");
                e.printStackTrace();
            } catch (JSONException e) {
                Log.e(TAG, "JSONException");
                e.printStackTrace();
            }
            return null;
        }

        // Helper function
        private String convertStreamToString(InputStream is) {
            Scanner s = new Scanner(is).useDelimiter("\\A");
            return s.hasNext() ? s.next().replace(",", ",\n") : "";
        }
    }
}

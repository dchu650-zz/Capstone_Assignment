package com.example.darren&aidan.contactmanager;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Darren & Aidan on 4/7/2015.
 */
public class Interests extends ActionBarActivity {
    private EditText interest;
    private Button addInterest;
    private String keyword;
    private String mPhoneNumber;
    private String text = "";
    private JSONArray jsonArray;
    private ListView keywordListView;
    private ListView myKeywordsListView;
    private TextView interestsText;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interests);




        interestsText = (TextView) findViewById(R.id.textView);

        TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mPhoneNumber = tMgr.getLine1Number();

        interest = (EditText) findViewById(R.id.myInterest);

        keywordListView = (ListView) findViewById(R.id.keywordView);
        keywordListView.setVisibility(View.GONE);

        myKeywordsListView = (ListView) findViewById(R.id.myKeywordsView);
        new GetMyKeywordsTask().execute(new ApiConnector());
        //registerForContextMenu(myKeywordsListView);
        myKeywordsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long arg3) {


                try {
                    System.out.println("DELETE: " + jsonArray.getJSONObject(position).getString("keyword") + "################################");
                    keyword = jsonArray.getJSONObject(position).getString("keyword");
                } catch(JSONException e){
                    e.printStackTrace();
                }
                new DeleteMyKeywordTask().execute(new ApiConnector());

                return false;
            }

        });



        addInterest = (Button) findViewById(R.id.addInterest);
        addInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword = interest.getText().toString();
                new InsertKeywordTask().execute(new ApiConnector());
                startActivity(new Intent(getApplicationContext(), MyProfile.class));
            }
        });






        //pop up keywords already in db
        interest.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable mEdit) {
                keywordListView.setVisibility(View.VISIBLE);
                myKeywordsListView.setVisibility(View.GONE);
                text = mEdit.toString();
                new findKeywordTask().execute(new ApiConnector());


            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        //autofill the keyword on click
        this.keywordListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    JSONObject itemClicked = jsonArray.getJSONObject(position);
                    interest.setText(itemClicked.getString("keyword"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });










    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v,
//                                    ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_main, menu);
//    }
//
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        switch (item.getItemId()) {
//            case R.id.delete:
//                System.out.println("DELETE: " + item.toString());
//                return true;
//            default:
//                return super.onContextItemSelected(item);
//        }
//    }


    private class DeleteMyKeywordTask extends AsyncTask<ApiConnector, Long, JSONArray>{
        @Override
        protected JSONArray doInBackground(ApiConnector... params) {
            params[0].DeleteKeyword(mPhoneNumber, keyword);

            return null;
        }
    }

    private class GetMyKeywordsTask extends AsyncTask<ApiConnector, Long, JSONArray>{
        @Override
        protected JSONArray doInBackground(ApiConnector... params) {
            return params[0].FindMyKeywords(mPhoneNumber);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {

            setListAdapter(jsonArray);

        }
    }

    private class InsertKeywordTask extends AsyncTask<ApiConnector, Long, JSONArray>
    {
        @Override
        protected JSONArray doInBackground(ApiConnector... params) {
            params[0].InsertKeyword(mPhoneNumber, keyword);

            return null;
        }
    }

    private class findKeywordTask extends AsyncTask<ApiConnector, Long, JSONArray>
    {
        @Override
        protected JSONArray doInBackground(ApiConnector... params) {
            return params[0].FindKeyword(text);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {

            setListAdapter(jsonArray);

        }
    }



    public void setListAdapter(JSONArray jsonArray){
        this.jsonArray = jsonArray;

        GetKeywordsListViewAdapter temp = new GetKeywordsListViewAdapter(this.jsonArray, this);

        if (text.length() > 0) {//display keywordsListView
            myKeywordsListView.setVisibility(View.GONE);
            interestsText.setVisibility(View.GONE);
            keywordListView.setVisibility(View.VISIBLE);
            this.keywordListView.setAdapter(temp);
        }
        else{//display my keywords
            myKeywordsListView.setVisibility(View.VISIBLE);
            interestsText.setVisibility(View.VISIBLE);
            keywordListView.setVisibility(View.GONE);
            this.myKeywordsListView.setAdapter(temp);
        }

    }

}

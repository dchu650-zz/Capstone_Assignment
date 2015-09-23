package com.example.darren&aidan.contactmanager;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;

/**
 * Created by Darren & Aidan on 4/3/2015.
 */
public class MyProfile extends ActionBarActivity {

    private String mPhoneNumber;
    private Button myInterestsButton;
    private Button myItemsButton;
    private JSONArray jsonArray;

    ListView myItemListView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);

        TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mPhoneNumber = tMgr.getLine1Number();

        myItemListView = (ListView) findViewById(R.id.itemView);



        //get to the customers interests (keywords)
        this.myInterestsButton = (Button) findViewById(R.id.myInterests);
        this.myInterestsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Interests.class);
                startActivity(intent);
            }

        });

        //get to the customers items (edit/delete)
        this.myItemsButton = (Button) findViewById(R.id.myItems);
        this.myItemsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyItems.class);
                startActivity(intent);
            }

        });

        //display items with matching keywords
        new GetLikedItems().execute(new ApiConnector());


    }

    private class GetLikedItems extends AsyncTask<ApiConnector, Long, JSONArray>
    {
        @Override
        protected JSONArray doInBackground(ApiConnector... params) {
            //it is executed on Background thread

            return params[0].GetLikedItems(mPhoneNumber);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {

            setListAdapter(jsonArray);



        }
    }

    public void setListAdapter(JSONArray jsonArray){
        this.jsonArray = jsonArray;

        GetAllItemsListViewAdapter temp = new GetAllItemsListViewAdapter(this.jsonArray, this, "0.0", "0.0");

        this.myItemListView.setAdapter(temp);

    }
}

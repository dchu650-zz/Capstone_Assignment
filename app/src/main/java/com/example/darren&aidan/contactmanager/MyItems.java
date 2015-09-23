package com.example.darren&aidan.contactmanager;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Darren & Aidan on 4/7/2015.
 */
public class MyItems extends ActionBarActivity {
    private ListView myItemsListView;
    private JSONArray jsonArray;
    private String mPhoneNumber;
    private String latitude;
    private String longitude;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_items);

        TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mPhoneNumber = tMgr.getLine1Number();

        //get MyItems
        myItemsListView = (ListView) findViewById(R.id.itemView);
        new GetAllItemsTask().execute(new ApiConnector());


        this.myItemsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    //get clicked item
                    JSONObject itemClicked = jsonArray.getJSONObject(position);


                    Intent showDetails = new Intent(getApplicationContext(), EditItem.class);
//                    showDetails.putExtra("Title", itemClicked.getString("title"));
//                    showDetails.putExtra("Price", itemClicked.getString("price"));
//                    //showDetails.putExtra("Keywords", itemClicked.getString("keywords"));
                    showDetails.putExtra("Phone", itemClicked.getString("phoneNo"));
//                    showDetails.putExtra("Description", itemClicked.getString("description"));
                    showDetails.putExtra("Time", itemClicked.getString("time"));
                    latitude = itemClicked.getString("latitude");
                    longitude = itemClicked.getString("longitude");
                    showDetails.putExtra("Latitude", itemClicked.getString("latitude"));
                    showDetails.putExtra("Longitude", itemClicked.getString("longitude"));
//                    Item item = new Item();
//                    item.setPhoneNumber(itemClicked.getString("phoneNo"));
//                    item.setTime((Timestamp)itemClicked.get("time"));
                    //showDetails.putExtra("Item", item);


                    startActivity(showDetails);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private class GetAllItemsTask extends AsyncTask<ApiConnector, Long, JSONArray>
    {
        @Override
        protected JSONArray doInBackground(ApiConnector... params) {
            //it is executed on Background thread

            return params[0].GetMyItems(mPhoneNumber);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {

            setListAdapter(jsonArray);

        }
    }

    public void setListAdapter(JSONArray jsonArray){
        this.jsonArray = jsonArray;
        System.out.println(jsonArray);

        GetAllItemsListViewAdapter temp = new GetAllItemsListViewAdapter(this.jsonArray, this, latitude, longitude);

        this.myItemsListView.setAdapter(temp);

    }
}



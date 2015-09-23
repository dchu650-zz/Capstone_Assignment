package com.example.darren&aidan.contactmanager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Darren & Aidan on 3/31/2015.
 */
public class GetAllItemsListViewAdapter extends BaseAdapter {

    private JSONArray dataArray;
    private Activity activity;

    private static LayoutInflater layoutInflater = null;
    private String myLatitude;
    private String myLongitude;
    private float[] distance;
    private JSONObject jsonObject;
    private String myUrl = "http://ec2-52-5-244-215.compute-1.amazonaws.com/uploads/";
    private String imageUrl = "";
    private URL url;






    public GetAllItemsListViewAdapter(JSONArray jsonArray, Activity a, String latitude, String longitude){

        this.dataArray = jsonArray;
        this.activity = a;
        myLatitude = latitude;
        myLongitude = longitude;


        layoutInflater = (LayoutInflater) this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public int getCount() {
        return this.dataArray.length();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ListCell cell;
        //set up convert view if it is null
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.get_all_planes_cell, null);
            cell = new ListCell();
            cell.Title = (TextView) convertView.findViewById(R.id.title);
            cell.Price = (TextView) convertView.findViewById(R.id.price);
            cell.Distance = (TextView) convertView.findViewById(R.id.distance);
            cell.picture = (ImageView) convertView.findViewById(R.id.plane_pic);


            convertView.setTag(cell);
        }
        else{
            cell = (ListCell) convertView.getTag();
        }
        //change cell data

        try {
            jsonObject = this.dataArray.getJSONObject(position);
            imageUrl = myUrl + jsonObject.getString("encodedImage");

            //new GetImageTask().execute();


            cell.setUrl(imageUrl);
            cell.Title.setText(jsonObject.getString("title"));
            cell.Price.setText(jsonObject.getString("price"));
            distance = new float[3];
            System.out.println("Array: " +distance.toString());
            System.out.println("Lat: " + myLatitude);
            System.out.println("Long: " + myLongitude);
            System.out.println("jsonLat: " + jsonObject.getString("latitude"));
            System.out.println("jsonLong: "+ jsonObject.getString("longitude"));
            System.out.println("Image: " + jsonObject.getString("encodedImage"));


            Location.distanceBetween(Double.parseDouble(myLatitude), Double.parseDouble(myLongitude), Double.parseDouble(jsonObject.getString("latitude")), Double.parseDouble(jsonObject.getString("longitude")), distance);
            double dismiles = (double) Math.round((distance[0]*0.000621371)*10) / 10;
            String miles = Double.toString(dismiles);
            cell.Distance.setText(miles + " Miles Away");
            //cell.picture.
            //photo here
            //decode code
//
//            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
//            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//            testByte.setImageBitmap(decodedByte);
            //cell.picture.setImageResource(R.drawable.ic_launcher);
        } catch (JSONException e){
            e.printStackTrace();
        }

        return convertView;
    }

    private class ListCell{
        private TextView Title;
        private TextView Price;
        private TextView Distance;
        private ImageView picture;

        private String imageUrl;


        public void setUrl(String s){
            imageUrl = s;
            new GetImageTask().execute();
        }

        private class GetImageTask extends AsyncTask<String, String, String>
        {
            Bitmap bitmap;
            @Override
            protected String doInBackground(String... args) {
                // updating UI from Background Thread

                try {

                    url = new URL(imageUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                return null;
            }
            @Override
            protected void onPostExecute(String args) {

                picture.setImageBitmap(bitmap);


            }


        }
    }



}

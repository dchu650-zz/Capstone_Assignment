package com.example.darren&aidan.contactmanager;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Darren & Aidan on 4/7/2015.
 */
public class EditItem extends ActionBarActivity {


    Button editButton;
    Button deleteButton;
    Button doneEditButton;
    private TextView title;
    private TextView price;
    private TextView keywords;
    private TextView description;

    private EditText editTitle;
    private EditText editPrice;
    private EditText editDescription;
    //
    private String phone;
    private String time;
    private String latitude;
    private String longitude;
//    private String itemDescription;

    //private Item myItem;

//    private Button changeImageButton;
//    private static final int SELECT_PICTURE =1;
//    private ImageView picture;

//    private String titleOfItem;
//    private static final String baseUrlForImage = "http://ec2-52-5-107-228.compute-1.amazonaws.com/images";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_item);
        Intent intent = getIntent();
        phone = intent.getStringExtra("Phone");
        time = intent.getStringExtra("Time");
        latitude = intent.getStringExtra("Latitude");
        longitude = intent.getStringExtra("Longitude");
        //button to upload image?
//        this.picture = (ImageView) this.findViewById(R.id.pic);
//        this.changeImageButton = (Button) this.findViewById(R.id.changeImage);
//        this.changeImageButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//
//                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
//
//            }
//        });


        this.title = (TextView) this.findViewById(R.id.title);
        this.price = (TextView) this.findViewById(R.id.price);
        //this.keywords = (TextView) this.findViewById(R.id.keywords);
        this.description = (TextView) this.findViewById(R.id.description);

        this.editTitle = (EditText) this.findViewById(R.id.editTitle);
        editTitle.setVisibility(View.GONE);
        this.editPrice = (EditText) this.findViewById(R.id.editPrice);
        editPrice.setVisibility(View.GONE);
        this.editDescription = (EditText) this.findViewById(R.id.editDescription);
        editDescription.setVisibility(View.GONE);


        new GetItemDetails().execute(new ApiConnector());

        doneEditButton = (Button) this.findViewById(R.id.doneEdit);
        doneEditButton.setVisibility(View.GONE);
        deleteButton = (Button) this.findViewById(R.id.delete);

        //delete your item
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //displays pop up with postedItem created
                new DeleteItemTask().execute(new ApiConnector());
                startActivity(new Intent(getApplicationContext(), MyItems.class));
            }
        });


        editButton = (Button) this.findViewById(R.id.edit);
        //edit your item
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //displays pop up with postedItem created
                title.setVisibility(View.GONE);
                price.setVisibility(View.GONE);
                description.setVisibility(View.GONE);
                editButton.setVisibility(View.GONE);

                editTitle.setVisibility(View.VISIBLE);
                editPrice.setVisibility(View.VISIBLE);
                editDescription.setVisibility(View.VISIBLE);
                doneEditButton.setVisibility(View.VISIBLE);

                editTitle.setText(title.getText());
                editPrice.setText(price.getText());
                editDescription.setText(description.getText());

                doneEditButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        new EditItemTask().execute(new ApiConnector());
                        startActivity(new Intent(getApplicationContext(), MyItems.class));
                    }

                });




            }
        });


    }

    private class EditItemTask extends AsyncTask<ApiConnector, Long, JSONArray>
    {
        @Override
        protected JSONArray doInBackground(ApiConnector... params) {

            params[0].InsertItem(phone,editTitle.getText().toString(), editPrice.getText().toString(), "KEYWORD", editDescription.getText().toString(), latitude, longitude, "funEditString!Haven't updated Image");
            new DeleteItemTask().execute(new ApiConnector());
            //params[0].InsertItem(itemParams);
            return null;
        }
    }

    private class DeleteItemTask extends AsyncTask<ApiConnector, Long, JSONArray>
    {
        @Override
        protected JSONArray doInBackground(ApiConnector... params) {
            params[0].DeleteItem(phone, time);
            //params[0].InsertItem(itemParams);
            return null;
        }
    }

    private class GetItemDetails extends AsyncTask<ApiConnector, Long, JSONArray>
    {
        @Override
        protected JSONArray doInBackground(ApiConnector... params) {
            //it is executed on Background thread

            return params[0].GetItemDetails(phone, time);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {

            try{

                JSONObject item = jsonArray.getJSONObject(0);
                System.out.println(item);
                title.setText(item.getString("title"));
                price.setText(item.getString("price"));
                //keywords.setText(item.getString("keywords"));
                description.setText(item.getString("description"));

            }catch(Exception e){
                e.printStackTrace();

            }

        }
    }

    //This deals with selecting picture from gallery
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(resultCode==RESULT_OK){
//            if(requestCode==SELECT_PICTURE){
//                Uri selectedImageUri = data.getData();
//                if(Build.VERSION.SDK_INT < 19){
//                    String selectedImagePath = getPath(selectedImageUri);
//                    Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath);
//                    SetImage(bitmap);
//                } else{
//                    ParcelFileDescriptor parcelFileDescriptor;
//                    try{
//                        parcelFileDescriptor = getContentResolver().openFileDescriptor(selectedImageUri, "r");
//                        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
//                        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
//                        parcelFileDescriptor.close();
//                        SetImage(image);
//                    } catch(FileNotFoundException e){
//                        e.printStackTrace();
//                    } catch (IOException e){
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    }

//    private void SetImage(Bitmap image){
//        this.picture.setImageBitmap(image);
//
//        //upload
//        String imageData = encodeTobase64(image);
//
//        final List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("image", imageData));
//        params.add(new BasicNameValuePair("title", titleOfItem));
//
//        new AsyncTask<ApiConnector, Long, Boolean>(){
//            @Override
//            protected Boolean doInBackground(ApiConnector... apiConnectors) {
//                return apiConnectors[0].uploadImageToServer(params);
//            }
//        }.execute(new ApiConnector());
//    }

//    public static String encodeTobase64(Bitmap image){
//        System.gc();
//
//        if(image == null)return null;
//
//        Bitmap imagex = image;
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        imagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//
//        byte[] b = baos.toByteArray();
//
//        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
//
//        return imageEncoded;
//    }
//
//    public String getPath(Uri uri){
//        if(uri==null){
//            return null;
//        }
//        String[] projection = {MediaStore.Images.Media.DATA};
//        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
//        if(cursor != null){
//            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
//            return cursor.getString(column_index);
//        }
//        return uri.getPath();
//    }


}

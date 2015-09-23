package com.example.darren&aidan.contactmanager;

import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

//extends
public class DisplayItem extends ActionBarActivity {
    EditText message;
    Button sendMsg;
    private TextView title;
    private TextView price;
    private TextView keywords;
    private TextView description;
//
    private String phone;
    private String time;
//    private String itemDescription;

    //private Item myItem;

    private Button changeImageButton;
    private static final int SELECT_PICTURE =1;
    private ImageView picture;

    private String titleOfItem;
    private static final String baseUrlForImage = "http://ec2-52-5-107-228.compute-1.amazonaws.com/images";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_item);
        Intent intent = getIntent();
        phone = intent.getStringExtra("Phone");
        time = intent.getStringExtra("Time");
        //button to upload image?
        this.picture = (ImageView) this.findViewById(R.id.pic);
        this.changeImageButton = (Button) this.findViewById(R.id.changeImage);
        this.changeImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);

            }
        });



        this.title = (TextView) this.findViewById(R.id.title);
        this.price = (TextView) this.findViewById(R.id.price);
        this.keywords = (TextView) this.findViewById(R.id.keywords);
        this.description = (TextView) this.findViewById(R.id.description);
        message = (EditText) findViewById(R.id.messageText);
        message.setVisibility(View.GONE);
        sendMsg = (Button) findViewById(R.id.sendButton);
        sendMsg.setVisibility(View.GONE);

        if(this.title != null){
            //we have an item
//            TextView tailNum = (TextView) findViewById(R.id.tail_no);
//            tailNum.setText(this.tailNo);
            new GetItemDetails().execute(new ApiConnector());
        }
        Button callButton = (Button) findViewById(R.id.callButton);
        final Uri phoneNumber = Uri.parse("tel:" + phone);


        callButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                try {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(phoneNumber);
                    startActivity(callIntent);
                } catch (ActivityNotFoundException activityException) {
                    Log.e("Calling a Phone Number", "Call failed", activityException);
                }
            }
        });

        final Button textButton = (Button) findViewById(R.id.textButton);

        textButton.setOnClickListener(new View.OnClickListener(){
        public void onClick(View view) {

            //get rid of text, show textbox and send
            textButton.setVisibility(View.GONE);
            message.setVisibility(View.VISIBLE);
            sendMsg.setVisibility(View.VISIBLE);

        }
        });
        sendMsg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String txtMsg = String.valueOf(message.getText());
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phone, null, txtMsg, null, null);
            }
        });



    }

    //This deals with selecting picture from gallery
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            if(requestCode==SELECT_PICTURE){
                Uri selectedImageUri = data.getData();
                if(Build.VERSION.SDK_INT < 19){
                    String selectedImagePath = getPath(selectedImageUri);
                    Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath);
                    SetImage(bitmap);
                } else{
                    ParcelFileDescriptor parcelFileDescriptor;
                    try{
                        parcelFileDescriptor = getContentResolver().openFileDescriptor(selectedImageUri, "r");
                        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                        parcelFileDescriptor.close();
                        SetImage(image);
                    } catch(FileNotFoundException e){
                        e.printStackTrace();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void SetImage(Bitmap image){
        this.picture.setImageBitmap(image);

        //upload
        String imageData = encodeTobase64(image);

        final List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("image", imageData));
        params.add(new BasicNameValuePair("title", titleOfItem));

        new AsyncTask<ApiConnector, Long, Boolean>(){
            @Override
            protected Boolean doInBackground(ApiConnector... apiConnectors) {
                return apiConnectors[0].uploadImageToServer(params);
            }
        }.execute(new ApiConnector());
    }

    public static String encodeTobase64(Bitmap image){
        System.gc();

        if(image == null)return null;

        Bitmap imagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] b = baos.toByteArray();

        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        return imageEncoded;
    }

    public String getPath(Uri uri){
        if(uri==null){
            return null;
        }
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if(cursor != null){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
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
                System.out.println("######################################################################");

                JSONObject item = jsonArray.getJSONObject(0);
                System.out.println(item);
                title.setText(item.getString("title"));
                price.setText(item.getString("price"));
                //keywords.setText(item.getString("keywords"));
                description.setText(item.getString("description"));

                String urlForImage = baseUrlForImage + item.getString("imageName");
                new DownloadImageTask(picture).execute(urlForImage);
                titleOfItem = item.getString("title");
            }catch(Exception e){
                e.printStackTrace();

            }

        }
    }

}
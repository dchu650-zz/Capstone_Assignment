<?php
     	date_default_timezone_set("UTC");

// function base64_url_decode($input) {
//  return base64_decode(strtr($input, '-_,', '+/='));
// }


    	if(isset($_REQUEST['Phone'])){
                $con = mysql_connect("localhost", "root", "");
                if (!$con){
                        die('Could not connect');
                }

                mysql_select_db("capstone1", $con);

                $phone = $_REQUEST['Phone'];
                $title = $_REQUEST['Title'];
                $price = $_REQUEST['Price'];
                $keyword = $_REQUEST['Keywords'];
                $description = $_REQUEST['Description'];
                $latitude = $_REQUEST['Latitude'];
                $longitude = $_REQUEST['Longitude'];
                $image = $_REQUEST['Image'];




                $key_array = explode(" ", $keyword);



                $timestamp = date('Y-m-d G:i:s');


$result = mysql_query("INSERT INTO Items(phoneNo, title, price, description, time, latitude, longitude, encodedImage) VALUES('$phone','$title', '$price','$description', '$timestamp', '$latitude', '$longitude', '$image')") or die('Errant query');

                foreach ($key_array as $word) {
                        $result = mysql_query("INSERT INTO Keywords(phoneNo, time, keyword) VALUES('$phone','$timestamp', '$word')") or die('Errant query');
                }


                mysql_close($con);
        }
	else{
             	$output = "not found";
                print(json_encode($output));
        }
    ?>
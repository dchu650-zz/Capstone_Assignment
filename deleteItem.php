<?php
     	date_default_timezone_set("UTC");

        if(isset($_REQUEST['PhoneNumber'])){
                $con = mysql_connect("localhost", "root", "");
                if (!$con){
                        die('Could not connect');
                }

                mysql_select_db("capstone1", $con);


                $phone = $_REQUEST['PhoneNumber'];
                $time = urldecode($_REQUEST['Time']);

                $timestamp = date('Y-m-d G:i:s');


                $result = mysql_query("DELETE FROM Items WHERE phoneNo = '$phone' AND time = '$time' ") or die('Errant query');
                echo $result;

                $result = mysql_query("DELETE FROM Keywords WHERE phoneNo = '$phone' AND time = '$time'") or die('Errant query');
                echo $result;

                mysql_close($con);
        }
	else{
             	$output = "not found";
                print(json_encode($output));
        }
?>
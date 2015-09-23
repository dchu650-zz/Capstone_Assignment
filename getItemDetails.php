<?php

     	if(isset($_REQUEST['PhoneNumber'])){
                $con = mysql_connect("localhost", "root", "");
                if (!$con){
                        die('Could not connect');
                }

                mysql_select_db("capstone1", $con);

                $phoneNumber = $_REQUEST['PhoneNumber'];
                $time = urldecode($_REQUEST['Time']);



                $result = mysql_query("SELECT * FROM Items WHERE phoneNo = '$phoneNumber' AND time = '$time' ") or die('Errant query');

                while($row = mysql_fetch_assoc($result))
                {
                        $output[]=$row;
                }

                print(json_encode($output));

                mysql_close($con);
        }
	else{
             	$output = "not found";
                print(json_encode($output));
        }
?>

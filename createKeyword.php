<?php
     	date_default_timezone_set("UTC");

        if(isset($_REQUEST['Phone'])){
                $con = mysql_connect("localhost", "root", "");
                if (!$con){
                        die('Could not connect');
                }

                mysql_select_db("capstone1", $con);

                $phone = $_REQUEST['Phone'];
                $keyword = $_REQUEST['Keyword'];



                $result = mysql_query("INSERT INTO User(phoneNo, keyword) VALUES('$phone', '$keyword')") or die('Errant query');

                mysql_close($con);
        }
	else{
             	$output = "not found";
                print(json_encode($output));
        }
?>
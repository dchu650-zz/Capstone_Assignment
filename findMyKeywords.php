<?php

$con = mysql_connect("localhost", "root", "");
if (!$con){
	die('Could not connect');
}

mysql_select_db("capstone1", $con);

if(isset($_REQUEST['Phone'])){

        $phone = $_REQUEST['Phone'];

        $result = mysql_query("SELECT keyword FROM User WHERE phoneNo = '$phone' ORDER BY keyword DESC");

        while($row = mysql_fetch_assoc($result))
        {
                $output[]=$row;
        }
}

print(json_encode($output));

mysql_close($con);

?>
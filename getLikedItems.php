<?php

$con = mysql_connect("localhost", "root", "");
if (!$con){
	die('Could not connect');
}

mysql_select_db("capstone1", $con);
$phoneNumber = $_REQUEST['Phone'];

// $result = mysql_query("SELECT DISTINCT * FROM Items NATURAL JOIN User NATURAL JOIN Keywords WHERE phoneNo = '$phoneNumber' ORDER BY time DESC");
$result = mysql_query("SELECT DISTINCT * FROM Items NATURAL JOIN (SELECT * FROM User WHERE phoneNo = '$phoneNumber') AS U NATURAL JOIN (SELECT * FROM Keywords WHERE phoneNo = '$phoneNumber') AS K ORDER BY time DESC");

while($row = mysql_fetch_assoc($result))
{
        $output[]=$row;
}

print(json_encode($output));

mysql_close($con);

?>
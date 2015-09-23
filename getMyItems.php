<?php

$con = mysql_connect("localhost", "root", "");
if (!$con){
	die('Could not connect');
}

mysql_select_db("capstone1", $con);
$phoneNumber = $_REQUEST['Phone'];

$result = mysql_query("SELECT * FROM Items WHERE phoneNo = '$phoneNumber' ORDER BY time DESC");
while($row = mysql_fetch_assoc($result))
{
        $output[]=$row;
}

print(json_encode($output));

mysql_close($con);

?>



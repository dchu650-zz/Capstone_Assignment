<?php

$con = mysql_connect("localhost", "root", "");
if (!$con){
	die('Could not connect');
}

mysql_select_db("capstone1", $con);

$startItem = (int)strval($_REQUEST['StartItem']);


$result = mysql_query('SELECT * FROM Items ORDER BY time DESC LIMIT '.$startItem.', 5');

while($row = mysql_fetch_assoc($result))
{
        $output[]=$row;
}

print(json_encode($output));

mysql_close($con);

?>
<?php

$con = mysql_connect("localhost", "root", "");
if (!$con){
	die('Could not connect');
}

mysql_select_db("capstone1", $con);

if(isset($_REQUEST['Search'])){


        $search = $_REQUEST['Search'];

        $key_array = explode(" ", $search);
        $newest_key = array_pop($key_array);



        $result = mysql_query('SELECT DISTINCT keyword FROM Keywords WHERE keyword LIKE "%'.$newest_key.'%" ORDER BY time DESC');

        while($row = mysql_fetch_assoc($result))
        {
                $output[]=$row;
        }
}
else{
     	$result = mysql_query('SELECT DISTINCT keyword FROM Keywords ORDER BY time DESC');

        while($row = mysql_fetch_assoc($result))
        {
                $output[]=$row;
        }
}
print(json_encode($output));

mysql_close($con);

?>

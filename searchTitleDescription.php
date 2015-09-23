<?php

$con = mysql_connect("localhost", "root", "");
if (!$con){
	die('Could not connect');
}

mysql_select_db("capstone1", $con);

if(isset($_REQUEST['Search'])){


        $search = $_REQUEST['Search'];

        $result = mysql_query('SELECT * FROM Items WHERE Concat(title, "", description) LIKE "%'.$search.'%" ORDER BY time DESC');

        while($row = mysql_fetch_assoc($result))
        {
                $output[]=$row;
        }
}
else{
     	$result = mysql_query('SELECT * FROM Items ORDER BY time DESC');

        while($row = mysql_fetch_assoc($result))
        {
                $output[]=$row;
        }
}
print(json_encode($output));

mysql_close($con);

?>

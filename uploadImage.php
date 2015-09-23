<?php


     	$con = mysql_connect("localhost", "root", "");
                if (!$con){
                        die('Could not connect');
                }

        mysql_select_db("capstone1", $con);

        if(isset($_POST["image"]) && isset($_POST["title"])){


                $data = $_POST["image"];
                $Title = $_POST["title"];
                $ImageName = $Title.".jpg";
                $filePath = "images/".$ImageName;
                echo "file ".$filePath;
                //check if file exists
                if(file_exists($filePath)){
                        unlink($filePath);//delete old file
                }

                $myfile = fopen($filePath, "w") or die("unable to open file!");

                file_put_contents($filePath, base64_decode($data));


                mysql_query("UPDATE Items SET picture = '$ImageName' WHERE title = '$Title'") or die("Could not save image name ".mysql_error());



        }
	]else{
              	echo 'not set';
        }

	mysql_close($con);
?>
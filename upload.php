<?php
$target_dir = "uploads/";
$uploadOk = 1;

$file_path = $target_dir . basename( $_FILES['uploaded_file']['name']);
if(move_uploaded_file($_FILES['uploaded_file']['tmp_name'], $file_path)) {
   echo "success";
} else{
   echo "fail";
}

if (file_exists($target_file)) {
    echo "Sorry, file already exists.";
    $uploadOk = 0;
}

$directory = "/var/www/html/uploads/";
$phpfiles = glob($directory. "*.*");
echo "Files in directory:";
echo "<br>";
echo "<br>";
foreach ($phpfiles as $phpfile) {
    $filepath = 'uploads/'.basename($phpfile);
    echo '<a href="'. $filepath .'">' .basename($phpfile). '</a>, Size: ' . filesize($phpfile). ' bytes <br><br>';
}

?>

<?php
include ("libraries/database.php");
$email=$_REQUEST['email'];

$data=new database("timphongtro");
$sql="SELECT * FROM taikhoan WHERE email='$email'";
$data->query($sql);

header('Content-type: text/xml');
echo "<result>";
if($data->num_rows()==0){
    echo "false";
}
else{
    echo "true";
}
echo "</result>";
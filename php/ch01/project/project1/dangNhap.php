<?php
    require 'libraries/database.php';
    
    $email=$_REQUEST['email'];
    $password=$_REQUEST['matKhau'];
    
    $data=new database("timphongtro");
    $sql= "SELECT * FROM taikhoan WHERE email='$email' AND MatKhau='$password'";
    $data->query($sql);
    
    header('Content-type: text/xml');
    echo "<result>";
    if($data->num_rows()==0){
        echo "false";
    }
    else{
        $result=$data->fetch();
        echo $result['IDTaiKhoan'];
    }
    echo "</result>";
?>
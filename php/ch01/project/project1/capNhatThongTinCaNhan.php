<?php
    require 'libraries/database.php';
    
    $idN=$_REQUEST['idN'];
    $tenHienThi=$_REQUEST['tenHienThi'];
    $tinhTrang=$_REQUEST['tinhTrang'];
    
    $data=new database("timphongtro");
    $sql1="select * from taikhoan where IDTaiKhoan='$idN'";
    $data->query($sql1);
    
    if($data->num_rows()!=0){
        $sql2="update taikhoan set TenHienThi='$tenHienThi', TinhTrangHienTai='$tinhTrang' where IDTaiKhoan='$idN'";
        $result2=$data->query($sql2);
        if($result2){
            header('Content-type: text/xml');
            echo "<result>"."true"."</result>";
        }
        else{
            header('Content-type: text/xml');
            echo "<result>"."Cập nhật vào cơ sở dữ liệu thất bại"."</result>";
        }
    }
    else{
        header('Content-type: text/xml');
        echo "<result>"."Tài khoản không tồn tại trong hệ thống"."</result>";
    }
?>
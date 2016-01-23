<?php
    require 'libraries/database.php';
    
    $idN=$_REQUEST['idN'];
    $matKhauCu=$_REQUEST['matKhauCu'];
    $matKhauMoi=$_REQUEST['matKhauMoi'];
    
    $data=new database("timphongtro");
    $sql1="select * from taikhoan where IDTaiKhoan='$idN'";
    $data->query($sql1);
    
    if($data->num_rows()!=0){
        $result1=$data->fetch();
        if($result1['MatKhau']==$matKhauCu){
            $sql2="update taikhoan set MatKhau='$matKhauMoi' where IDTaiKhoan='$idN'";
            $result2=$data->query($sql2);
            if($result2){
                header('Content-type: text/xml');
                echo "<result>"."true"."</result>";
            }
            else{
                header('Content-type: text/xml');
                echo "<result>"."Thay đổi mật khẩu trong cơ sở dữ liệu thất bại"."</result>";
            }
        }
        else{
            header('Content-type: text/xml');
            echo "<result>"."Mật khẩu cũ không chính xác"."</result>";
        }
    }
    else{
        header('Content-type: text/xml');
        echo "<result>"."Tài khoản không tồn tại trong hệ thống"."</result>";
    }
 ?>   
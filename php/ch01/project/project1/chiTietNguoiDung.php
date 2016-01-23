<?php
    require 'libraries/database.php';
    $idN=$_REQUEST['idN'];
    
    $data=new database("timphongtro");
    $sql1="select * from taikhoan where IDTaiKhoan='$idN'";
    $data->query($sql1);
    
    if($data->num_rows()!=0){
        $result1=$data->fetch();
        
        $sql2="select * from taikhoan_o_phongtro where IDTaiKhoan='$idN'";
        $data->query($sql2);
        $result2=$data->fetch();
        
        header('Content-type: text/xml');
        echo "<result>";
        echo "<tenHienThi>".$result1['TenHienThi']."</tenHienThi>";
        echo "<email>".$result1['email']."</email>";
        echo "<tinhTrang>".$result1['TinhTrangHienTai']."</tinhTrang>";
        if($result1['TinhTrangHienTai']!=2){
            echo "<idP>".$result2['IDPhongTro']."</idP>";
        }
        else{
            echo "<idP>"."null"."</idP>";
        }
        echo "</result>";
    }
    else{
        header('Content-type: text/xml');
        echo "<result>"."false"."</result>";
    }
?>
<?php
    require 'libraries/database.php';
    
    $idN=$_REQUEST['idN'];
    $idP=$_REQUEST['idP'];
    
    $data=new database("timphongtro");
    $sql1="select * from taikhoan_o_phongtro where IDTaiKhoan='$idN' and IDPhongTro='$idP'";
    $data->query($sql1);
    
    if($data->num_rows()!=0){
        $sql2="select * from taikhoan where IDTaiKhoan='$idN'";
        $data->query($sql2);
        $result2=$data->fetch();
        
        $sql3="select * from phongtro where IDPhongTro='$idP'";
        $data->query($sql3);
        $result3=$data->fetch();
        
        if(($result2['TinhTrangHienTai']==0)&&($result3['SoNguoiTrongPhong']==1)){
            $sql4="delete from taikhoan_o_phongtro where IDTaiKhoan='$idN' and IDPhongTro='$idP'";
            $result4=$data->query($sql4);
            
            $sql5="delete from phongtro where IDPhongTro='$idP'";
            $result5=$data->query($sql5);
            
            $sql6="update taikhoan set TinhTrangHienTai=2 where IDTaiKhoan='$idN'";
            $result6=$data->query($sql6);
            
            if($result4&&$result5&&$result6){
                header('Content-type: text/xml');
                echo "<result>"."true"."</result>";
            }
            else{
                header('Content-type: text/xml');
                echo "<result>"."Thay đổi thông tin phòng hoặc tài khoản thất bại"."</result>";
            }
        }
        else{
            header('Content-type: text/xml');
            echo "<result>"."Điều kiện xóa phòng không thỏa mãn"."</result>";
        }
    }
    else{
        header('Content-type: text/xml');
        echo "<result>"."Tài khoản hoặc phòng không tồn tại"."</result>";
    }
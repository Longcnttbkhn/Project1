<?php
    require 'libraries/database.php';
    
    $idN=$_REQUEST['idN'];
    $idP=$_REQUEST['idP'];
    $giaCa=$_REQUEST['giaCa'];
    $boSung=$_REQUEST['boSung'];
    $tinhTrang=$_REQUEST['tinhTrang'];
    $cauHoiBaoMat=$_REQUEST['cauHoiBaoMat'];
    $traLoiCauHoiBaoMat=$_REQUEST['traLoiCauHoiBaoMat'];
    $danhGia=$_REQUEST['danhGia'];
    
    $data=new database("timphongtro");
    $sql1="select * from taikhoan_o_phongtro where IDTaiKhoan='$idN' and IDPhongTro='$idP'";
    $data->query($sql1);
    
    if($data->num_rows()!=0){
        $sql2="select * from phongtro where IDPhongTro='$idP'";
        $data->query($sql2);
        if($data->num_rows()!=0){
            $sql3="update phongtro set GiaPhong='$giaCa', ThongTinBoSung='$boSung', TinhTrang='$tinhTrang', CauHoiBaoMat='$cauHoiBaoMat', TraLoiCauHoiBaoMat='$traLoiCauHoiBaoMat' where IDPhongTro='$idP'";
            $result3=$data->query($sql3);
            $sql4="update taikhoan_o_phongtro set DiemDanhGia='$danhGia' where IDTaiKhoan='$idN' and IDPhongTro='$idP'";
            $result4=$data->query($sql4);
            if($result3&&$result4){
                header('Content-type: text/xml');
                echo "<result>"."true"."</result>";
            }
            else{
                header('Content-type: text/xml');
                echo "<result>"."Thay đổi thông tin phòng thất bại"."</result>";
            }
        }
        else{
            header('Content-type: text/xml');
            echo "<result>"."Phòng trọ không tồn tại"."</result>";
        }
    }
    else{
        header('Content-type: text/xml');
        echo "<result>"."Tài khoản không tồn tại"."</result>";
    }
?>
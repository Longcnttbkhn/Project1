<?php
    require 'libraries/database.php';
    
    $idN=$_REQUEST['idN'];
    $dienTich=$_REQUEST['dienTich'];
    $giaCa=$_REQUEST['giaCa'];
    $khuVuc=$_REQUEST['khuVuc'];
    $diaChi=$_REQUEST['diaChi'];
    $boSung=$_REQUEST['boSung'];
    
    $data=new database("timphongtro");
    $sql1="select * from taikhoan where IDTaiKhoan='$idN'";
    $data->query($sql1);
    
    if($data->num_rows()!=0){
        $result1=$data->fetch();
        $soLanDeXuat=$result1['SoLanDeXuatPhong'];
        if($soLanDeXuat!=0&&$result1['TinhTrangHienTai']!=2){
            $now= getdate();
            $currentDate = $now["year"] . "-" . $now["mon"] . "-" . $now["mday"];
            
            $sql2="INSERT INTO `phongdexuat`( `IDTaiKhoanDeXuat`, `DiaChi`, `KhuVuc`, `DienTich`, `GiaPhong`, `NgayDeXuat`, `ThongTinBoSung`) VALUES ('$idN','$diaChi','$khuVuc','$dienTich','$giaCa','$currentDate','$boSung')";
            $result2=$data->query($sql2);
            if($result2){
                $soLanDeXuat-=1;
                $sql3="Update taikhoan Set SoLanDeXuatPhong = '$soLanDeXuat' Where IDTaiKhoan='$idN'";
                $result3=$data->query($sql3);
                if($result3){
                    header('Content-type: text/xml');
                    echo "<result>"."true"."</result>";
                }
                else{
                    header('Content-type: text/xml');
                    echo "<result>"."Thay đổi thông tin người sử dụng thất bại"."</result>";
                }
            }else{
                header('Content-type: text/xml');
                echo "<result>"."Thêm phòng đề xuất vào cơ sở dữ liệu thất bại"."</result>";
            }
        }
        else{
            header('Content-type: text/xml');
            echo "<result>"."Tài khoản không đủ điều kiện đề xuất phòng"."</result>";
        }
    }
    else{
        header('Content-type: text/xml');
        echo "<result>"."Tài khoản không tồn tại"."</result>";
    }
?>
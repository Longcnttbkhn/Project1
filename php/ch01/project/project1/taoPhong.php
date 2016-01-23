<?php
require 'libraries/database.php';
// date_default_timezone_set('Asia/Ha_Noi');

$idN=$_REQUEST['idN'];
$dienTich=$_REQUEST['dienTich'];
$giaCa=$_REQUEST['giaCa'];
$khuVuc=$_REQUEST['khuVuc'];
$diaChi=$_REQUEST['diaChi'];
$boSung=$_REQUEST['boSung'];
$cauHoiBaoMat=$_REQUEST['cauHoiBaoMat'];
$traLoiCauHoiBaoMat=$_REQUEST['traLoiCauHoiBaoMat'];
$danhGia=$_REQUEST['danhGia'];

$data=new database("timphongtro");
$sql1="Select * from taiKhoan where IDTaiKhoan='$idN'";
$data->query($sql1);
if($data->num_rows()!=0){
    $result1=$data->fetch();
    if(($result1['SoLanTaoPhong']!=0)&&($result1['TinhTrangHienTai']==2)){
        $sql2="Insert Into phongtro (DiaChi,KhuVuc,DienTich,GiaPhong, TinhTrang,ChiSoTinhTrang,SoNguoiTrongPhong,CauHoiBaoMat,TraLoiCauHoiBaoMat,ThongTinBoSung,IDTaiKhoanTao,DiemDanhGiaTB,TGCoNguoiO) Values ('$diaChi','$khuVuc','$dienTich','$giaCa',1,0,1,'$cauHoiBaoMat','$traLoiCauHoiBaoMat','$boSung','$idN','$danhGia',0)";
        $result2=$data->query($sql2);
        if($result2){
            $sql3="Update taikhoan Set SoLanTaoPhong = 0, TinhTrangHienTai=1 Where IDTaiKhoan='$idN'";
            $result3=$data->query($sql3);
            if($result3){
                $sql4="Select * from phongtro where IDTaiKhoanTao ='$idN'";
                $data->query($sql4);
                $result4=$data->fetch();
                $idP=$result4['IDPhongTro'];
                $now= getdate();
                $currentDate = $now["year"] . "-" . $now["mon"] . "-" . $now["mday"]; 
//                 $sql5="Insert Into taikhoan_o_phongtro (IDPhongtro,NgayChuyenDen,DienDanhGia,IDTaiKhoan) Values ('$idN','2015-11-10 00:00:00','$danhGia','$idP')";
                $sql5="INSERT INTO `timphongtro`.`taikhoan_o_phongtro` (`IDPhongTro`, `NgayChuyenDen`, `NgayChuyenDi`, `DiemDanhGia`, `IDTaiKhoan`) VALUES ('$idP', '$currentDate', NULL, '$danhGia', '$idN')";
                $result5=$data->query($sql5);
                if($result5){
                    header('Content-type: text/xml');
                    echo "<result>"."true"."</result>";
                }
                else{
                    header('Content-type: text/xml');
                    echo "<result>"."Thêm dữ liệu thất bại"."</result>";
                }
            }
            else{
                header('Content-type: text/xml');
                echo "<result>"."Thay đổi thông tin thất bại"."</result>";
            }
        }else{
            header('Content-type: text/xml');
            echo "<result>"."Thêm dữ liệu thất bại"."</result>";
        }
    }
    else{
        header('Content-type: text/xml');
        echo "<result>"."Điều kiện tạo phòng không thỏa mãn"."</result>";
    }
}
else{
    header('Content-type: text/xml');
    echo "<result>"."Tài khoản không tồn tại"."</result>";
}
?>

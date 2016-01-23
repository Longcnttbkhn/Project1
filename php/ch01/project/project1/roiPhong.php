<?php
    require 'libraries/database.php';
    require 'libraries/time.php';
    
    $idN=$_REQUEST['idN'];
    $idP=$_REQUEST['idP'];
    
    $data=new database("timphongtro");
    $sql1="select * from taikhoan_o_phongtro where IDTaiKhoan='$idN' and IDPhongTro='$idP'";
    $data->query($sql1);
    if($data->num_rows()!=0){
        $result1=$data->fetch();
        $now= getdate();
        $currentDate = $now["year"] . "-" . $now["mon"] . "-" . $now["mday"];
        $time=new time($result1['NgayChuyenDen'], $currentDate);
        $soNgayO=$time->day()+1; 
        
        $sql2="select * from phongtro where IDPhongTro='$idP'";
        $data->query($sql2);
        $result2=$data->fetch();
        
        $diemDanhgia=($result2['DiemDanhGiaTB']*$result2['TGCoNguoiO']+$result1['DiemDanhGia']*$soNgayO)/($result2['TGCoNguoiO']+$soNgayO);
        
        $sql3="select *from taikhoan where IDTaiKhoan='$idN'";
        $data->query($sql3);
        $result3=$data->fetch();
        
        if($result3['TinhTrangHienTai']==0){
            $sql4="update taikhoan set TinhTrangHienTai=2 where IDTaiKhoan='$idN'";
            $result4=$data->query($sql4);
            
            $sql5="delete from taikhoan_o_phongtro where IDTaiKhoan='$idN' and IDPhongTro='$idP'";
            $result5=$data->query($sql5);
            
            $soNguoiTrongPhong=$result2['SoNguoiTrongPhong']-1;
            
            $sql6="update phongtro set SoNguoiTrongPhong='$soNguoiTrongPhong', TGCoNguoiO='$soNgayO', DiemDanhGiaTB= '$diemDanhgia' where IDPhongTro='$idP'";
            $result6=$data->query($sql6);
            
            if($soNguoiTrongPhong==0){
                $sql7="update phongtro set TinhTrang=2 where IDPhongTro='$idP'";
                $data->query($sql7);
            }
            
            if($result4&&$result5&&$result6){
                header('Content-type: text/xml');
                echo "<result>"."true"."</result>";
            }
            else{
                header('Content-type: text/xml');
                echo "<result>"."Thay đổi thông tin người dùng và phòng thất bại"."</result>";
            }
        }
        else{
            header('Content-type: text/xml');
            echo "<result>"."Trạng thái của người dùng không phải là muốn chuyển"."</result>";
        }
    }
    else{
        header('Content-type: text/xml');
        echo "<result>"."Tài khoản hoặc phòng không tồn tại"."</result>";
    }
?>
    
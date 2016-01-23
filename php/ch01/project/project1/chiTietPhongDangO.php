<?php
    require 'libraries/database.php';
    
    $idN=$_REQUEST['idN'];
    
    $data= new database("timphongtro");
    $sql1="select * from taikhoan_o_phongtro where IDTaiKhoan='$idN'";
    $data->query($sql1);
    
    if($data->num_rows()!=0){
        $result1=$data->fetch();
        $idP=$result1['IDPhongTro'];
        
        $sql2="select * from phongtro where IDPhongTro='$idP'";
        $data->query($sql2);
        $result2=$data->fetch();
        
        
        $sql3="select * from taikhoan_o_phongtro, taikhoan where taikhoan_o_phongtro.IDTaiKhoan=taikhoan.IDTaiKhoan and IDPhongTro='$idP'";
        $data->query($sql3);
        $result3=$data->fetchAll('user');
        
        header('Content-type: text/xml');
        echo "<result>";
        echo "<idP>".$idP."</idP>";
        echo "<dienTich>".$result2['DienTich']."</dienTich>";
        echo "<gia>".$result2['GiaPhong']."</gia>";
        echo "<khuVuc>".$result2['KhuVuc']."</khuVuc>";
        echo "<diaChi>".$result2['DiaChi']."</diaChi>";
        echo "<boSung>".$result2['ThongTinBoSung']."</boSung>";
        echo "<tinhTrang>".$result2['TinhTrang']."</tinhTrang>";
        echo "<soNguoiO>".$result2['SoNguoiTrongPhong']."</soNguoiO>";
        echo "<danhSachNguoiO>";
        foreach($result3 as $index => $user) {
            if(is_array($user)) {
                foreach($user as $key => $value) {
                    echo "<".$key.">";
                    if(is_array($value)) {
                        echo "<tenHienThi>".$value['TenHienThi']."</tenHienThi>";
                    }
                    echo "</".$key.">";
                }
            }
        }
        echo "</danhSachNguoiO>";
        echo "<cauHoiBaoMat>".$result2['CauHoiBaoMat']."</cauHoiBaoMat>";
        echo "<traLoiCauHoiBaoMat>".$result2['TraLoiCauHoiBaoMat']."</traLoiCauHoiBaoMat>";
        echo "<danhGia>".$result1['DiemDanhGia']."</danhGia>";
        echo "</result>";
        
    }
    else{
        header('Content-type: text/xml');
        echo "<result>"."false"."</result>";
    }
?>
<?php
    require 'libraries/database.php';
    
    $idP=$_REQUEST['idP'];
    $idN=$_REQUEST['idN'];
    $traLoiCauHoiBaoMat=$_REQUEST['traLoiCauHoiBaoMat'];
    
    $data=new database("timphongtro");
    $sql1="select * from taikhoan where IDTaiKhoan='$idN'";
    $data->query($sql1);
    
    if($data->num_rows()!=0){
        $result1=$data->fetch();
        //neu tai khoan co tinh trang la chua co cho o
        if($result1['TinhTrangHienTai']==2){
            $sql2="select * from phongtro where IDPhongTro='$idP'";
            $data->query($sql2);
            if($data->num_rows()!=0){
                $result2=$data->fetch();
                //neu phong khong day
                if($result2['TinhTrang']!=1){
                    if($traLoiCauHoiBaoMat==$result2['TraLoiCauHoiBaoMat']){
                        $sql3="Update taikhoan Set TinhTrangHienTai=1 Where IDTaiKhoan='$idN'";
                        $result3=$data->query($sql3);
                        if($result3){
                            if($result2['TinhTrang']==2){
                                $sql4="Update phongtro Set TinhTrang = 1, SoNguoiTrongPhong=1 where IDPhongTro='$idP'";
                                $data->query($sql4);
                            }
                            if($result2['TinhTrang']==0){
                                $soNguoiTrongPhong=$result2['SoNguoiTrongPhong']+1;
                                $sql4="Update phongtro Set SoNguoiTrongPhong='$soNguoiTrongPhong' where IDPhongTro='$idP'";
                                $data->query($sql4);
                            }
                            $diemDanhGia=$result2['DiemDanhGiaTB'];
                            $now= getdate();
                            $currentDate = $now["year"] . "-" . $now["mon"] . "-" . $now["mday"];
                            $sql5="INSERT INTO `timphongtro`.`taikhoan_o_phongtro` (`IDPhongTro`, `NgayChuyenDen`, `NgayChuyenDi`, `DiemDanhGia`, `IDTaiKhoan`) VALUES ('$idP', '$currentDate', NULL, '$diemDanhGia', '$idN')";
                            $result5=$data->query($sql5);
                            if($result5){
                                header('Content-type: text/xml');
                                echo "<result>"."true"."</result>";
                            }
                            else{
                                header('Content-type: text/xml');
                                echo "<result>"."Thêm dữ liệu vào cơ sở dữ liệu thất bại"."</result>";
                            }
                        }
                        else{
                            header('Content-type: text/xml');
                            echo "<result>"."Thay đổi trạng thái tài khoản thất bại"."</result>";
                        }
                    }
                    else{
                        header('Content-type: text/xml');
                        echo "<result>"."Câu trả lời không chính xác"."</result>";
                    }
                }
                else{
                    header('Content-type: text/xml');
                    echo "<result>"."Phòng ở trạng thái đầy"."</result>";
                }
            }
            else{
                header('Content-type: text/xml');
                echo "<result>"."Phòng không tồn tại trong hệ thống"."</result>";
            }
        }
        else{
            header('Content-type: text/xml');
            echo "<result>"."Tình trạng của tài khoản không phải là chưa có chỗ ở"."</result>";
        }
    }
    else{
        header('Content-type: text/xml');
        echo "<result>"."Tài khoản không tồn tại trong hệ thống"."</result>";
    }
?>
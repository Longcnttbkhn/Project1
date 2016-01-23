<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
<?php
require 'libraries/database.php';
$passkey=$_GET['passkey'];

$data=new database("timphongtro");
$sql1="SELECT * FROM dangkytaikhoan WHERE MaXacNhan ='$passkey'";
$data->query($sql1);

if($data->num_rows()!=0){
    $result1=$data->fetch();
    $email=$result1['email'];
    $tenHienThi=$result1['TenHienThi'];
    $matKhau=$result1['MatKhau'];
    
//     $sql2="SELECT * From taikhoan where IDTaiKhoan>=All(Select IDTaiKhoan from taikhoan)";
//     $data->query($sql2);
//     $result=$data->fetch();
//     $IDTK=$result['IDTaiKhoan'];
//     $IDTK+=1;
// //     echo $IDTK;
   
     $sql3="INSERT INTO taikhoan (IDTaiKhoan, email, MatKhau,TenHienThi, TinhTrangHienTai, SoLanTaoPhong, SoLanDeXuatPhong)VALUES(null,'$email', '$matKhau', '$tenHienThi', 2,1,2)";
    
    // Nếu thành công thì di chuyển dữ liệu từ bảng "temp_members_db" sang bảng "registered_members
    
    if($data->query($sql3)){
        echo "Tài khoản của bạn đã được kích hoạt. Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi";
        // xóa bỏ thông tin người sử dụng này từ bảng "temp_members_db" có passkey này
        $sql4="DELETE FROM dangkytaikhoan WHERE email = '$email'";
        $data->query($sql4);
    }
}
    // Nếu không tìm thấy passkey, hiển thị thông báo
else {
    echo "Chuỗi xác nhận sai hoặc tài khoản đã được kích hoạt";
}
?>
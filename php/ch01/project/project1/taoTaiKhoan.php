
<?php
require "libraries/database.php";
require "phpMailer/class.phpmailer.php";
require "phpMailer/PHPMailerAutoload.php";

$maXacNhan=md5(uniqid(rand()));
$email= $_REQUEST['email'];
$tenHienThi=$_REQUEST['tenHienThi'];
$matKhau=$_REQUEST['matKhau'];


$data=new database("timphongtro");
$sql1="SELECT * FROM taikhoan WHERE email='$email'";
$data->query($sql1);
if($data->num_rows()==0){

    $sql="INSERT INTO dangkytaikhoan (`MaXacNhan`, `email`, `TenHienThi`, `MatKhau`) VALUES ('$maXacNhan','$email','$tenHienThi','$matKhau')";
    $result=$data->query($sql);


    if($result){
        $mail = new PHPMailer();
        
        $mail->IsSMTP();
        
        $mail->SMTPDebug  = 0;
        
        $mail->Debugoutput = "html"; // Lỗi trả về hiển thị với cấu trúc HTML
        $mail->Host       = "smtp.gmail.com"; //host smtp để gửi mail
        $mail->Port       = 587; // cổng để gửi mail
        $mail->SMTPSecure = "tls"; //Phương thức mã hóa thư - ssl hoặc tls
        $mail->SMTPAuth   = true; //Xác thực SMTP
        $mail->Username   = "banamhd1999@gmail.com"; // Tên đăng nhập tài khoản Gmail
        $mail->Password   = "haiduong159"; //Mật khẩu của gmail
        $mail->SetFrom('banamhd1999@gmail@gmail.com', 'Test Email'); // Thông tin người gửi
        $mail->AddReplyTo('banamhd1999@gmail@gmail.com','Test Reply');// Ấn định email sẽ nhận khi người dùng reply lại.
        $mail->AddAddress($email, $tenHienThi);//Email của người nhận
        $mail->Subject = "Thu Xac Nhan Dang Ky Tai Khoan"; //Tiêu đề của thư
        $message="Đường link kích hoạt : \r\n";
        $message.="Nhấp vào link sau để kích hoạt tài khoản \r\n";
        $message.="http://http://192.168.173.1/php/ch01/project/project1/xacNhanDangKy.php?passkey=$maXacNhan"; // đường link xác nhận
        $mail->MsgHTML($message); //Nội dung của bức thư.
        // $mail->MsgHTML(file_get_contents("email-template.html"), dirname(__FILE__));
        // Gửi thư với tập tin html
        $mail->AltBody = "This is a plain-text message body";//Nội dung rút gọn hiển thị bên ngoài thư mục thư.
    }

    header('Content-type: text/xml');
    echo "<result>";
    if(!$mail->Send()){
        echo "Gửi thư thất bại";
    }
    else{
        echo "true";
    }
    echo "</result>";
}
else{
    header('Content-type: text/xml');
    echo "<result>"."Tài khoản đã tồn tại"."</result>";
}

?>
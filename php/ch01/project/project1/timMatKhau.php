<?php
require 'libraries/database.php';
require "phpMailer/class.phpmailer.php";
require "phpMailer/PHPMailerAutoload.php";

$email= $_REQUEST['email'];

$data=new database("timphongtro");
$sql="select * from taikhoan where email='$email' ";
$data->query($sql);

if($data->num_rows()!=0){
    $result=$data->fetch();
    $matKhau=$result['MatKhau'];
    $tenHienThi=$result['TenHienThi'];
    
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
    $mail->Subject = "Thu Gui Ve Mat Khau Tu Ung Dung Tim Nha Tro"; //Tiêu đề của thư
    $message="Mật khẩu của tài khoản $email trong hệ thống là: $matKhau .  \r\n";
    $message.="Cám ơn bạn đã sử dụng dịc vụ của chúng tôi !";
    $mail->MsgHTML($message); //Nội dung của bức thư.
    // $mail->MsgHTML(file_get_contents("email-template.html"), dirname(__FILE__));
    // Gửi thư với tập tin html
    $mail->AltBody = "This is a plain-text message body";//Nội dung rút gọn hiển thị bên ngoài thư mục thư.
    
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
    echo "<result>"."Email không tồn tại trong hệ thống"."</result>";
}
?>

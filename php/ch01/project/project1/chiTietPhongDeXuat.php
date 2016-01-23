<?php
    require 'libraries/database.php';
    
    $idP=$_REQUEST['idP'];
    
    $data=new database("timphongtro");
    $sql="select * from phongdexuat where IDPhongDeXuat ='$idP'";
    $data->query($sql);
    
    if($data->num_rows()!=0){
        $result=$data->fetch();
        
        header('Content-type: text/xml');
        echo "<result>";
        echo "<diaChi>".$result['DiaChi']."</diaChi>";
        echo "<boSung>".$result['ThongTinBoSung']."</boSung>";
        echo "</result>";
    }
    else{
        header('Content-type: text/xml');
        echo "<result>"."false"."</result>";
    }
?>
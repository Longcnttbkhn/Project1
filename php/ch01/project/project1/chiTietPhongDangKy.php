<?php
    require 'libraries/database.php';
    
    $idP=$_REQUEST['idP'];
    
    $data=new database("timphongtro");
    $sql="select * from phongtro where IDPhongTro='$idP'";
    $data->query($sql);
    
    if($data->num_rows()!=0){
        $result= $data->fetch();
        
        header('Content-type: text/xml');
        echo "<result>";
        echo "<diaChi>".$result['DiaChi']."</diaChi>";
        echo "<boSung>".$result['ThongTinBoSung']."</boSung>";
        echo "<tinhTrang>".$result['TinhTrang']."</tinhTrang>";
        echo "<danhGia>".$result['DiemDanhGiaTB']."</danhGia>";
        echo "</result>";
    }
    else{
        header('Content-type: text/xml');
        echo "<result>"."false"."</result>";
    }
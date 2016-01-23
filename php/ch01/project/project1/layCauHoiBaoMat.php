<?php
    require 'libraries/database.php';
    
    $idP=$_REQUEST['idP'];
    
    $data= new database("timphongtro");
    $sql="select * from phongtro where IDPhongTro='$idP'";
    $data->query($sql);
    
    if($data->num_rows()!=0){
        $result= $data-> fetch();
        
        header('Content-type: text/xml');
        echo "<result>";
        echo "<cauHoiBaoMat>".$result['CauHoiBaoMat']."</cauHoiBaoMat>";
        echo "</result>";
    }
    else{
        header('Content-type: text/xml');
        echo "<result>"."false"."</result>";
    }
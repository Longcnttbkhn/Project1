<?php
    require 'libraries/database.php';
    
    $idP=$_REQUEST['idP'];
    $khuVuc=$_REQUEST['khuVuc'];
    $dienTichMin=$_REQUEST['dienTichMin'];
    $dienTichMax=$_REQUEST['dienTichMax'];
    $giaMin=$_REQUEST['giaMin'];
    $giaMax=$_REQUEST['giaMax'];
    $kieuPhong=$_REQUEST['kieuPhong'];
    $tinhTrangPhong=$_REQUEST['tinhTrangPhong'];
    
    $data1=new database("timphongtro");
    $data2=new database("timphongtro");
    if($idP!="null"){
        $sql1="select * from phongtro where IDPhongTro='$idP'";
        $data1->query($sql1);
        $n1=$data1->num_rows();
        if($kieuPhong=="dangKy"){
            if($n1!=0){
                $result1=$data1->fetch();
                
                header('Content-type: text/xml');
                echo "<result>";
                echo "<phong>";
                echo "<idP>".$idP."</idP>";
                echo "<kieu>"."dangKy"."</kieu>";
                echo "<khuVuc>".$result1['KhuVuc']."</khuVuc>";
                echo "<dienTich>".$result1['DienTich']."</dienTich>";
                echo "<gia>".$result1['GiaPhong']."</gia>";
                echo "</phong>";
                echo "</result>";
            }
            else{
                header('Content-type: text/xml');
                echo "<result>"."false"."</result>";    
            }
        }
        $sql2="select * from phongdexuat where IDPhongDeXuat='$idP'";
        $data2->query($sql2);
        $n2=$data2->num_rows();
        if($kieuPhong=="deXuat"){
            if($n2!=0){
                $result2=$data2->fetch();
                
                header('Content-type: text/xml');
                echo "<result>";
                echo "<phong>";
                echo "<idP>".$idP."</idP>";
                echo "<kieu>"."deXuat"."</kieu>"; 
                echo "<khuVuc>".$result2['KhuVuc']."</khuVuc>";
                echo "<dienTich>".$result2['DienTich']."</dienTich>";
                echo "<gia>".$result2['GiaPhong']."</gia>";
                echo "</phong>";
                echo "</result>";
            }
            else{
                header('Content-type: text/xml');
                echo "<result>"."false"."</result>";
            }
        }
        if($kieuPhong=="tatCa"){
            if($n1==0&&$n2==0){
                header('Content-type: text/xml');
                echo "<result>"."false"."</result>";
            }
            else{
                if($n1!=0){
                    $result1=$data1->fetch();
                    
                    header('Content-type: text/xml');
                    echo "<result>";
                    echo "<phong>";
                    echo "<idP>".$idP."</idP>";
                    echo "<kieu>"."dangKy"."</kieu>";
                    echo "<khuVuc>".$result1['KhuVuc']."</khuVuc>";
                    echo "<dienTich>".$result1['DienTich']."</dienTich>";
                    echo "<gia>".$result1['GiaPhong']."</gia>";
                    echo "</phong>";
                    
                    if($n2!=0){
                        $result2=$data2->fetch();
                        
                        echo "<phong>";
                        echo "<idP>".$idP."</idP>";
                        echo "<kieu>"."deXuat"."</kieu>";  
                        echo "<khuVuc>".$result2['KhuVuc']."</khuVuc>";
                        echo "<dienTich>".$result2['DienTich']."</dienTich>";
                        echo "<gia>".$result2['GiaPhong']."</gia>";
                        echo "</phong>";
                    }
                  echo "</result>";
                }
                else{
                    $result2=$data2->fetch();
                    
                    header('Content-type: text/xml');
                    echo "<result>";
                    echo "<phong>";
                    echo "<idP>".$idP."</idP>";
                    echo "<kieu>"."deXuat"."</kieu>";
                    echo "<khuVuc>".$result2['KhuVuc']."</khuVuc>";
                    echo "<dienTich>".$result2['DienTich']."</dienTich>";
                    echo "<gia>".$result2['GiaPhong']."</gia>";
                    echo "</phong>";
                    echo "</result>";
                }
            }
        }
    }
    else{
        if($tinhTrangPhong=="phongTrong"){
            $sql3="select * from phongtro where KhuVuc='$khuVuc' and DienTich>='$dienTichMin' and DienTich<='$dienTichMax' and GiaPhong>='$giaMin' and GiaPhong<='$giaMax' and TinhTrang=2 ORDER By DiemDanhGiaTB DESC";
        }
        else if($tinhTrangPhong=="canTimThem"){
            $sql3="select * from phongtro where KhuVuc='$khuVuc' and DienTich>='$dienTichMin' and DienTich<='$dienTichMax' and GiaPhong>='$giaMin' and GiaPhong<='$giaMax'and TinhTrang=0 ORDER By DiemDanhGiaTB DESC";
        }
        else{
            $sql3="select * from phongtro where KhuVuc='$khuVuc' and DienTich>='$dienTichMin' and DienTich<='$dienTichMax' and GiaPhong>='$giaMin' and GiaPhong<='$giaMax'and TinhTrang in (0,2) ORDER By DiemDanhGiaTB DESC";
        }
        $data1->query($sql3);
        $n3=$data1->num_rows();
        if($kieuPhong=="dangKy"){
            if($n3!=0){
                $result3=$data1->fetchAll("phong");
                
                header('Content-type: text/xml');
                echo "<result>";
                foreach($result3 as $index => $room) {
                    if(is_array($room)) {
                        foreach($room as $key => $value) {
                            echo "<".$key.">";
                            if(is_array($value)) {
                            	echo "<idP>".$value['IDPhongTro']."</idP>";
                                echo "<kieu>"."dangKy"."</kieu>";
                                echo "<khuVuc>".$value['KhuVuc']."</khuVuc>";
                                echo "<dienTich>".$value['DienTich']."</dienTich>";
                                echo "<gia>".$value["GiaPhong"]."</gia>";
                            }
                            echo "</".$key.">";
                        }
                    }
                }
                echo "</result>";
            }
            else{
                header('Content-type: text/xml');
                echo "<result>"."false"."</result>";
            }
        }
        
        $sql4="select * from phongdexuat where KhuVuc='$khuVuc' and DienTich>='$dienTichMin' and DienTich<='$dienTichMax' and GiaPhong>='$giaMin' and GiaPhong<='$giaMax'";
        $data2->query($sql4);
        $n4=$data2->num_rows();
        
        if($kieuPhong=="deXuat"){
            if($n4!=0){
                $result4=$data2->fetchAll("phong");
                
                header('Content-type: text/xml');
                echo "<result>";
                foreach($result4 as $index => $room) {
                    if(is_array($room)) {
                        foreach($room as $key => $value) {
                            echo "<".$key.">";
                            if(is_array($value)) {
                            	echo "<idP>".$value['IDPhongDeXuat']."</idP>";
                                echo "<kieu>"."deXuat"."</kieu>";
                                echo "<khuVuc>".$value['KhuVuc']."</khuVuc>";
                                echo "<dienTich>".$value['DienTich']."</dienTich>";
                                echo "<gia>".$value["GiaPhong"]."</gia>";
                            }
                            echo "</".$key.">";
                        }
                    }
                }
                echo "</result>";
            }
            else{
                header('Content-type: text/xml');
                echo "<result>"."false"."</result>";
            }
        }
        
        if($kieuPhong=="tatCa"){
            if($n3==0&&$n4==0){
                header('Content-type: text/xml');
                echo "<result>"."false"."</result>";
            }
            else{
                if($n3!=0){
                    $result3=$data1->fetchAll("phong");
                    
                    header('Content-type: text/xml');
                    echo "<result>";
                    foreach($result3 as $index => $room) {
                        if(is_array($room)) {
                            foreach($room as $key => $value) {
                                echo "<".$key.">";
                                if(is_array($value)) {
                                	echo "<idP>".$value['IDPhongTro']."</idP>";
                                    echo "<kieu>"."dangKy"."</kieu>"; 
                                    echo "<khuVuc>".$value['KhuVuc']."</khuVuc>";
                                    echo "<dienTich>".$value['DienTich']."</dienTich>";
                                    echo "<gia>".$value["GiaPhong"]."</gia>";
                                }
                                echo "</".$key.">";
                            }
                        }
                    }
                    
                    if($n4!=0){
                        $result4=$data2->fetchAll("phong");
                        
                        foreach($result4 as $index => $room) {
                            if(is_array($room)) {
                                foreach($room as $key => $value) {
                                    echo "<".$key.">";
                                    if(is_array($value)) {
                                    	echo "<idP>".$value['IDPhongDeXuat']."</idP>";
                                        echo "<kieu>"."deXuat"."</kieu>";
                                        echo "<khuVuc>".$value['KhuVuc']."</khuVuc>";
                                        echo "<dienTich>".$value['DienTich']."</dienTich>";
                                        echo "<gia>".$value["GiaPhong"]."</gia>";
                                    }
                                    echo "</".$key.">";
                                }
                            }
                        }
                    }
                    echo "</result>";
                }
                else{
                    $result4=$data2->fetchAll("phong");
                    
                    header('Content-type: text/xml');
                    echo "<result>";
                    foreach($result4 as $index => $room) {
                        if(is_array($room)) {
                            foreach($room as $key => $value) {
                                echo "<".$key.">";
                                if(is_array($value)) {
                                	echo "<idP>".$value['IDPhongDeXuat']."</idP>";
                                    echo "<kieu>"."deXuat"."</kieu>";
                                    echo "<khuVuc>".$value['KhuVuc']."</khuVuc>";
                                    echo "<dienTich>".$value['DienTich']."</dienTich>";
                                    echo "<gia>".$value["GiaPhong"]."</gia>";
                                }
                                echo "</".$key.">";
                            }
                        }
                    }
                    echo "</result>";
                }
            }
        }
        
    }
?>
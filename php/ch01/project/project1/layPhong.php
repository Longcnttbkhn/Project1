<?php
    require 'libraries/database.php';
    
    $data=new database("timphongtro");
    $sql1="select * from phongtro where TinhTrang In (0,2) ORDER By DiemDanhGiaTB DESC";
    $data->query($sql1);
    $n1=$data-> num_rows();
    
    if($n1!=0){
        $result1=$data->fetchn('phong',8);
        $sql2="select * from phongdexuat";
        $data->query($sql2);
        $n2=$data->num_rows();

        header('Content-type: text/xml');
        echo "<result>";
        foreach($result1 as $index => $room) {
            if(is_array($room)) {
                foreach($room as $key => $value) {
                    echo "<".$key.">";
                    if(is_array($value)) {
                        echo "<idP>".$value['IDPhongTro']."</idP>";
                        echo "<kieu>"."dangKy"."</kieu>";
                        echo "<khuVuc>".$value['KhuVuc']."</khuVuc>";
                        echo "<dienTich>".$value['DienTich']."</dienTich>";
                        echo "<gia>".$value['GiaPhong']."</gia>";
                    }
                    echo "</".$key.">";
                }
            }
        }

        if($n2!=0){
            $result2=$data->fetchn('phong', 2);
            foreach($result2 as $index => $room) {
                if(is_array($room)) {
                    foreach($room as $key => $value) {
                        echo "<".$key.">";
                        if(is_array($value)) {
                            echo "<idP>".$value['IDPhongDeXuat']."</idP>";
                            echo "<kieu>"."deXuat"."</kieu>";
                            echo "<khuVuc>".$value['KhuVuc']."</khuVuc>";
                            echo "<dienTich>".$value['DienTich']."</dienTich>";
                            echo "<gia>".$value['GiaPhong']."</gia>";
                        }
                        echo "</".$key.">";
                    }
                }
            }
        }
        echo "</result>";
    }
    else{
        $sql2="select * from phongdexuat";
        $data->query($sql2);
        $n2=$data->num_rows();
        
        header('Content-type: text/xml');
        echo "<result>";
        if($n2!=0){
            $result2=$data->fetchn('phong', 10);
            foreach($result2 as $index => $room) {
                if(is_array($room)) {
                    foreach($room as $key => $value) {
                        echo "<".$key.">";
                        if(is_array($value)) {
                            echo "<idP>".$value['IDPhongDeXuat']."</idP>";
                            echo "<kieu>"."deXuat"."</kieu>";
                            echo "<khuVuc>".$value['KhuVuc']."</khuVuc>";
                            echo "<dienTich>".$value['DienTich']."</dienTich>";
                            echo "<gia>".$value['GiaPhong']."</gia>";
                        }
                        echo "</".$key.">";
                    }
                }
            }
        }
        else{
            echo "false";
        }
        echo "</result>";
    }
?>
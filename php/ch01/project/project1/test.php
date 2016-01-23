<?php
require 'libraries/time.php';
$time="2014-10-1";
$currentDate = "2015-11-22";
// $array1=explode("-",$time);
// $array2=explode("-", $currentDate);
// $tg=($array2[0]-$array1[0])*365+($array2[1]-$array1[1])*30+($array2[2]-$array1[2]);
// echo $tg;

$time1=new time($time, $currentDate);
echo $time1->day();

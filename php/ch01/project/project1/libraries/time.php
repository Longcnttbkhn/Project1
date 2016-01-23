<?php
class time{
    protected $_time1;
    protected $_time2;
    
    public function __construct($a,$b){
        $this->_time1=$a;
        $this->_time2=$b;
    }
    
    public function day(){
        $time1=strtotime($this->_time1);
        $time2=strtotime($this->_time2);
        $tg=(int)(($time2-$time1)/(60*60*24));
        return $tg;
    }
}
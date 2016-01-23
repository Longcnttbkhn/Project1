<?php
require ("libraries/config.php");
    
class database{
    protected $_conn= "";
    protected $_result="";
    
    //ket noi csdl
    public function __construct($a){
        $this->_conn=mysql_connect(config::HOST,config::USER,config::PASS) or die("khong the ket noi csdl!");
        mysql_select_db($a,$this->_conn);
        mysql_query("SET NAMES utf8");
    }
    
    //neu da ket noi csdl thi tra ket qua cau truy van cho bien result 
    public function query($sql){
        if($this->_conn){
            $this->_result=mysql_query($sql);
        }
    }
    
    //dem so ket qua tra ve
    public function num_rows(){
        if($this->_result){
            $rows=mysql_num_rows($this->_result);
        }else{
            $rows=0;
        }
        return $rows;
    }
    
    public function fetch(){
        if($this->_result){
            $data= mysql_fetch_assoc($this->_result);
        }else{
            $data=array();
        }
        return $data;
    }
    
    public function fetchAll($a){
        if($this->_result){
            while($db=mysql_fetch_assoc($this->_result)){ //lay tung ket qua
                $data[]=array($a=>$db); // gan vao mang data
            }
        }else{
            $data=array();
        }
        return $data;
    }
}
?>    
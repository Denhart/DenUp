<?php
include 'key.php'; //Includes secretket, host and path
$secret = $_GET["secret"];
if($secret == $secretkey){
  $data = file_get_contents('php://input');
  if ($data) {
    $filename = date('Y_m_d-H-i-s');
    $dataFile = "$path" . "$filename" . ".$_GET[type]";
    $fh = fopen($dataFile, 'w') or die();
    $data = base64_decode($data);
    fwrite($fh, $data);
    fclose($fh);
    echo "$host" . "$filename".".$_GET[type]";
    return;
  }
}else{
  //Hehe a little security by obscurity!! That'll teach em! :P
echo "404 - Not found.";
}
?>
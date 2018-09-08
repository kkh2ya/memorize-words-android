<?php

$server_name = "localhost";
$mysql_user = "root";
$mysql_pass = "root";
$db_name = "memorize_english";

$con = mysqli_connect($server_name, $mysql_user, $mysql_pass, $db_name);

$result = mysqli_query($con, "select * from userlist;");

$response = array();

while($row = mysqli_fetch_array($result))
{
	array_push($response, array("words"=>$row[0],
	"answer"=>$row[1], "sentence"=>$row[2], "sentence_answer"=>$row[3]));
}

echo json_encode(array("response"=>$response));
mysqli_close($con);

?>

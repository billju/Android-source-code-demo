<?php
$user_email = $_POST["email"];
$user_password = $_POST["password"];
$con = mysqli_connect("localhost","root","8763","mystore");
$sql = "INSERT INTO user_info VALUES('".$user_email."','".$user_password."');";
if(empty($user_email)||empty($user_password)){
	echo "Input is empty";
}else{
	if(mysqli_query($con,$sql)){
		echo "Data insertion succes...";
	}else{
		echo "Error while insertion.";
	}
}
mysqli_close($con);
?>
<?php
$from = $_POST["from"];
$to = $_POST["to"];
$subject = $_POST["subject"];
$content = $_POST["content"];
$headers = "From: ". $from;
mail($to,$subject,$content,$headers);
echo "<h1>Email was sent out! <br><br>";
echo "Please don't misuse the site! You will be caught!<br></h1>";
?>

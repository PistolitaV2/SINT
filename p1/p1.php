<html>
        
<head>
    <meta charset="UTF-8"/>
    <title>p1.php</title>
    <link rel="stylesheet" href="p1.css">
</head>

<body class="subgrupo" >
	
<?php
    $zonahoraria = date_default_timezone_get();
    echo 'Zona horaria predeterminada: ' . $zonahoraria;
    echo "<br>"
    print "Nome e apellido: " . $_REQUEST["cname"] . "<br>" . PHP_EOL;
    print "Usuario: " . $_REQUEST["clogin"] . "<br>" . PHP_EOL;
    print "Contrasinal: " . $_REQUEST["cpasswd"] . "<br>" . PHP_EOL;
    print "Telefono: " . $_REQUEST["ctlph"] . "<br>" . PHP_EOL;
    print "Correo electrónico: " . $_REQUEST["cemail"] . "<br>" . PHP_EOL;
    print "Idade: " . $_REQUEST["cage"] . "<br>" . PHP_EOL;
    print "Xéneros de interes: ";
    if(!empty($_REQUEST['cgenre'])){
        foreach($_REQUEST['cgenre'] as $selected){
        echo $selected."<br>";
         }
    }
    echo "<br>";
    print "Feeback: " . $_REQUEST["ccomment"] . "<br>" . PHP_EOL;
    print "Compañía proveedora de internet: " . $_REQUEST["cInternet"] . "<br>" . PHP_EOL;
    print "Navegador: " . $_REQUEST["cbrowser"] . "<br>" . PHP_EOL;
    ?> 
    
    <?php phpinfo() ?>
   
</body>
</html>
function deselectall(){
    for (i=0;i<document.f1.elements.length;i++)
       if(document.f1.elements[i].type == "checkbox")
          document.f1.elements[i].checked=0
 } 
 function selectall(){
    for (i=0;i<document.f1.elements.length;i++)
       if(document.f1.elements[i].type == "checkbox")
          document.f1.elements[i].checked=1
 } 

 function checkAll(){
    var obx = document.getElementsByName("cgenre");
    for(var i = 0; i < len.length; i ++){
        obx[i].checked =1;	
    }
}	
function checkAll(checkbox) 
{
	tocheck=document.getElementsByTagName('input'); //Rescatamos controles tipo Input
	for(i=0;i<tocheck.length;i++) //Ejecutamos y recorremos los controles
	{
		if(tocheck[i].type == "checkbox" && tocheck[i].id!="select") // Ejecutamos si es una casilla de verificacion
		{
			tocheck[i].checked=checkbox.checked; // Si el input es CheckBox se aplica la funcion ActivarCasilla
		}
	}
}

/*function uncheckAll(){
    var obx = document.getElementsByName("cgenre");
    for(var i = 0; i < len.length; i ++){
        obx[i].checked =0;	
    }
} */



 function filler_nav() {
    var Nav = document.miform.cbrowser;
    Nav.value = navigator.userAgent;
  return;
}
function checknome() {
   var cname = document.forms.miform.cname.value;
   if (cname.length == 0) return true;
   if (cname.length < 3)  {
     alert("O nome ten menos de 3 caracteres");
     return false;
   } 
   else;
   var valoresAceptados = /^[0-9]+$/;
       if (cname.match(valoresAceptados)){
          return false;
       } 
   return true;
 }
 function checktlph(){
  var telefono = document.forms.miform.ctlph.value;
  if(telefono.match(/^[6-9]\d{8}$/) == null){
      alert("O teléfono non é correcto");
      return false;
  }else {
      return true;
  }
}



 function checkpasswd(){
  var password =  document.forms.miform.cpasswd.value;
     if (password.length == 0){
         return true;
     }else {
          if (password.length < 6 || password.length > 12){
              alert("A contraseña ten que ter entre 6 e 12 caracteres")
              return false
          }else {
              if(password.search(/[A-Z]/) == -1){
                  alert("É necesario que teña unha maiúscula");
                  return false;
              }else {
                  if(password.search(/[a-z]/) == -1){
                      alert("É necesario que teña unha minúscula");
                      return false;
                  }else {
                          if (password.includes("0") || password.includes("1") || password.includes("2") || password.includes("3") || password.includes("4") || password.includes("5") || password.includes("6") || password.includes("7") || password.includes("8") || password.includes("9")){
                              if (password.includes("+") || password.includes("-") || password.includes("*") || password.includes("/")){
                                  return true;
                              }else {
                                  alert("É necesario que teña un elemento tipo [+-*/”]");
                                  return false;
                              }
                          }else {
                              alert("É necesario que teña un número");
                              return false;
                          }
                  }
              }       
          }
      }
  }


 function get(){
  if(document.forms.miform.enctype == "multipart/form-data"){
      alert("multipart/form-data non funciona con GET");
      return false;
  }
  document.forms.miform.method="post";
  return true;
}

function post(){
  document.forms.miform.method="POST";
  return true;
}
function PostMultipart () {
  if(document.forms.miform.method == "post"){
      document.forms.miform.enctype = "multipart/form-data";
      return true;
  } 
  else{
      alert("multipart/form-data debe ir con POST");
      return false;
  }
}

function Urlencoded() {
  document.forms.miform.enctype = "application/x-www-form-urlencoded";
  return true;
}

/* function checkpassword() {
   var cpasswd = document.forms.miform.cpasswd.value;
   if (cpasswd.length == 0) return true;
   if (cpasswd.length < 6) {
     alert("La contraseña tiene menos de 8 caracteres");
     return false;
   }
   if (cpasswd.length > 12) {
      alert("La contraseña tiene menos de 8 caracteres");
      return false;
    }
   else return true;
 } */
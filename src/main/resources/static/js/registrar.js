// Call the dataTables jQuery plugin
$(document).ready(function() {
    //on ready
});

async function registrarUsuario(){
  let datos = {};
  datos.nombre = document.getElementById('txtNombre').value;
  datos.apellido = document.getElementById('txtApellido').value;
  datos.correo = document.getElementById('txtEmail').value;
  datos.password = document.getElementById('txtpassword').value;

  let repetirpass = document.getElementById('txtrepetirpassword').value;

  if(repetirpass != datos.password){

    alert("Contraseña no coincide")
    return;

  }
  const request = await fetch('api/maestro', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(datos)
  });

  alert('La cuenta se ha registrado con exito');
  window.location.href = 'login.html'


}

// Call the dataTables jQuery plugin
$(document).ready(function() {
    //on ready
});

async function iniciarSesion(){
  let datos = {};
  datos.correo = document.getElementById('txtemail').value;
  datos.password = document.getElementById('txtpassword').value;

  const request = await fetch('api/login', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(datos)
  });
  const response = await request.text();
  if (response != 'FAIL'){
    localStorage.token = response;
    localStorage.correo = datos.correo;
    window.location.href='estudiantes.html'
  } else {
    alert('Las credenciales son incorrectas')
  }


}

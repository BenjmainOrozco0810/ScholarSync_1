// Call the dataTables jQuery plugin
$(document).ready(function() {
    cargarUsuarios();
    $('#usuarios').DataTable();
    actualizarEmailUsuario();
});

function actualizarEmailUsuario(){
    document.getElementById('txt-email-usuario').textContent = localStorage.correo;
}

async function cargarUsuarios() {
    const request = await fetch('api/estudiantes', {
        method: 'GET',
        headers: getHeaders()
    });
    const usuarios = await request.json();

    let listadoHtml = '';

    for (let usuario of usuarios) {
        let botoneliminar = '<a href="#" onclick="eliminarUsuario('+usuario.id+')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';
        let botonactualizar = '<a href="actualizar.html?id='+usuario.id+'" class="btn btn-info btn-circle btn-sm"><i class="fas fa-info-circle"></i></a>';

        let usuarioHtml = '<tr><td>'+usuario.id+'</td><td>'+usuario.nombre+'</td><td>'+usuario.correo+'</td><td>'+usuario.telefono+'</td><td>'+usuario.idioma+'</td><td>'+botoneliminar+' '+botonactualizar+'</td></tr>';
        listadoHtml += usuarioHtml;
    }

    document.querySelector('#usuarios tbody').outerHTML = listadoHtml;
}

function getHeaders() {
    return {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': localStorage.token
    };
}

async function eliminarUsuario(id) {
    if(!confirm('¿Desea eliminar este usuario?')){
        return;
    }
    const request = await fetch('api/estudiante/'+id, {
        method: 'DELETE',
        headers: getHeaders()
    });
    location.reload();
}
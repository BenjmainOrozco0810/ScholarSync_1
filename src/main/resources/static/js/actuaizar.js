document.addEventListener('DOMContentLoaded', function() {
    cargarDatosEstudiante();
    configurarFormularios();
    actualizarEmailUsuario();
});
function actualizarEmailUsuario(){
    document.getElementById('txt-email-usuario').textContent = localStorage.correo;
}

async function cargarDatosEstudiante() {
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');

    if (!id) {
        alert('No se especificó un estudiante');
        window.location.href = 'usuarios.html';
        return;
    }

    try {
        const response = await fetch(`api/estudiantes/${id}`, {
            method: 'GET',
            headers: getHeaders()
        });

        if (!response.ok) {
            throw new Error(`Error HTTP: ${response.status}`);
        }

        const estudiante = await response.json();

        // Mostrar datos en la tarjeta
        document.getElementById('txtnombre').textContent = estudiante.nombre;
        document.getElementById('txtemail').textContent = estudiante.correo;
        document.getElementById('txttelefono').textContent = estudiante.telefono;
        document.getElementById('txtidioma').textContent = estudiante.idioma;

        // Rellenar formularios de edición
        document.getElementById('nombre').value = estudiante.nombre;
        document.getElementById('correo').value = estudiante.correo;
        document.getElementById('telefono').value = estudiante.telefono;
        document.getElementById('idioma').value = estudiante.idioma.toLowerCase();

        // Rellenar formulario de contacto
        document.getElementById('correoContacto').value = estudiante.correo;
        document.getElementById('telefonoContacto').value = estudiante.telefono;

    } catch (error) {
        console.error('Error:', error);
        alert('Error al cargar los datos del estudiante: ' + error.message);
        window.location.href = 'estudiantes.html';
    }
}

function configurarFormularios() {
    // Validación en tiempo real para teléfono
    $('#telefono, #telefonoContacto').on('input', function() {
        this.value = this.value.replace(/\D/g, '');
    });

    // Formulario principal
    $('#studentForm').submit(async function(e) {
        e.preventDefault();

        const id = new URLSearchParams(window.location.search).get('id');
        const formData = {
            nombre: $('#nombre').val(),
            correo: $('#correo').val(),
            telefono: $('#telefono').val(),
            idioma: $('#idioma').val()
        };

        try {
            const response = await fetch(`api/estudiantes/${id}`, {
                method: 'PUT',
                headers: getHeaders(),
                body: JSON.stringify(formData)
            });

            if (!response.ok) {
                throw new Error(await response.text());
            }

            alert('Estudiante actualizado con éxito');
            location.reload();
        } catch (error) {
            alert('Error al actualizar: ' + error.message);
        }
    });

    // Formulario de contacto
    $('#contactForm').submit(async function(e) {
        e.preventDefault();

        const id = new URLSearchParams(window.location.search).get('id');
        const formData = {
            correo: $('#correoContacto').val(),
            telefono: $('#telefonoContacto').val()
        };

        try {
            const response = await fetch(`api/estudiantes/${id}/contacto`, {
                method: 'PATCH',
                headers: getHeaders(),
                body: JSON.stringify(formData)
            });

            if (!response.ok) {
                throw new Error(await response.text());
            }

            alert('Contacto actualizado con éxito');
            location.reload();
        } catch (error) {
            alert('Error al actualizar contacto: ' + error.message);
        }
    });
}

function getHeaders() {
    return {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': localStorage.token
    };
}
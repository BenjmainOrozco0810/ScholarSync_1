// Call the dataTables jQuery plugin
$(document).ready(function() {
    //on ready
});

async function registrarEstudiante() {
    // Obtener valores del formulario
    let datos = {};
    let nombre = document.getElementById('txtnombre').value.trim();
    let apellido = document.getElementById('txtapellido').value.trim();
    datos.nombre = nombre + ' ' + apellido;
    datos.correo = document.getElementById('txtemail').value.trim();
    datos.telefono = document.getElementById('txttelefono').value.trim();
    datos.idioma = document.getElementById('txtidioma').value.trim();

    // 1. Validar nombre y apellido (no vacíos y menos de 255 caracteres)
    if (nombre === '' || apellido === '') {
        alert('El nombre y apellido son obligatorios');
        return;
    }
    if (datos.nombre.length > 255) {
        alert('El nombre completo no debe exceder 255 caracteres');
        return;
    }

    // 2. Validar correo (formato válido)
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(datos.correo)) {
        alert('Por favor ingrese un correo electrónico válido');
        return;
    }

    // 3. Validar teléfono (exactamente 10 dígitos)
    const telefonoRegex = /^\d{10}$/;
    if (!telefonoRegex.test(datos.telefono)) {
        alert('El teléfono debe tener exactamente 10 dígitos numéricos');
        return;
    }

    // 4. Validar idioma (solo los permitidos)
    const idiomasPermitidos = ['ingles', 'español', 'frances'];
    if (!idiomasPermitidos.includes(datos.idioma.toLowerCase())) {
        alert('Idioma no válido. Seleccione entre: inglés, español o francés');
        return;
    }

    try {
        // Enviar datos al servidor
        const request = await fetch('api/estudiante', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(datos)
        });

        // Verificar si la respuesta del servidor es exitosa
        if (!request.ok) {
            const errorData = await request.json();
            throw new Error(errorData.message || 'Error al registrar estudiante');
        }

        // Mostrar mensaje de éxito y redirigir
        alert('Estudiante agregado con éxito');
        window.location.href = 'estudiantes.html';

    } catch (error) {
        // Capturar errores de red o del servidor
        alert(`Error: ${error.message}`);
        console.error('Error:', error);
    }
}
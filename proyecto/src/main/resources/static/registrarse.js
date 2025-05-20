
const BASE_URL = window.location.hostname === 'localhost'
	? "http://localhost:8080/api/royale"
	: "https://royale-1wd7.onrender.com/api/royale";

document.getElementById("registroForm").addEventListener("submit", async function(event) {
	event.preventDefault();

	const errorMsg = document.getElementById("registerError");


	let nombre = document.getElementById("nombre").value;
	let apellido = document.getElementById("apellido").value;
	let dni = document.getElementById("dni").value;
	let telefono = document.getElementById("telefono").value;
	let email = document.getElementById("email").value;
	let password1 = document.getElementById("password1").value;
	let password2 = document.getElementById("password2").value;


	if(password1 != password2){
		errorMsg.textContent = "Las contraseñas no coinciden";
		errorMsg.style.display = "block";
		return; // Para que no siga si las contraseñas no coinciden
	}

	try {
		const response = await fetch(BASE_URL, {
			method: "POST",
			headers: { "Content-Type": "application/json" },
			credentials: "include", // para enviar cookies si las hay
			body: JSON.stringify({
				nombre: nombre,
				apellido: apellido,
				dni: dni,
				email: email,
				telefono: telefono,
				password: password1
			})
		});

		console.log(response.ok);

		if (!response.ok) {
			if(response.status == 409){ // Conflict
				errorMsg.textContent = "Este mail ya tiene un cliente asociado. Intente otro mail";
				errorMsg.style.display = "block";
			} else {
				errorMsg.textContent = "Error al registrarse";
				errorMsg.style.display = "block";
			}
			return;
		}

		window.location.href = "index.html";
	} catch (error) {
		console.error("Error al registrarse:", error);
		errorMsg.textContent = "Error inesperado. Inténtalo de nuevo más tarde.";
		errorMsg.style.display = "block";
	}
});




document.getElementById("loginForm").addEventListener("submit", async function(event) {
	event.preventDefault();
	
	const errorMsg = document.getElementById("loginError");
	
	let email = document.getElementById("mail").value;
	let contrasenya = document.getElementById("password").value;
	
	 try {
      const response = await fetch("http://localhost:8080/api/royale/users", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ 
			email: email, 
			password: contrasenya })
		
      });
		console.log(response.ok);
      if (!response.ok) {
        
		if(response.status == 401){ //Unauthorized
			errorMsg.textContent = "Email o contraseña incorrectos";
			errorMsg.style.display = "block";
		}
		
        return;
      }

      window.location.href = "cuentas.html";
    } catch (error) {
      console.error("Error al iniciar sesión:", error);
	  errorMsg.textContent = "Error inesperado. Inténtalo de nuevo más tarde.";
	  errorMsg.style.display = "block";
	  
    }
  });

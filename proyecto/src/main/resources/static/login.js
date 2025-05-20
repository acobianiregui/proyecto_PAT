document.getElementById("loginForm").addEventListener("submit", async function(event) {
  event.preventDefault();
  
  const errorMsg = document.getElementById("loginError");
  
  const email = document.getElementById("mail").value;
  const contrasenya = document.getElementById("password").value;
  
  try {
    const response = await fetch("http://localhost:8080/api/royale/users", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      credentials: "include", // 👈 Esto permite recibir y guardar la cookie
      body: JSON.stringify({
        email: email,
        password: contrasenya
      })
    });

    if (!response.ok) {
      if (response.status === 401) {
        errorMsg.textContent = "Email o contraseña incorrectos";
        errorMsg.style.display = "block";
      } else {
        errorMsg.textContent = "Error de autenticación";
        errorMsg.style.display = "block";
      }
      return;
    }

    // Ya no hace falta usar localStorage, la cookie se guarda automáticamente
    // Si aún quieres guardar un valor extra del body, puedes hacer esto:
    // const result = await response.json();
    // localStorage.setItem("userId", result.id);

    window.location.href = "cuentas.html";

  } catch (error) {
    console.error("Error al iniciar sesión:", error);
    errorMsg.textContent = "Error inesperado. Inténtalo de nuevo más tarde.";
    errorMsg.style.display = "block";
  }
});

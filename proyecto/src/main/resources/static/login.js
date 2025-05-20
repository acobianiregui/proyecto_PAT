const BASE_URL = window.location.hostname === 'localhost'
    ? "http://localhost:8080/api/royale/users"
    : "https://royale-1wd7.onrender.com/api/royale/users";

document.getElementById("loginForm").addEventListener("submit", async function(event) {
  event.preventDefault();

  const errorMsg = document.getElementById("loginError");

  const email = document.getElementById("mail").value;
  const contrasenya = document.getElementById("password").value;

  try {
    const response = await fetch(BASE_URL, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      credentials: "include",
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

    window.location.href = "cuentas.html";

  } catch (error) {
    console.error("Error al iniciar sesión:", error);
    errorMsg.textContent = "Error inesperado. Inténtalo de nuevo más tarde.";
    errorMsg.style.display = "block";
  }
});



const BASE_URL = window.location.origin.includes("localhost")
    ? "http://localhost:8080/api/royale"
    : "http://royale-1wd7.onrender.com/api/royale";

document.addEventListener("DOMContentLoaded", async () => {
  const saludo = document.getElementById("saludo");
  const tabla = document.getElementById("tablaCuentas").querySelector("tbody");

  try {
    const response = await fetch(BASE_URL, {
      method: "GET",
      credentials: "include" // 👈 Envía la cookie con la sesión
    });

    if (!response.ok) {
      //alert("Sesión inválida o expirada.");
      window.location.href = "index.html";
      return;
    }

    const cliente = await response.json();

    // Mostrar saludo
    saludo.textContent = `Bienvenido, ${cliente.nombre}`;

    // Mostrar cuentas en la tabla
    cliente.cuentas.forEach(cuenta => {
      const fila = document.createElement("tr");
      fila.innerHTML = `
        <td>${cuenta.iban}</td>
        <td>${cuenta.saldo.toFixed(2)} €</td>
        <td>${cuenta.sucursal}</td>
      `;
      tabla.appendChild(fila);
    });

  } catch (error) {
    console.error("Error al cargar datos del cliente:", error);
    alert("Error inesperado.");
  }
});

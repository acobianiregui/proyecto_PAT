const BASE_URL = window.location.origin.includes("localhost")
  ? "http://localhost:8080/api/royale"
  : "https://royale-1wd7.onrender.com/api/royale";

document.addEventListener("DOMContentLoaded", async () => {
  const saludo = document.getElementById("saludo");
  const tabla = document.getElementById("tablaCuentas").querySelector("tbody");

  try {
    const response = await fetch(BASE_URL, {
      method: "GET",
      credentials: "include" // 👈 Envía la cookie con la sesión
    });

    if (!response.ok) {
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
        <td><button class="btnAcceder" data-iban="${cuenta.iban}" data-saldo="${cuenta.saldo.toFixed(2)}">Acceder</button></td>
      `;
      tabla.appendChild(fila);
    });

    tabla.addEventListener("click", e => {
      if (e.target.classList.contains("btnAcceder")) {
        const iban = e.target.dataset.iban;
        const saldo = e.target.dataset.saldo;
        window.location.href = `cuentaInterfaz.html?iban=${encodeURIComponent(iban)}&saldo=${encodeURIComponent(saldo)}`;
      }
    });

  } catch (error) {
    console.error("Error al cargar datos del cliente:", error);
    alert("Error inesperado.");
  }
});

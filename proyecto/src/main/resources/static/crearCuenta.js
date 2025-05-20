const BASE_URL = window.location.origin.includes("localhost")
  ? "http://localhost:8080/api/royale/cuentas"
  : "https://royale-1wd7.onrender.com/api/royale/cuentas";

document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("formCrearCuenta");
  const mensaje = document.getElementById("mensaje");

  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    // Limpiar mensaje previo
    mensaje.textContent = "";
    mensaje.style.color = "red";

    const sucursal = document.getElementById("sucursal").value;
    const numeroCuenta = document.getElementById("numeroCuenta").value;

    try {
      const response = await fetch(`${BASE_URL}/cuentas`, {
        method: "POST",
        credentials: "include",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
          sucursal: sucursal,
          numeroCuenta: numeroCuenta
        })
      });

      const data = await response.json();

      if (!response.ok) {
        mensaje.textContent = data.mensaje || "No se pudo crear la cuenta.";
        return;
      }

      mensaje.style.color = "green";
      mensaje.textContent = "Cuenta creada correctamente.";
      form.reset();

    } catch (error) {
      console.error("Error al crear cuenta:", error);
      mensaje.textContent = "Error inesperado al crear la cuenta.";
    }
  });
});

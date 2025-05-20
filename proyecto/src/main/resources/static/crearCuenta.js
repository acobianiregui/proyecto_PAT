const BASE_URL = window.location.origin.includes("localhost")
  ? "http://localhost:8080/api/royale"
  : "https://royale-1wd7.onrender.com/api/royale";

document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("formCrearCuenta");
  const mensaje = document.getElementById("mensaje");

  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    //limpiar
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


      if (!response.ok) {
        mensaje.textContent = data.mensaje || "Ya existe ese número de cuenta";
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

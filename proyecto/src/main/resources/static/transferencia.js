const BASE_URL = window.location.origin.includes("localhost")
  ? "http://localhost:8080/api/royale"
  : "https://royale-1wd7.onrender.com/api/royale";

document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("formTransferencia");

  // Crear un elemento para mostrar mensajes al usuario
  const mensaje = document.createElement("p");
  mensaje.id = "mensaje";
  mensaje.style.color = "red";
  form.parentNode.insertBefore(mensaje, form.nextSibling);

  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    mensaje.textContent = "";
    mensaje.style.color = "red";

    const params = new URLSearchParams(window.location.search);
    const iban = params.get("iban");

    if (iban) {
        const inputIbanOrigen = document.getElementById("ibanOrigen");
        inputIbanOrigen.value = iban;
        inputIbanOrigen.textContent = iban;
      }

    const ibanDestino = document.getElementById("ibanDestino").value.trim();
    const importe = parseFloat(document.getElementById("importe").value);


    try {
      const response = await fetch(`${BASE_URL}/transferencia`, {
        method: "POST",
        credentials: "include", // Para enviar cookies de sesión
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          iban_cuenta_origen: iban,
          iban_cuenta_destino: ibanDestino,
          importe: importe,
        }),
      });


      if (!response.ok) {
        mensaje.textContent = data.mensaje || "Error al realizar la transferencia.";
        return;
      }

      mensaje.style.color = "green";
      mensaje.textContent = "Transferencia realizada correctamente.";
      form.reset();

    } catch (error) {
      console.error("Error al enviar transferencia:", error);
      mensaje.textContent = "Error inesperado al realizar la transferencia.";
    }
  });
});
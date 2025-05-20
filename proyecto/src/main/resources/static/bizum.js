const BASE_URL = window.location.origin.includes("localhost")
  ? "http://localhost:8080/api/royale"
  : "https://royale-1wd7.onrender.com/api/royale/bizum";

document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("bizumForm");
  const mensaje = document.getElementById("mensaje");

  const params = new URLSearchParams(window.location.search);
  const iban = params.get("iban");

  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    // Limpiar mensaje anterior
    mensaje.textContent = "";
    mensaje.style.color = "red";

    const destinatario = document.getElementById("destinatario").value;
    const cantidad = parseFloat(document.getElementById("cantidad").value);

    try {
      const response = await fetch(`${BASE_URL}/bizum`, {
        method: "POST",
        credentials: "include",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          telefono_destino: destinatario,
          importe: cantidad,
          iban_origen: iban
        }),
      });

      const data = await response.json();

      if (!response.ok) {
        mensaje.textContent = data.mensaje || "No se pudo realizar el Bizum.";
        return;
      }

      mensaje.style.color = "green";
      mensaje.textContent = "Bizum enviado correctamente.";
      form.reset();

    } catch (error) {
      console.error("Error al enviar Bizum:", error);
      mensaje.textContent = "Error inesperado al realizar el Bizum.";
    }
  });
});
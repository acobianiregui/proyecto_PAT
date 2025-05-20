document.addEventListener("DOMContentLoaded", () => {
  const titulo = document.getElementById("tituloCuenta");
  const btnBizum = document.getElementById("btnBizum");
  const btnTransacciones = document.getElementById("btnTransacciones");

  // Obtener el IBAN de la URL
  const params = new URLSearchParams(window.location.search);
  const iban = params.get("iban");

  if (!iban) {
    titulo.textContent = "Cuenta no especificada.";
    btnBizum.disabled = true;
    btnTransacciones.disabled = true;
    return;
  }

  // Mostrar el IBAN en el título
  titulo.textContent = "Cuenta: ${iban}";

  // Redirecciones con el IBAN
  btnBizum.addEventListener("click", () => {
    window.location.href = "bizum.html?iban=${encodeURIComponent(iban)}";
  });

  btnTransacciones.addEventListener("click", () => {
    window.location.href = "transferencia.html?iban=${encodeURIComponent(iban)}";
  });
});
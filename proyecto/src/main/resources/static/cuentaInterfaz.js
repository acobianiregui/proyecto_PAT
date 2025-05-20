document.addEventListener("DOMContentLoaded", () => {
  const titulo = document.getElementById("tituloCuenta");
  const saldoElemento = document.getElementById("saldoCuenta");
  const btnBizum = document.getElementById("btnBizum");
  const btnTransacciones = document.getElementById("btnTransferencias");

  // Obtener parámetros de la URL
  const params = new URLSearchParams(window.location.search);
  const iban = params.get("iban");
  const saldo = params.get("saldo");

  if (!iban) {
    titulo.textContent = "Cuenta no especificada.";
    btnBizum.disabled = true;
    btnTransacciones.disabled = true;
    return;
  }

  // Mostrar el IBAN y el saldo en la interfaz
  titulo.textContent = `Cuenta: ${iban}`;
  if (saldoElemento && saldo !== null) {
    saldoElemento.textContent = `Saldo: ${parseFloat(saldo).toFixed(2)} €`;
  }

  // Redirecciones con el IBAN
  btnBizum.addEventListener("click", () => {
    window.location.href = `bizum.html?iban=${encodeURIComponent(iban)}`;
  });

  btnTransacciones.addEventListener("click", () => {
    window.location.href = `transferencia.html?iban=${encodeURIComponent(iban)}`;
  });
});
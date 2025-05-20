document.addEventListener("DOMContentLoaded", () => {
  const titulo = document.getElementById("tituloCuenta");
  const saldoElemento = document.getElementById("saldoCuenta");
  const btnBizum = document.getElementById("btnBizum");
  const btnTransacciones = document.getElementById("btnTransferencias");
  const btnHistorialTransferencias = document.getElementById("btnHistorialTransferencias");

  //Obtener info
  const params = new URLSearchParams(window.location.search);
  const iban = params.get("iban");
  const saldo = params.get("saldo");

  if (!iban) {
    titulo.textContent = "Cuenta no especificada.";
    btnBizum.disabled = true;
    btnTransacciones.disabled = true;
    return;
  }

  
  titulo.textContent = `Cuenta: ${iban}`;
  if (saldoElemento && saldo !== null) {
    saldoElemento.textContent = `Saldo: ${parseFloat(saldo).toFixed(2)} €`;
  }

  //redireccion
  btnBizum.addEventListener("click", () => {
    window.location.href = `bizum.html?iban=${encodeURIComponent(iban)}`;
  });

  btnTransacciones.addEventListener("click", () => {
    window.location.href = `transferencia.html?iban=${encodeURIComponent(iban)}`;
  });
  btnHistorialTransferencias.addEventListener("click", () => {
      window.location.href = `historialTransferencias.html?iban=${encodeURIComponent(iban)}`;
    });
});
const BASE_URL = window.location.origin.includes("localhost")
  ? "http://localhost:8080/api/royale"
  : "https://royale-1wd7.onrender.com/api/royale";

document.addEventListener("DOMContentLoaded", async () => {
  const tabla = document.getElementById("tablaTransferencias").querySelector("tbody");
  const saludo = document.getElementById("saludo");

  //Obtener iban
  const params = new URLSearchParams(window.location.search);
  const iban = params.get("iban");

  if (!iban) {
    saludo.textContent = "IBAN no especificado para mostrar transferencias.";
    return;
  }

  try {
     const response = await fetch(`${BASE_URL}/cuentas/operaciones/${encodeURIComponent(iban)}`, {
      method: "GET",
      credentials: "include",
    });

    if (!response.ok) {
          if (response.status === 404) {
            saludo.textContent = "No se encontraron operaciones para esta cuenta.";
          } else if (response.status === 401) {
            window.location.href = "index.html";
          } else {
            saludo.textContent = "Error al obtener las operaciones.";
          }
          return;
        }

    saludo.textContent = `Historial de Transferencias para cuenta: ${iban}`;

    const operaciones = await response.json();

    if (operaciones.length === 0) {
      const filaVacia = document.createElement("tr");
      filaVacia.innerHTML = `<td colspan="6" style="text-align:center;">No hay transferencias para esta cuenta.</td>`;
      tabla.appendChild(filaVacia);
      return;
    }

    operaciones.forEach(op => {
          const fila = document.createElement("tr");

          fila.innerHTML = `
            <td>${op.tipo}</td>
            <td>${op.cuentaOrigen?.iban || '-'}</td>
            <td>${op.cuentaDestino?.iban || '-'}</td>
            <td>${op.importe.toFixed(2)} €</td>
            <td>${op.concepto || '-'}</td>
          `;

      tabla.appendChild(fila);
    });

  } catch (error) {
    console.error("Error al cargar transferencias:", error);
    alert("Error inesperado al cargar el historial de transferencias.");
  }
});
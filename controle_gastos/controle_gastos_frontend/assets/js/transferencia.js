const remetenteId = new URLSearchParams(window.location.search).get("pessoaId");

if (!remetenteId) {
  alert("Erro: ID do remetente não encontrado na URL!");
}

function carregarDestinatarios() {
  fetch(`http://localhost:8081/pessoa/${remetenteId}/transferencia`)
    .then((response) => {
      if (!response.ok) {
        throw new Error("Erro ao buscar destinatários");
      }
      return response.json();
    })
    .then((data) => {
      const select = document.getElementById("destinatarioId");
      select.innerHTML = '<option value="">Selecione um destinatário</option>';

      const destinatarios = data.content || []; // Se content não existir, retorna array vazio

      if (destinatarios.length === 0) {
        alert("Nenhum destinatário encontrado.");
        return;
      }

      destinatarios.forEach((usuario) => {
        const option = document.createElement("option");
        option.value = usuario.id;
        option.textContent = usuario.nome;
        select.appendChild(option);
      });
    })
    .catch((error) => console.error("Erro ao carregar destinatários:", error));
}

document.getElementById("transferenciaForm").addEventListener("submit", function (event) {
  event.preventDefault();
  const destinatarioId = document.getElementById("destinatarioId").value;
  const valor = document.getElementById("valor").value;

  if (!destinatarioId || !valor) {
    alert("Erro: Preencha todos os campos antes de registrar a transferência.");
    return;
  }

  fetch(`http://localhost:8081/transferencia/${remetenteId}/${destinatarioId}`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ valor }),
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Erro ao registrar transferência");
      }
      return response.json();
    })
    .then(() => {
      alert("Transferência registrada com sucesso!");
      window.location.href = "index.html";
    })
    .catch((error) => alert(error.message));
});

window.onload = carregarDestinatarios;
document
.getElementById("transacaoForm")
.addEventListener("submit", function (event) {
  event.preventDefault();

  const pessoaId = new URLSearchParams(window.location.search).get(
    "pessoaId"
  );
  const valor = parseFloat(document.getElementById("valor").value);
  const descricao = document.getElementById("descricao").value;
  const tipo = document.getElementById("tipo").value;

  fetch(`http://localhost:8081/transacao/${pessoaId}`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ descricao, valor, tipo }),
  })
    .then((response) => {
      if (!response.ok) {
        return response.json().then((err) => {
          throw new Error(err.message);
        });
      }
      return response.json();
    })
    .then(() => {
      alert("Transação registrada com sucesso!");
      window.location.href = "index.html";
    })
    .catch((error) => {
      alert("Erro ao registrar transação: " + error.message);
      console.error("Erro:", error);
    });
});
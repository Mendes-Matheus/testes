document
.getElementById("pessoaForm")
.addEventListener("submit", function (event) {
  event.preventDefault();
  const nome = document.getElementById("nome").value;
  const idade = document.getElementById("idade").value;

  fetch("http://localhost:8081/pessoa", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ nome, idade }),
  })
    .then((response) => response.json())
    .then(() => {
      document.getElementById("pessoaForm").reset();
      carregarPessoas();
    })
    .catch((error) => console.error("Erro ao salvar pessoa:", error));
});

function carregarPessoas() {
fetch("http://localhost:8081/pessoa")
  .then((response) => response.json())
  .then((data) => {
    const lista = document.getElementById("pessoasList");
    lista.innerHTML = "";
    data.content.forEach((pessoa) => {
      const row = document.createElement("tr");
      row.innerHTML = `
                    <td>${pessoa.nome}</td>
                    <td>${pessoa.idade}</td>
                    <td>${pessoa.receitas ?? "-"}</td>
                    <td>${pessoa.mesada ?? "-"}</td>
                    <td>${pessoa.despesas}</td>
                    <td>${pessoa.saldo}</td>
                    <td>
                        <a href="registrar-transacao.html?pessoaId=${
                          pessoa.id
                        }" class="btn btn-custom btn-sm">Registrar Transação</a>
                        <a href="registrar-transferencia.html?pessoaId=${
                          pessoa.id
                        }" class="btn btn-custom btn-sm">Registrar Transferência</a>
                        <button class="btn btn-custom btn-sm" onclick="excluirPessoa(${
                          pessoa.id
                        })">Excluir Pessoa</button>
                    </td>
                `;
      row.addEventListener("click", () =>
        carregarTransacoes(pessoa.id, pessoa.nome)
      );
      lista.appendChild(row);
    });
  })
  .catch((error) => console.error("Erro ao carregar pessoas:", error));
}

function excluirPessoa(pessoaId) {
    if (confirm("Tem certeza que deseja excluir esta pessoa?")) {
    fetch(`http://localhost:8081/pessoa/${pessoaId}`, {
        method: "DELETE",
    })
        .then(() => carregarPessoas())
        .catch((error) => console.error("Erro ao excluir pessoa:", error));
    }
}

carregarPessoas();
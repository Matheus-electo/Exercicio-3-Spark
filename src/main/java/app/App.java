package app;

import static spark.Spark.*;
import dao.ProdutoDAO;
import model.Produto;
import java.util.List;

public class App {
    public static void main(String[] args) {
        // Configurações do Spark
        port(4567);
        staticFiles.location("/public");

        ProdutoDAO dao = new ProdutoDAO();
        dao.conectar();

        // ROTA 1: INSERIR (Recebe dados do formulário)
        post("/produto/inserir", (request, response) -> {
            String nome = request.queryParams("nome_produto");
            double preco = Double.parseDouble(request.queryParams("preco_produto"));

            Produto p = new Produto(0, nome, preco);
            if (dao.inserir(p)) {
                response.redirect("/produto/listar"); // Após inserir, já mostra a lista
                return null;
            } else {
                return "<h1>Erro ao cadastrar!</h1>";
            }
        });

        // ROTA 2: LISTAR (Gera a tabela HTML com o botão Excluir)
        get("/produto/listar", (request, response) -> {
            List<Produto> produtos = dao.listar();
            
            StringBuilder html = new StringBuilder();
            html.append("<html><head><meta charset='UTF-8'></head><body>");
            html.append("<h1>Produtos Cadastrados</h1>");
            html.append("<table border='1' style='width:50%; text-align:left;'>");
            html.append("<tr><th>ID</th><th>Nome</th><th>Preço</th><th>Ação</th></tr>");
            
            for (Produto p : produtos) {
                html.append("<tr>")
                    .append("<td>").append(p.getId()).append("</td>")
                    .append("<td>").append(p.getNome()).append("</td>")
                    .append("<td>R$ ").append(p.getPreco()).append("</td>")
                    .append("<td><a href='/produto/excluir/").append(p.getId()).append("' style='color:red;'>[Excluir]</a></td>")
                    .append("</tr>");
            }
            
            html.append("</table><br>");
            html.append("<a href='/index.html'>+ Cadastrar Novo Produto</a>");
            html.append("</body></html>");
            
            return html.toString();
        });

        // ROTA 3: EXCLUIR (Recebe o ID pela URL)
        get("/produto/excluir/:id", (request, response) -> {
            int id = Integer.parseInt(request.params(":id"));
            if (dao.excluir(id)) {
                response.redirect("/produto/listar"); // Volta para a lista após deletar
                return null;
            } else {
                return "<h1>Erro ao excluir produto!</h1>";
            }
        });

        System.out.println("Servidor pronto! Acesse: http://localhost:4567/index.html");
    }
}
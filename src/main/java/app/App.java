package app;

import static spark.Spark.*;
import dao.ProdutoDAO;
import model.Produto;

public class App {
	public static void main(String[] args) {
	    staticFiles.location("/public"); 
	    
	    // 2. Defina a porta
	    port(4567);

        ProdutoDAO dao = new ProdutoDAO();
        
        // Tenta conectar ao banco logo no início
        if (!dao.conectar()) {
            System.err.println("ERRO: Não foi possível conectar ao banco de dados!");
        }

        // Rota POST que o index.html chama
        post("/produto/inserir", (request, response) -> {
            String nome = request.queryParams("nome_produto");
            String precoStr = request.queryParams("preco_produto");
            
            double preco = Double.parseDouble(precoStr);
            Produto p = new Produto(0, nome, preco);
            
            if (dao.inserir(p)) {
                return "<h1>Sucesso!</h1><p>Produto " + nome + " cadastrado.</p><a href='/index.html'>Voltar</a>";
            } else {
                return "<h1>Erro!</h1><p>Verifique o console do Eclipse.</p>";
            }
        });

        System.out.println("Servidor on! Acesse: http://localhost:4567/index.html");
    }
}
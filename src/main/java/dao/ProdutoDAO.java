package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Produto;

public class ProdutoDAO {
    private Connection conexao;

    // Conecta ao banco 'crud_java' no PostgreSQL
    public boolean conectar() {
        try {
            String url = "jdbc:postgresql://localhost:5432/crud_java";
            String usuario = "postgres";
            String senha = "Mathias7107!"; // <--- Verifique se essa senha está 100% correta!

            conexao = DriverManager.getConnection(url, usuario, senha);
            System.out.println("CONEXÃO COM O BANCO OK!"); // Se aparecer isso, o banco está certo
            return true;
        } catch (SQLException e) {
            // Isso aqui vai imprimir o erro real no seu console do Eclipse
            System.err.println("ERRO DE CONEXÃO: " + e.getMessage());
            return false;
        }
    }

    // CREATE: Insere um novo produto
    public boolean inserir(Produto produto) {
        try {
            String sql = "INSERT INTO produto (nome, preco) VALUES (?, ?)";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, produto.getNome());
            st.setDouble(2, produto.getPreco());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    // READ: Retorna a lista de todos os produtos
    public List<Produto> listar() {
        List<Produto> lista = new ArrayList<>();
        try {
            String sql = "SELECT * FROM produto ORDER BY id ASC";
            PreparedStatement st = conexao.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Produto p = new Produto(rs.getInt("id"), rs.getString("nome"), rs.getDouble("preco"));
                lista.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar: " + e.getMessage());
        }
        return lista;
    }

    // DELETE: Remove um produto pelo ID
    public boolean excluir(int id) {
        try {
            String sql = "DELETE FROM produto WHERE id = ?";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir: " + e.getMessage());
            return false;
        }
    }
}
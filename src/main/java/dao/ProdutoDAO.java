package dao;
import java.sql.*;
import model.Produto;

public class ProdutoDAO {
    private Connection conexao;
    
    public boolean conectar() {
        try {
            String url = "jdbc:postgresql://localhost:5432/crud_java";
            String usuario = "postgres"; 
            String senha = "Mathias7107!"; 

            conexao = DriverManager.getConnection(url, usuario, senha);
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao conectar: " + e.getMessage());
            return false;
        }
    }

    public boolean inserir(Produto produto) {
        try {
            String sql = "INSERT INTO produto (nome, preco) VALUES (?, ?)";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, produto.getNome());
            st.setDouble(2, produto.getPreco());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir: " + e.getMessage());
            return false;
        }
    }
}
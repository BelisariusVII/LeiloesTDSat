/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.List;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public void cadastrarProduto(ProdutosDTO produto) {
        conn = new conectaDAO().connectDB();

        String sql = "INSERT INTO indice (nome, valor, status) VALUES (?, ?, ?)";

        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getValor());
            stmt.setString(3, produto.getStatus());
            stmt.execute();

        } catch (Exception e) {
            System.out.println("Erro ao inserir o produto: " + e.getMessage());
        }

    }

    public ArrayList<ProdutosDTO> listarProdutos() {
        conn = new conectaDAO().connectDB();
        String sql = "SELECT * FROM indice";

        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));

                listagem.add(produto);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar os produtos: " + e.getMessage());
        }

        return listagem;
    }

    public int venderProduto(int id) {
        int status;

        try {
            conn = new conectaDAO().connectDB();

            String produtovendido = "Vendido";

            PreparedStatement st = conn.prepareStatement("UPDATE indice SET status = ? WHERE id = ?");

            st.setInt(2, id);
            st.setString(1, produtovendido);

            status = st.executeUpdate();

            return status;

        } catch (SQLException ex) {

            System.out.println(ex.getErrorCode());

            return ex.getErrorCode();

        }
    }

    public List<ProdutosDTO> listarProdutosVendidos(String statusVenda) {
        conn = new conectaDAO().connectDB();
        String sql = "SELECT * FROM indice WHERE status = ?";

        try {
            PreparedStatement st = conn.prepareStatement(sql);

            st.setString(1, statusVenda);

            ResultSet rs = st.executeQuery();

            List<ProdutosDTO> lista = new ArrayList<>();
            while (rs.next()) {
                ProdutosDTO produto = new ProdutosDTO();

                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setValor(rs.getInt("valor"));
                produto.setStatus(rs.getString("status"));

                lista.add(produto);
            }

            return lista;
        } catch (SQLException ex) {

            System.out.println("Erro ao pesquisar: " + ex.getMessage());
            return null;
        }
    }

}

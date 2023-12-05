package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Contato;

public class ContatoDaoJdbc implements InterfaceDao<Contato> {
    private Connection conn;

    public ContatoDaoJdbc() throws SQLException {
        this.conn = ConnFactory.getConnection();
    }

    @Override
    public void incluir(Contato entidade) throws Exception {
        try {
            
            PreparedStatement ps = conn.prepareStatement("INSERT INTO contato (nome, email, telefone) VALUES(?, ?, ?)");
            ps.setString(1, entidade.getNome());
            ps.setString(2, entidade.getEmail());
            ps.setString(3, entidade.getTelefone());
            ps.execute();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public void editar(Contato entidade) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE contato SET nome=?, email=?, telefone=? WHERE id=?");
            ps.setString(1, entidade.getNome());
            ps.setString(2, entidade.getEmail());
            ps.setString(3, entidade.getTelefone());
            ps.setInt(4, entidade.getId());

            ps.execute();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public void excluir(Contato entidade) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM contato WHERE id=?");
            ps.setInt(1, entidade.getId());
            ps.execute();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public Contato pesquisarPorId(int id) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM contato");
            ResultSet rs = ps.executeQuery();
            ArrayList<Contato> lista = new ArrayList<>();
            while (rs.next()) {
                Contato c1 = new Contato();
                c1.setId(rs.getInt("id"));
                c1.setNome(rs.getString("nome"));
                c1.setEmail(rs.getString("email"));
                c1.setTelefone(rs.getString("telefone"));
                lista.add(c1);
            }

            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getId() == id) {
                    return lista.get(i);
                }
            }

            return null;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public List<Contato> listar(String param) throws Exception {
        try {
            PreparedStatement ps = null;
            ResultSet rs = null;
            if(param.equals("")){
                ps = conn.prepareStatement("SELECT * FROM contato");
            } else {
                ps = conn.prepareStatement("SELECT * FROM contato WHERE nome like '%" + param + "%'");
            }
            rs = ps.executeQuery();
            ArrayList<Contato> lista = new ArrayList<>();
            while (rs.next()) {
                Contato c = new Contato();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setEmail(rs.getString("email"));
                c.setTelefone(rs.getString("telefone"));
                lista.add(c);
            }

            return lista;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
}

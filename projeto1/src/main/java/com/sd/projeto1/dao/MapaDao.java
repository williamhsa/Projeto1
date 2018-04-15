
package com.sd.projeto1.dao;

import com.sd.projeto1.model.Mapa;
import com.sd.projeto1.util.SQLiteConnection;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;


public class MapaDao implements Serializable{

    private static final long serialVersionUID = 1L;

    public Mapa buscarPorId(Integer id) throws Exception{
        Connection con = null;
        PreparedStatement ps = null;

        try {
                con = SQLiteConnection.connect();

                ps = con.prepareStatement("select chave, texto, tipooperacao, data "
                                                        + "from mapa where id = ?");
                ps.setInt(1, id);

                ResultSet rs = ps.executeQuery();

                Mapa mapa = new Mapa();

                while(rs.next()){
                    mapa.setChave(rs.getInt("chave"));
                    mapa.setTexto(rs.getString("texto"));
                    mapa.setTipoOperacaoId(rs.getInt("tipooperacao"));             
                    mapa.setData(rs.getDate("data"));
                }
                rs.close();
                con.close();

                return mapa;

        } catch (Exception e) {
                throw new Exception("Erro ao buscar Mapa. "+e.getMessage());
        }
		
    }
    
}

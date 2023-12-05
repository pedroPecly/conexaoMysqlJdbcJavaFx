package model.dao;

public class DaoFactory {
    public static ContatoDaoJdbc novoContatoDaoJdbc() throws Exception{
        return new ContatoDaoJdbc(); //basicamente funciona como uma fabrica de Dao
    }
}

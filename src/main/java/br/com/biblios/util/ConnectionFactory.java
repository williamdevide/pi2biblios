package br.com.biblios.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    
    public static Connection getConnection() {
        try {
            // Carrega o driver JDBC do MySQL. É uma boa prática fazer isso uma vez.
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // Se o driver não for encontrado no classpath, não há como continuar.
            throw new RuntimeException("Driver MySQL não encontrado!", e);
        }

        // TENTATIVA 1: Conectar na porta padrão 3306
        try {
            // Note que corrigi o nome do banco para "pi2biblios" que usamos no script SQL
            return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/pi2biblios?useUnicode=true&characterEncoding=UTF-8", 
                "root", 
                ""
            );
        } catch (SQLException e) {
            // Se a conexão na porta 3306 FALHAR, o código entra neste bloco 'catch'.
            System.out.println("AVISO: Falha ao conectar na porta 3306. Tentando a porta 3307...");

            // TENTATIVA 2: Conectar na porta alternativa 3307
            try {
                return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3307/pi2biblios?useUnicode=true&characterEncoding=UTF-8", 
                    "root", 
                    ""
                );
            } catch (SQLException ex) {
                // Se a segunda tentativa na porta 3307 também FALHAR, desistimos e lançamos o erro.
                throw new RuntimeException("Erro na conexão com o banco de dados. Nenhuma das portas (3306, 3307) respondeu.", ex);
            }
        }
    }
}
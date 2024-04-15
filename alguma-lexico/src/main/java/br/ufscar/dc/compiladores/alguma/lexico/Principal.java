package br.ufscar.dc.compiladores.alguma.lexico;

import java.io.IOException;
import java.io.PrintWriter;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Token;

public class Principal {

    public static void main(String[] args) {
        // Verifica se os argumentos necessários foram fornecidos
        if (args.length < 2) {
            System.out.println("Forneca o arquivo de entrada e o arquivo de saída.");
            return;
        }
        
        // Escreve os tokens no arquivo de saída
        try (PrintWriter writer = new PrintWriter(args[1])) {
            // Cria um CharStream que lê o arquivo de entrada
            CharStream cs = CharStreams.fromFileName(args[0]);
            
            // Cria o lexer
            LALexer lex = new LALexer(cs);

            Token t = null;
            while ((t = lex.nextToken()).getType() != Token.EOF) { // Itera sobre os tokens
                String nomeToken = LALexer.VOCABULARY.getDisplayName(t.getType());

                // Tratamento de erros específicos
                if (nomeToken.equals("COMENTARIO_NAO_FECHADO")) {
                    String error = "Linha " + t.getLine() + ": " + "comentario nao fechado";
                    writer.println(error);
                    break;
                }
                if (nomeToken.equals("ERR")) {
                    String error = "Linha " + t.getLine() + ": "  + t.getText() + " - simbolo nao identificado";
                    writer.println(error);
                    break;
                }
                if (nomeToken.equals("CADEIA_NAO_FECHADA")) {
                    String error = "Linha " + t.getLine() + ": " + "cadeia literal nao fechada";
                    writer.println(error);
                    break;
                }
                    
                String tokenStr = "<" + "'" + t.getText() + "'" + "," + LALexer.VOCABULARY.getDisplayName(t.getType()) + ">";
                
                // Escreve o token no arquivo de saída
                writer.println(tokenStr);
            }

        } catch (IOException ex) {
            System.err.println("Erro ao abrir os arquivos: " + ex.getMessage());
        }
    }
}

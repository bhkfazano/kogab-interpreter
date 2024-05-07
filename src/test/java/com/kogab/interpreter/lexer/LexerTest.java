package com.kogab.interpreter.lexer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class LexerTest {

    @Test
    public void testEachTokenType() {
        Lexer lexer = new Lexer("(){}.,-+;*/" +
                "! != = ==" +
                "> >= < <=" +
                "identifier \"string\" 123 123.0" +
                "and class else false fun for if nil or print return super this true var while"
        );
        List<Token> tokens = lexer.scanTokens();
        assertEquals(40, tokens.size());

        assertEquals(TokenType.LEFT_PAREN, tokens.get(0).getType());
        assertEquals(TokenType.RIGHT_PAREN, tokens.get(1).getType());
        assertEquals(TokenType.LEFT_BRACE, tokens.get(2).getType());
        assertEquals(TokenType.RIGHT_BRACE, tokens.get(3).getType());
        assertEquals(TokenType.DOT, tokens.get(4).getType());
        assertEquals(TokenType.COMMA, tokens.get(5).getType());
        assertEquals(TokenType.MINUS, tokens.get(6).getType());
        assertEquals(TokenType.PLUS, tokens.get(7).getType());
        assertEquals(TokenType.SEMICOLON, tokens.get(8).getType());
        assertEquals(TokenType.STAR, tokens.get(9).getType());
        assertEquals(TokenType.SLASH, tokens.get(10).getType());

        assertEquals(TokenType.BANG, tokens.get(11).getType());
        assertEquals(TokenType.BANG_EQUAL, tokens.get(12).getType());
        assertEquals(TokenType.EQUAL, tokens.get(13).getType());
        assertEquals(TokenType.EQUAL_EQUAL, tokens.get(14).getType());

        assertEquals(TokenType.GREATER, tokens.get(15).getType());
        assertEquals(TokenType.GREATER_EQUAL, tokens.get(16).getType());
        assertEquals(TokenType.LESS, tokens.get(17).getType());
        assertEquals(TokenType.LESS_EQUAL, tokens.get(18).getType());

        assertEquals(TokenType.IDENTIFIER, tokens.get(19).getType());
        assertEquals(TokenType.STRING, tokens.get(20).getType());
        assertEquals(TokenType.NUMBER, tokens.get(21).getType());
        assertEquals(TokenType.NUMBER, tokens.get(22).getType());

        assertEquals(TokenType.AND, tokens.get(23).getType());
        assertEquals(TokenType.CLASS, tokens.get(24).getType());
        assertEquals(TokenType.ELSE, tokens.get(25).getType());
        assertEquals(TokenType.FALSE, tokens.get(26).getType());
        assertEquals(TokenType.FUN, tokens.get(27).getType());
        assertEquals(TokenType.FOR, tokens.get(28).getType());
        assertEquals(TokenType.IF, tokens.get(29).getType());
        assertEquals(TokenType.NIL, tokens.get(30).getType());
        assertEquals(TokenType.OR, tokens.get(31).getType());
        assertEquals(TokenType.PRINT, tokens.get(32).getType());
        assertEquals(TokenType.RETURN, tokens.get(33).getType());
        assertEquals(TokenType.SUPER, tokens.get(34).getType());
        assertEquals(TokenType.THIS, tokens.get(35).getType());
        assertEquals(TokenType.TRUE, tokens.get(36).getType());
        assertEquals(TokenType.VAR, tokens.get(37).getType());
        assertEquals(TokenType.WHILE, tokens.get(38).getType());

        assertEquals(TokenType.EOF, tokens.get(39).getType());
    }

    @Test
    public void testScanTokensWithEmptyString() {
        Lexer lexer = new Lexer("");
        List<Token> tokens = lexer.scanTokens();
        assertEquals(1, tokens.size());
    }

    @Test
    public void testScanTokensWithSingleToken() {
        Lexer lexer = new Lexer("var");
        List<Token> tokens = lexer.scanTokens();
        assertEquals(2, tokens.size());
    }

    @Test
    public void testScanTokensWithMultipleLines() {
        Lexer lexer = new Lexer("var x = 5;\nvar y = 10;");
        List<Token> tokens = lexer.scanTokens();
        assertEquals(11, tokens.size());
    }

    @Test
    public void testScanTokensWithComments() {
        Lexer lexer = new Lexer("var x = 5; // This is a comment\nvar y = 10;");
        List<Token> tokens = lexer.scanTokens();
        assertEquals(11, tokens.size());
    }

    @Test
    public void testScanTokensWithStrings() {
        Lexer lexer = new Lexer("var x = \"Hello, World!\";");
        List<Token> tokens = lexer.scanTokens();
        assertEquals(6, tokens.size());
    }

    @Test
    public void testScanTokensWithNumbers() {
        Lexer lexer = new Lexer("var x = 5;");
        List<Token> tokens = lexer.scanTokens();
        assertEquals(6, tokens.size());
    }
}

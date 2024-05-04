package com.kogab.interpreter.lexer;

import com.kogab.interpreter.Kogab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.kogab.interpreter.lexer.TokenType.*;

public class Lexer {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;
    private static final Map<String, TokenType> keywords;

    static {
        keywords = new HashMap<>();
        keywords.put("and", AND);
        keywords.put("class", CLASS);
        keywords.put("else", ELSE);
        keywords.put("false", FALSE);
        keywords.put("for", FOR);
        keywords.put("fun", FUN);
        keywords.put("if", IF);
        keywords.put("nil", NIL);
        keywords.put("or", OR);
        keywords.put("print", PRINT);
        keywords.put("return", RETURN);
        keywords.put("super", SUPER);
        keywords.put("this", THIS);
        keywords.put("true", TRUE);
        keywords.put("var", VAR);
        keywords.put("while", WHILE);
    }

    public Lexer(String source) {
        this.source = source;
    }

    public List<Token> scanTokens() {
        while(!this.isAtEnd()) {
            start = current;
            this.scanToken();
        }

        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(': this.addToken(LEFT_PAREN); break;
            case ')': this.addToken(RIGHT_PAREN); break;
            case '{': this.addToken(LEFT_BRACE); break;
            case '}': this.addToken(RIGHT_BRACE); break;
            case ',': this.addToken(COMMA); break;
            case '.': this.addToken(DOT); break;
            case '-': this.addToken(MINUS); break;
            case '+': this.addToken(PLUS); break;
            case ';': this.addToken(SEMICOLON); break;
            case '*': this.addToken(STAR); break;
            case '!': this.addToken(this.match('=') ? BANG_EQUAL : BANG); break;
            case '=': this.addToken(this.match('=') ? EQUAL_EQUAL : EQUAL); break;
            case '<': this.addToken(this.match('=') ? LESS_EQUAL : LESS); break;
            case '>': this.addToken(this.match('=') ? GREATER_EQUAL : GREATER); break;
            case '/':
                if (this.match('/')) {
                    while (this.peek() != '\n' && !this.isAtEnd()) this.advance();
                } else {
                    this.addToken(SLASH);
                }
                break;
            case ' ':
            case '\r':
            case '\t':
                break;
            case '\n':
                line++;
                break;
            case '"': this.scanString(); break;
            default:
                if (this.isDigit(c)) {
                    this.scanNumber();
                } else if (isAlpha(c)) {
                    this.scanIdentifier();
                } else {
                    Kogab.error(this.line, "Unexpected character: " + c);
                }
        }
    }

    private boolean match(char expected) {
        if (this.isAtEnd()) return false;
        if (this.source.charAt(this.current) != expected) return false;

        current++;
        return true;
    }

    private char advance() {
        return this.source.charAt(this.current++);
    }

    private void addToken(TokenType type) {
        this.addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }

    private void scanString() {
        while (this.peek() != '"' && !isAtEnd()) {
            if (this.peek() == '\n') line++;
            this.advance();
        }

        if (this.isAtEnd()) {
            Kogab.error(this.line, "Unterminated string.");
        }

        this.advance();

        String value = this.source.substring(start + 1, current - 1);
        this.addToken(STRING, value);
    }

    private void scanNumber() {
        while (this.isDigit(this.peek())) this.advance();

        if (this.peek() == '.' && this.isDigit(this.peekNext())) {
            this.advance();

            while (this.isDigit(this.peek())) this.advance();
        }

        Double value = Double.parseDouble(source.substring(start, current));
        this.addToken(NUMBER, value);
    }

    private void scanIdentifier() {
        while (this.isAlphanumeric(this.peek())) advance();

        String text = this.source.substring(start, current);
        TokenType type = keywords.get(text);
        if (type == null) type = IDENTIFIER;

        addToken(type);
    }

    private char peek() {
        if (this.isAtEnd()) return '\0';
        return this.source.charAt(this.current);
    }

    private char peekNext() {
        if (this.current + 1 >= this.source.length()) return '\0';
        return this.source.charAt(this.current + 1);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                (c == '_');
    }

    private boolean isAlphanumeric(char c) {
        return this.isDigit(c) || this.isAlpha(c);
    }
}

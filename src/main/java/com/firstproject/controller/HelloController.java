package com.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("/")
    @ResponseBody
    public String hello() {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <title>Mon Premier Spring Boot</title>
                <style>
                    body { 
                        font-family: Arial; 
                        text-align: center; 
                        margin-top: 100px;
                        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                        color: white;
                    }
                    h1 { font-size: 3em; }
                    p { font-size: 1.2em; }
                </style>
            </head>
            <body>
                <h1>🎉 Hello World! 🎉</h1>
                <p>Mon premier projet Spring Boot fonctionne!</p>
                <p>✨ Bravo, vous avez réussi! ✨</p>
            </body>
            </html>
            """;
    }
}
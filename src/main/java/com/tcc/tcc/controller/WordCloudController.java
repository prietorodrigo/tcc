package com.tcc.tcc.controller;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.RectangleBackground;
import com.kennycason.kumo.font.scale.LinearFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.ColorPalette;
import com.tcc.tcc.model.ProducaoCientifica;
import com.tcc.tcc.repository.ProducaoCientificaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
public class WordCloudController {
    @Autowired
    private ProducaoCientificaRepository producaoCientificaRepository;

    @GetMapping("/generateWordCloud")
    public String generateWordCloud(Model model) {
        List<ProducaoCientifica> producaoCientificaList = producaoCientificaRepository.findAll();
        List<String> allKeywords = new ArrayList<>();

        // Obtener todas las palabras clave
        for (ProducaoCientifica producaoCientifica : producaoCientificaList) {
            String[] keywords = producaoCientifica.getPalavrasChaves().split("-");
            for (String keyword : keywords) {
                allKeywords.add(keyword.trim());
            }
        }

        // Contar la frecuencia de las palabras clave
        FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(allKeywords);

        // Generar la nube de palabras
        final Dimension dimension = new Dimension(600, 600);
        WordCloud wordCloud = new WordCloud(dimension, CollisionMode.RECTANGLE);
        wordCloud.setPadding(0);
        wordCloud.setBackground(new RectangleBackground(dimension));
        wordCloud.setColorPalette(new ColorPalette(Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE));
        wordCloud.setFontScalar(new LinearFontScalar(10, 40));
        wordCloud.build(wordFrequencies);

        // Guardar la imagen de la nube de palabras en el sistema de archivos
        File outputFile = new File("src/main/resources/static/img/wordcloud.png");
        wordCloud.writeToFile(outputFile.getAbsolutePath());

        // Enviar la ruta de la imagen a la vista
        model.addAttribute("wordCloudImagePath", "/img/wordcloud.png");

        return "index";
    }
}

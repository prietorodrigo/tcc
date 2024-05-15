package com.tcc.tcc.controller;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.tcc.tcc.model.Proposta;
import com.tcc.tcc.repository.PropostaRepository;
import com.tcc.tcc.service.PropostaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/listarPropostas")
public class GerarTermoPdf extends AbstractPdfView {
    @Autowired
    PropostaService propostaService;

    @GetMapping("/{id}")
    public void generatePdf(@PathVariable Long id, HttpServletResponse response) throws Exception {
        // Aquí asumo que tienes algún servicio para obtener la propuesta según el ID
        Proposta proposta = propostaService.findById(id);

        // Configura el tipo de contenido y el encabezado de descarga
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=\"termo_proposta.pdf\"");

        // Crea un documento PDF usando iText
        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        // Abre el documento
        document.open();

        // Configurar los márgenes
        document.setMargins(72, 72, 85, 85);

        // Agrega el contenido al documento

        Paragraph imagem = new Paragraph();
        Image img = Image.getInstance("./src/main/resources/static/img/brasaooficialcolorido.png");
        img.scaleToFit(71, 40);
        img.setAlignment(Element.ALIGN_CENTER);
        imagem.add(img);
        // Agregar el párrafo al documento
        document.add(imagem);

        Paragraph cabecalho = new Paragraph();
        cabecalho.setFont(FontFactory.getFont(FontFactory.HELVETICA, 10));
        cabecalho.add("Instituto Federal de Educação, Ciência e Tecnologia Sul-rio-grandense");
        cabecalho.add("\nCurso Superior de " + proposta.getCurso().getNome());
        cabecalho.add("\nCampus Santana do Livramento");
        cabecalho.setAlignment(Element.ALIGN_CENTER);
        document.add(cabecalho);

        Paragraph title = new Paragraph("\nTERMO DE COMPROMISSO DE ORIENTAÇÃO");
        title.setAlignment(Element.ALIGN_CENTER);
        title.setFont(FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11));
        document.add(title);

        Paragraph content = new Paragraph();
        content.setFont(FontFactory.getFont(FontFactory.HELVETICA, 11));
        content.add("\n\nEu, " + proposta.getEstudante().getName() + ", matrícula " + proposta.getEstudante().getMatricula()
                + ", aluno(a) do Curso de " + proposta.getCurso().getNome() + ", solicito a " +
                "orientação do(a) Professor(a) " + proposta.getOrientador().getName() + " para o desenvolvimento do " +
                "Trabalho de Conclusão de Curso, com área/tema previsto " +
                proposta.getArea().getNome()+ ", a ser desenvolvido no âmbito das disciplinas " +
                "de "+ proposta.getCurso().getDisciplinastcc() + ". Comprometo-me a ser assíduo às " +
                "orientações e compromissos firmados com meu orientador(a) bem como à cumprir as " +
                "regulamentações e prazos previstos pelo Regulamento de Trabalho de Conclusão de Curso e " +
                "pelos Planos de Ensino das disciplinas de "+ proposta.getCurso().getDisciplinastcc() +".");
        content.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(content);

        Paragraph assinatura = new Paragraph();
        assinatura.setFont(FontFactory.getFont(FontFactory.HELVETICA, 11));
        assinatura.add("\n\n\n_____________________________________________\nAssinatura do(a) Aluno(a)");
        assinatura.setAlignment(Element.ALIGN_RIGHT);
        document.add(assinatura);

        Paragraph content2 = new Paragraph();
        content2.setFont(FontFactory.getFont(FontFactory.HELVETICA, 11));
        content2.add("\n\nEu, " + proposta.getOrientador().getName() + ", SIAPE " + proposta.getOrientador().getSiape() + ", Professor(a) do Curso de " +
                proposta.getCurso().getNome() + ", me comprometo a orientar o Trabalho " +
                "de Conclusão de Curso do(a) aluno(a) " + proposta.getEstudante().getName() + ", a ser desenvolvido " +
                "no âmbito das disciplinas de " + proposta.getCurso().getDisciplinastcc() +".");
        content2.setAlignment(Element.ALIGN_JUSTIFIED);
        document.add(content2);

        // Suponiendo que proposta.getData() devuelve un objeto LocalDate
        LocalDate fechaLocal = proposta.getData();
        // Convertir LocalDate a Date
        Date fecha = java.sql.Date.valueOf(fechaLocal);
        // Crear el formato deseado
        SimpleDateFormat formato = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("pt", "BR"));
        // Formatear la fecha en el formato deseado
        String fechaFormateada = formato.format(fecha);

        // Agregar la fecha formateada al párrafo
        Paragraph assinatura2 = new Paragraph();
        assinatura2.setFont(FontFactory.getFont(FontFactory.HELVETICA, 11));
        assinatura2.add("\n\n\n_____________________________________________\nAssinatura do(a) Professor(a) Orientador(a)");
        assinatura2.add("\n\n\nSant’Ana do Livramento, " + fechaFormateada);
        assinatura2.setAlignment(Element.ALIGN_RIGHT);
        document.add(assinatura2);

        Paragraph subtitulo = new Paragraph("\n\nPasso a passo:");
        subtitulo.setFont(FontFactory.getFont(FontFactory.HELVETICA_BOLD));
        document.add(subtitulo);

        Paragraph content3 = new Paragraph();
        content3.setFont(FontFactory.getFont(FontFactory.HELVETICA, 11));
        content3.add("Aluno(a) preenche o formulário com seus dados.");
        content3.add("\nAluno(a) encaminha o formulário via e-mail para o(a) professor(a) orientador(a).");
        content3.add("\nProfessor(a) orientador(a) preenche os seus dados.");
        content3.add("\nProfessor(a) orientador(a) encaminha o documento devidamente preenchido e com todas as assinaturas via e-mail para a coordenação de curso.");
        content3.add("\nAluno(a) encaminha o formulário via moodle para professor de Práticas I.");
        document.add(content3);

        // Cierra el documento
        document.close();
    }
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {

    }
}

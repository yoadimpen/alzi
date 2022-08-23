package tfm.alzi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Pregunta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pregunta_id")
    private long id;

    @Column(name = "tipo")
    private String tipo;

    // common

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "question")
    private String question;

    @Column(name = "link_question")
    private String linkQuestion;

    // tipo: pregunta/respuesta y completa

    @Column(name = "answer")
    private String answer;

    // tipo: multi-respuesta

    @Column(name = "option_1")
    private String option1;

    @Column(name = "link_option_1")
    private String linkOption1;

    @Column(name = "is_correct_1")
    private Boolean isCorrect1;

    @Column(name = "option_2")
    private String option2;

    @Column(name = "link_option_2")
    private String linkOption2;
    
    @Column(name = "is_correct_2")
    private Boolean isCorrect2;

    @Column(name = "option_3")
    private String option3;

    @Column(name = "link_option_3")
    private String linkOption3;

    @Column(name = "is_correct_3")
    private Boolean isCorrect3;

    @Column(name = "option_4")
    private String option4;

    @Column(name = "link_option_4")
    private String linkOption4;

    @Column(name = "is_correct_4")
    private Boolean isCorrect4;

    public Long getId(){
        return id;
    }

    public String getTipo(){
        return tipo;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public String getQuestion(){
        return question;
    }

    public void setQuestion(String question){
        this.question = question;
    }

    public String getLinkQuestion(){
        return linkQuestion;
    }

    public void setLinkQuestion(String linkQuestion){
        this.linkQuestion = linkQuestion;
    }

    public String getAnswer(){
        return answer;
    }

    public void setAsnwer(String answer){
        this.answer = answer;
    }

    public String getOption1(){
        return option1;
    }

    public void setOption1(String option1){
        this.option1 = option1;
    }

    public String getLinkOption1(){
        return linkOption1;
    }

    public void setLinkOption1(String linkOption1){
        this.linkOption1 = linkOption1;
    }

    public Boolean isCorrect1(){
        return isCorrect1;
    }

    public void setCorrect1(Boolean isCorrect1){
        this.isCorrect1 = isCorrect1;
    } 

    public String getOption2(){
        return option2;
    }

    public void setOption2(String option2){
        this.option2 = option2;
    }

    public String getLinkOption2(){
        return linkOption2;
    }

    public void setLinkOption2(String linkOption2){
        this.linkOption2 = linkOption2;
    }

    public Boolean isCorrect2(){
        return isCorrect2;
    }

    public void setCorrect2(Boolean isCorrect2){
        this.isCorrect2 = isCorrect2;
    }

    public String getOption3(){
        return option3;
    }

    public void setOption3(String option3){
        this.option3 = option3;
    }

    public String getLinkOption3(){
        return linkOption3;
    }

    public void setLinkOption3(String linkOption3){
        this.linkOption3 = linkOption3;
    }

    public Boolean isCorrect3(){
        return isCorrect3;
    }

    public void setCorrect3(Boolean isCorrect3){
        this.isCorrect3 = isCorrect3;
    }

    public String getOption4(){
        return option4;
    }

    public void setOption4(String option4){
        this.option4 = option4;
    }

    public String getLinkOption4(){
        return linkOption4;
    }

    public void setLinkOption4(String linkOption4){
        this.linkOption4 = linkOption4;
    }

    public Boolean isCorrect4(){
        return isCorrect4;
    }

    public void setCorrect4(Boolean isCorrect4){
        this.isCorrect4 = isCorrect4;
    }

}

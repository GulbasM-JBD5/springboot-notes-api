
package com.example.thirdyear.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class FlashcardRequest {

    @NotBlank(message = "Sual boş ola bilməz.")
    @Size(max = 500, message = "Sual maksimum 500 simvol ola bilər.")
    private String question;

    @NotBlank(message = "Cavab boş ola bilməz.")
    @Size(max = 5000, message = "Cavab maksimum 5000 simvol ola bilər.")
    private String answer;

    public FlashcardRequest() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}

package com.decoder.bookstore.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record BookRecordDto(
        @NotBlank @Size(max = 150) String title,
        @NotBlank @Size(max = 100) String author,
        @NotBlank @Size(max = 100) String publisher,

        /* Aqui temos outro exemplo que saiu diferente da aula, ele adicionou esse @Positive vou pedir para ele explicar aqui abaixou oque é isso */
        // @Positive aceita apenas valores maiores que zero
        @NotNull @Positive Integer publicationYear
) {
}
